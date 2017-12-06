package com.example.dianaivan.booksapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.dianaivan.booksapp.Models.Book;
import com.example.dianaivan.booksapp.Models.Rating;
import com.example.dianaivan.booksapp.database.TableControllerBook;
import com.example.dianaivan.booksapp.database.TableControllerRatings;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.in;

public class ViewBookActivity extends AppCompatActivity {
    private int bookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_book);

        bookId = getIntent().getExtras().getInt("bookId");
        populateView();
        populateChart();
    }

    private void populateChart()
    {
        BarChart chart =  findViewById(R.id.chart);
        TableControllerBook tableControllerBook=new TableControllerBook(this);
        Book book=tableControllerBook.readSingleRecord(bookId);

        List<BarEntry> entries=new ArrayList<BarEntry>();
        List<Rating> ratings=new TableControllerRatings(this).readByBookId(bookId);
       entries.add(new BarEntry(5f,(float)book.getRating()));
        BarDataSet set = new BarDataSet(entries, "Rate");

        BarData data = new BarData(set);
        data.setBarWidth(1f); // set custom bar width
        chart.setData(data);
        chart.setFitBars(true);

        chart.invalidate();
    }

    private void populateView()
    {
        final TableControllerBook tableControllerBook=new TableControllerBook(this);
        final Book book=tableControllerBook.readSingleRecord(bookId);

        TextView viewTextTitle=findViewById(R.id.viewTextBookTitle);
        TextView viewTextAuthor=findViewById(R.id.viewTextBookAuthor);
        TextView viewTextGenre=findViewById(R.id.viewTextGenre);
        TextView viewTextExchangeMethod=findViewById(R.id.viewTextExchangeMethod);
        TextView viewTextLocation=findViewById(R.id.viewTextLocation);
        ImageView thumbnailImageView=findViewById(R.id.book_view_thumbnail);

        viewTextTitle.setText(book.getTitle());
        viewTextAuthor.setText(book.getAuthor());
        viewTextGenre.setText(book.getGenre());
        viewTextExchangeMethod.setText(book.getExchangeMethod());
        viewTextLocation.setText(book.getLocation());
        Picasso.with(this).load(book.getImageURL()).placeholder(R.mipmap.ic_launcher).into(thumbnailImageView);

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

    private void updateRating(int rateValue)
    {
        //insert rating in db

        Rating r=new Rating(bookId,rateValue);
        TableControllerRatings tableRatings=new TableControllerRatings(this);
        boolean createSuccesful=tableRatings.create(r);

        //update the rating value for the current book
        List<Rating> ratings=tableRatings.readByBookId(bookId);
        TableControllerBook tableControllerBook=new TableControllerBook(this);
        Book book=tableControllerBook.readSingleRecord(bookId);

        double totalRating=0;
        for(Rating aux : ratings)
        {
            totalRating+=aux.getRate();
        }
        totalRating=totalRating/ratings.size();
        if(totalRating!=0)
            book.setRating(totalRating);
        tableControllerBook.update(book);
        populateChart();
    }


}
