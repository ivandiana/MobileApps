package com.example.dianaivan.examprep;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dianaivan.examprep.domain.Book;

import org.w3c.dom.Text;

public class DetailsActivity extends AppCompatActivity implements MyCallback{

    Context context;
    TextView bookT;
    int bookId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        bookT=findViewById(R.id.bookTitle);
        bookId=getIntent().getIntExtra("bookId",0);
        populateView();
        context=this;
    }

    void populateView()
    {
        final TextView bookTitle=(TextView) findViewById(R.id.bookTitle);
        final TextView bookDate=(TextView)findViewById(R.id.bookDate);

        Manager manager=new Manager(getApplication());
        final Book b=manager.getBookById(bookId,this);
        if(b!=null)
        {
            bookDate.setText(b.getDate().toString());
            bookTitle.setText(b.getTitle());
        }
    }

    public void back(View view)
    {
        clear();
    }

    public void delete(View view)
    {
        final Manager manager=new Manager(getApplication());
        final Book b=manager.getBookById(bookId,this);
        new AlertDialog.Builder(this).setTitle("Delete").setMessage("Are you sure you want to permanently delete this record?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        manager.deleteBook(b, (MyCallback) context);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //pass;

                    }
                }).show();
    }

    @Override
    public void showError(String message) {
        Snackbar.make(bookT,message, Snackbar.LENGTH_INDEFINITE)
                .setAction("RETRY",new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        populateView();
                    }
                }).show();
    }

    @Override
    public void clear() {
        finish();
    }
}
