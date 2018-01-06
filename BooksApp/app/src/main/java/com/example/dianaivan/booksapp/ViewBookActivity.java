package com.example.dianaivan.booksapp;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dianaivan.booksapp.Models.Book;
import com.example.dianaivan.booksapp.Services.RemoteBookServiceImpl;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewBookActivity extends AppCompatActivity {
    private RemoteBookServiceImpl.RemoteBookServiceInterface remoteService= RemoteBookServiceImpl.getInstance();
    private String bookTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_book);

        bookTitle = getIntent().getStringExtra("bookTitle");
        populateView();
        populateChart();
    }


    private void populateChart()
    {
        final BarChart chart =  findViewById(R.id.chart);
        //TableControllerBook tableControllerBook=new TableControllerBook(this);
        final List<BarEntry> entries=new ArrayList<BarEntry>();
        Call<Book> call=remoteService.getBook(bookTitle);
        call.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                final Book book=response.body();
                if(book!=null)
                {
                    double rate=book.getRating()/book.getRatingsNo();
                    entries.add(new BarEntry(5f,(float)rate));
                    BarDataSet set = new BarDataSet(entries, "Rate");
                    BarData data = new BarData(set);
                    data.setBarWidth(1f); // set custom bar width
                    chart.setData(data);
                    chart.setFitBars(true);

                    chart.invalidate();

                    Log.d("ViewActivity", "Updated book chart: "+book.getTitle());
                }
                else
                {
                    Log.d("ViewActivity", "No book with title: "+bookTitle);
                }
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {
                Log.d("HomeActivity", "Failed getting book: "+bookTitle);
            }
        });
    }

    private void populateView()
    {
       // final TableControllerBook tableControllerBook=new TableControllerBook(this);
        //final Book book=tableControllerBook.readSingleRecord(bookId);

        final TextView viewTextTitle=findViewById(R.id.viewTextBookTitle);
        final TextView viewTextAuthor=findViewById(R.id.viewTextBookAuthor);
        final TextView viewTextGenre=findViewById(R.id.viewTextGenre);
        final TextView viewTextExchangeMethod=findViewById(R.id.viewTextExchangeMethod);
        final TextView viewTextLocation=findViewById(R.id.viewTextLocation);
        final ImageView thumbnailImageView=findViewById(R.id.book_view_thumbnail);
        final Context context=this;

        Call<Book> call=remoteService.getBook(bookTitle);
        call.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                final Book book=response.body();

                if(book!=null)
                {
                    if(book.getImageURL().isEmpty())
                        book.setImageURL("https://www.google.ro/search?q=book&source=lnms&tbm=isch&sa=X&ved=0ahUKEwiUg4zo2u7XAhWEWRQKHTWjCrMQ_AUICigB&biw=1229&bih=568#imgrc=-5-48n6dvPGAAM:");
                    viewTextTitle.setText(book.getTitle());
                    viewTextAuthor.setText(book.getAuthor());
                    viewTextGenre.setText(book.getGenre());
                    viewTextExchangeMethod.setText(book.getExchangeMethod());
                    viewTextLocation.setText(book.getLocation());
                    Picasso.with(context).load(book.getImageURL()).placeholder(R.mipmap.ic_launcher).into(thumbnailImageView);
                    Log.d("ViewBookActivity", "Viewing book: " + book.getTitle());
                }
                else
                {
                    Log.d("ViewBookActivity", "Unable to view book: " + book.getTitle());
                }
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {
                Log.d("ViewBookActivity", "Failed to view book: " + bookTitle);
            }
        });



        Button b=findViewById(R.id.buttonRateBook);
        b.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                rateBook();
            }
        });
    }

    private void rateBook()
    {

        final Dialog d=new Dialog(this);
        d.setContentView(R.layout.number_picker);
        Button b1 = (Button) d.findViewById(R.id.button1);
        Button b2 = (Button) d.findViewById(R.id.button2);

        final NumberPicker np = (NumberPicker) d.findViewById(R.id.np);
        np.setMaxValue(5);
        np.setMinValue(1);

        //onSelect
        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                updateRating(np.getValue());
                d.dismiss();
            }
        });
        //on cancel
        b2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        d.show();

    }

    private void updateRating(final int rateValue)
    {

        //update the rating value for the current book
     //   TableControllerBook tableControllerBook=new TableControllerBook(this);
       // Book book=tableControllerBook.readSingleRecord(bookId);

        Call<Book> call=remoteService.getBook(bookTitle);
        call.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                final Book book=response.body();
                if(book!=null)
                {
                    book.setRating(book.getRating()+rateValue);
                    book.setRatingsNo(book.getRatingsNo()+1);
                    updateBook(book);
                    Log.d("HomeActivity", "Updated book rating: "+book.getTitle());
                }
                else
                {
                    Log.d("HomeActivity", "No book with title: "+bookTitle);
                }
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {
                Log.d("HomeActivity", "Failed getting book: "+bookTitle);
            }
        });
    }

    private void updateBook(final Book book)
    {
        Call<Book> call=remoteService.createBook(book.getTitle(),book);
        call.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                Log.d("HomeActivity", "Updated book: "+book.getTitle());
                populateChart();
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {
                Log.d("HomeActivity", "Update failed for "+book.getTitle());
            }
        });
    }


}
