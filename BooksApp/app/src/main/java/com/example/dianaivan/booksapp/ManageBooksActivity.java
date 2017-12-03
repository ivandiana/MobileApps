package com.example.dianaivan.booksapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dianaivan.booksapp.Adapters.BookAdapter;
import com.example.dianaivan.booksapp.Listeners.OnClickListenerAddBook;
import com.example.dianaivan.booksapp.Listeners.OnLongClickListenerBook;
import com.example.dianaivan.booksapp.Models.Book;
import com.example.dianaivan.booksapp.database.TableControllerBook;

import java.util.ArrayList;
import java.util.List;

public class ManageBooksActivity extends AppCompatActivity {

    private ListView myListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_books);

        Button buttonAddBook=findViewById(R.id.buttonAddBook);
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

        myListView= findViewById(R.id.books_list_view);

        final List<Book> bookList=new TableControllerBook(this).read();
        BookAdapter adapter=new BookAdapter(this,bookList);
        myListView.setAdapter(adapter);

        final Context context=this;
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position,long id)
            {
                final Book selectedBook=bookList.get(position);
                final CharSequence[] items={"Edit","Delete"};

                new AlertDialog.Builder(context).setTitle("Book Record")
                        .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int item) {
                                if(item==0){
                                    editRecord(selectedBook.getId(),context);
                                }
                                else if (item==1){
                                    boolean deleteSuccessful=new TableControllerBook(context).delete(selectedBook.getId());
                                    if(deleteSuccessful)
                                    {
                                        Toast.makeText(context, "Book record was deleted.", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        Toast.makeText(context, "Unable to delete book record.", Toast.LENGTH_SHORT).show();
                                    }
                                    ((ManageBooksActivity)context).countRecords();
                                    ((ManageBooksActivity)context).readRecords();
                                }
                            }
                        }).show();
                return ;
            }
        });

    }

    public void editRecord(final int bookId,final Context context)
    {
        //read a single record
        final TableControllerBook tableControllerBook=new TableControllerBook(context);
        final Book book=tableControllerBook.readSingleRecord(bookId);
        //inflate book_input_form for editing this time
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView=inflater.inflate(R.layout.book_input_form,null,false);
        //List down the elements
        final EditText editTextTitle=(EditText) formElementsView.findViewById(R.id.editTextBookTitle);
        final EditText editTextAuthor=(EditText) formElementsView.findViewById(R.id.editTextBookAuthor);
        final EditText editTextGenre=(EditText) formElementsView.findViewById(R.id.editTextGenre);
        final EditText editTextExchangeMethod=(EditText) formElementsView.findViewById(R.id.editTextExchangeMethod);
        final EditText editTextLocation=(EditText) formElementsView.findViewById(R.id.editTextLocation);
        final EditText editTextImageURL=formElementsView.findViewById(R.id.editTextImageURL);

        editTextAuthor.setText(book.getAuthor());
        editTextTitle.setText(book.getTitle());
        editTextGenre.setText(book.getGenre());
        editTextExchangeMethod.setText(book.getExchangeMethod());
        editTextLocation.setText(book.getLocation());
        editTextImageURL.setText((book.getImageURL()));
        new AlertDialog.Builder(context)
                .setView(formElementsView)
                .setTitle("Edit Record")
                .setPositiveButton("Save Changes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                book.setTitle(editTextTitle.getText().toString());
                                book.setAuthor(editTextAuthor.getText().toString());
                                book.setGenre(editTextGenre.getText().toString());
                                book.setExchangeMethod(editTextExchangeMethod.getText().toString());
                                book.setLocation(editTextLocation.getText().toString());
                                book.setImageURL(editTextImageURL.getText().toString());

                                boolean updateSuccessful=tableControllerBook.update(book);
                                if(updateSuccessful){
                                    Toast.makeText(context, "Book record was updated.", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(context, "Unable to update book record.", Toast.LENGTH_SHORT).show();
                                }
                                ((ManageBooksActivity)context).countRecords();
                                ((ManageBooksActivity)context).readRecords();

                            }
                        }).show();



    }
}
