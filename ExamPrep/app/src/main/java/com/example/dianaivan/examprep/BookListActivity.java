package com.example.dianaivan.examprep;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.example.dianaivan.examprep.adapter.MyAdapter;
import com.example.dianaivan.examprep.domain.Book;

import java.util.List;



public class BookListActivity extends AppCompatActivity implements MyCallback{

    private MyAdapter adapter;

    ProgressBar progressBar;

    FloatingActionButton fab;
    private View recyclerView;
    private Manager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);
        //toolbar.setTitle(getTitle());
        fab = (FloatingActionButton) findViewById(R.id.fab);
        progressBar=(ProgressBar)findViewById(R.id.progress);
        recyclerView=(RecyclerView)findViewById(R.id.event_list);
        assert recyclerView!=null;

        manager=new Manager(getApplication());
        setUpRecyclerView((RecyclerView) recyclerView);
        loadEvents();

    }

    private boolean loadEvents()
    {
        boolean connectivity=manager.networkConnectivity(getApplicationContext());
        if(connectivity)
        {
            //allow to see the add button
            fab.setVisibility(View.VISIBLE);
        }
        else
        {
            fab.setVisibility(View.GONE);
            showError("No internet connection");
        }
        manager.loadEvents(progressBar,this);
        return connectivity;
    }
    @Override
    public void showError(String error) {
        progressBar.setVisibility(View.GONE);
        Snackbar.make(recyclerView, error, Snackbar.LENGTH_INDEFINITE)
                .setAction("RETRY",new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        loadEvents();
                    }
                }).show();
    }

    @Override
    public void clear() {
        adapter.clear();
    }

    public void onAddClick(View view)
    {

    }

    public void onRefreshClick(View view)
    {
        manager.loadEvents(progressBar,this);
    }

    private  void setUpRecyclerView(@NonNull RecyclerView recyclerView)
    {
        adapter=new MyAdapter();
        ((BookApp)getApplication()).db.getBooksDao().getBooks()
                .observe(this,new Observer<List<Book>>(){
                    @Override
                    public void onChanged(@Nullable List<Book> books)
                    {
                        adapter.setData(books);
                    }
                });
        recyclerView.setAdapter(adapter);
    }
}
