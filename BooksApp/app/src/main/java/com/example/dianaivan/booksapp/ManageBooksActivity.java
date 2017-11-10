package com.example.dianaivan.booksapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dianaivan.booksapp.Listeners.OnClickListenerAddBook;
import com.example.dianaivan.booksapp.Listeners.OnLongClickListenerBook;
import com.example.dianaivan.booksapp.Models.Book;
import com.example.dianaivan.booksapp.database.TableControllerBook;

import java.util.List;

public class ManageBooksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_books);

        Button buttonAddBook=(Button) findViewById(R.id.buttonAddBook);
        buttonAddBook.setOnClickListener(new OnClickListenerAddBook());

        countRecords();
        readRecords();

    }

    //count all book records
    public void countRecords(){
        int recordCount=new TableControllerBook(this).count();
        TextView textViewRecordCount=(TextView) findViewById(R.id.textViewRecordCount);
        textViewRecordCount.setText(recordCount + " records found.");
    }

    //read all records
    public void readRecords()
    {
        LinearLayout linearLayoutRecords=(LinearLayout)findViewById(R.id.linearLayoutRecords);
        linearLayoutRecords.removeAllViews();

        List<Book> books=new TableControllerBook(this).read();
        if(books.size()>0)
        {
            for(Book b:books)
            {
                int id=b.getId();
                String bookTitle=b.getTitle();
                String bookAuthor=b.getAuthor();

                String textViewContents=bookTitle+" - "+bookAuthor;
                TextView textViewBookItem=new TextView(this);
                textViewBookItem.setPadding(0,10,0,10);
                textViewBookItem.setText(textViewContents);
                textViewBookItem.setTag(Integer.toString(id));
                textViewBookItem.setOnLongClickListener(new OnLongClickListenerBook());
                linearLayoutRecords.addView(textViewBookItem);
            }
        }
        else
        {
            TextView locationItem=new TextView(this);
            locationItem.setPadding(8,8,8,8);
            locationItem.setText("No records found");

            linearLayoutRecords.addView(locationItem);

        }

    }
}
