package com.example.dianaivan.examprep;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.ProgressBar;

import com.example.dianaivan.examprep.domain.Book;
import com.example.dianaivan.examprep.service.BookService;
import com.example.dianaivan.examprep.service.ServiceFactory;

import java.util.List;

import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Diana Ivan on 1/22/2018.
 */

public class Manager {
    private BookApp app;
    private BookService service;

    Manager(Application application)
    {
        this.app=(BookApp) application;
        service= ServiceFactory.createRetrofitService(BookService.class,BookService.SERVICE_ENDPOINT);

    }

    boolean networkConnectivity(Context context)
    {
        ConnectivityManager cm=((ConnectivityManager)context.
                getSystemService(Context.CONNECTIVITY_SERVICE));
        assert cm!=null;

        NetworkInfo networkInfo=cm.getActiveNetworkInfo();
        return networkInfo!= null && networkInfo.isConnected();
    }

    void loadEvents(final ProgressBar progressBar, final MyCallback callback)
    {
        //TODO take a better looking at this
        service.getBooks()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Book>>() {
                    @Override
                    public void onCompleted() {
                        Timber.v("Book Service completed");
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e,"Error while loading the events");
                        callback.showError("Not able to retrieve the data. Displaying local data!");
                    }

                    @Override
                    public void onNext(final List<Book> books) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                app.db.getBooksDao().deleteBooks();
                                app.db.getBooksDao().addBooks(books);
                            }
                        }).start();
                        Timber.v("Books locally persisted");
                    }
                });
    }

    public void saveBook(final Book book, final MyCallback callback)
    {
        service.addBook(book)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Book>() {
                    @Override
                    public void onCompleted() {

                        //persist data locally
                        saveDataLocally(book);
                        Timber.v("Service completed.");
                        callback.clear();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e,"Error while persisting a book");
                        callback.showError("Not able to connect to the server, will not persist!");
                    }

                    @Override
                    public void onNext(Book book) {
                        Timber.v("Book persisted");
                    }
                });
    }

    private void saveDataLocally(final Book book)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                app.db.getBooksDao().addBook(book);
            }
        }).start();
    }

}
