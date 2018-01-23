package com.example.dianaivan.examprep;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.dianaivan.examprep.domain.Book;

import java.util.Date;

public class NewBookActivity extends AppCompatActivity implements  MyCallback {

    EditText bookTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_book);
        bookTitle=(EditText)findViewById(R.id.bookTitle);
    }

    public void save(View view)
    {
        Manager manager=new Manager(getApplication());
        manager.saveBook(new Book(0,bookTitle.getText().toString(),new Date()), this);

    }

    @Override
    public void showError(String message) {
        Snackbar.make(bookTitle, message, Snackbar.LENGTH_INDEFINITE)
                .setAction("DISMISS",new View.OnClickListener(){
                            @Override
                            public void onClick(View view)
                            {
                                finish();
                            }
                        }
                ).show();
    }

    @Override
    public void clear() {
        finish();
    }
}
