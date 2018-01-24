package com.example.dianaivan.examprep;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dianaivan.examprep.domain.Book;

import timber.log.Timber;

public class EditActivity extends AppCompatActivity implements MyCallback{

    Context context;
    EditText bookT;
    Book b;
    int bookId;
    EditText editTextTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        bookT=findViewById(R.id.bookTitle);
        bookId=getIntent().getIntExtra("bookId",0);

        context=this;
        editTextTitle=(EditText)findViewById(R.id.bookTitle);
        editBook();
    }

    private void editBook()
    {
        Manager manager=new Manager(getApplication());
        b=manager.getBookById(bookId,this);

        editTextTitle.setText(b.getTitle());
        Timber.v("In edit book..");

    }

    @Override
    public void showError(String message) {
        Snackbar.make(bookT,message, Snackbar.LENGTH_INDEFINITE)
                .setAction("RETRY",new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        editBook();
                    }
                }).show();
    }

    @Override
    public void clear() {
        finish();
    }

    public void back(View view)
    {
        clear();
    }

    public void update(View view)
    {
        Manager manager=new Manager(getApplication());
        b.setTitle(editTextTitle.getText().toString());
        manager.updateBook(b, (MyCallback) context);
        finish();
    }
}
