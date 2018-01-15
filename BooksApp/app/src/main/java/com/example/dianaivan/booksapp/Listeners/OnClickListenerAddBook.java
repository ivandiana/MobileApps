package com.example.dianaivan.booksapp.Listeners;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dianaivan.booksapp.ManageBooksActivity;
import com.example.dianaivan.booksapp.Models.Book;
import com.example.dianaivan.booksapp.R;
import com.example.dianaivan.booksapp.Services.RemoteBookServiceImpl;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Diana Ivan on 11/5/2017.
 */

public class OnClickListenerAddBook implements View.OnClickListener{

    private RemoteBookServiceImpl.RemoteBookServiceInterface remoteService;

    public OnClickListenerAddBook(RemoteBookServiceImpl.RemoteBookServiceInterface service )
    {
        remoteService=service;
    }
    @Override
    public void onClick(View view)
    {
        //get the application context that is needed to inflate the XML layout file
        final Context context=view.getRootView().getContext();
       //make ui elements/widgets accessible using code
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView=inflater.inflate(R.layout.book_input_form,null,false);

        //list form widgets inside book_input_form as final vars for using them in an AlertDialog
        final EditText editTextBookTitle=(EditText) formElementsView.findViewById(R.id.editTextBookTitle);
        final EditText editTextBookAtuhor=(EditText) formElementsView.findViewById(R.id.editTextBookAuthor);
        final EditText editTextGenre=(EditText) formElementsView.findViewById(R.id.editTextGenre);
        final EditText editTextExchangeMethod=(EditText) formElementsView.findViewById(R.id.editTextExchangeMethod);
        final EditText editTextLocation=(EditText) formElementsView.findViewById(R.id.editTextLocation);
        final EditText editTextImageURL=(EditText) formElementsView.findViewById(R.id.editTextImageURL);

        //create an alert dialog with the inflated book_input_form.x

        new AlertDialog.Builder(context).setView(formElementsView)
                .setTitle("Add New Book")
                .setPositiveButton("Add",
                        new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog,int id){
                        String bookTitle=editTextBookTitle.getText().toString();
                        String bookAuthor=editTextBookAtuhor.getText().toString();
                        String genre=editTextGenre.getText().toString();
                        String exchangeMethod=editTextExchangeMethod.getText().toString();
                        String location=editTextLocation.getText().toString();
                        String imageURL=editTextImageURL.getText().toString();


                        final Book b=new Book(bookTitle,bookAuthor,genre,exchangeMethod,location,imageURL,0,0);

                        addNewBook(b,context);
                        dialog.cancel();
                    }
                        }).show();


    }

    private void addNewBook(final Book b,final Context context)
    {
        Call<Book> call=remoteService.createBook(b.getTitle(),b);
        call.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                Toast.makeText(context,"Book info was saved",Toast.LENGTH_SHORT).show();
                Log.d("AddBook", "Book added: " + b.getTitle());
                RemoteBookServiceImpl.notifyAllObservers();
                ((ManageBooksActivity)context).readRecords();
                ((ManageBooksActivity)context).countRecords();
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {
                Toast.makeText(context,"Unable to save book information",Toast.LENGTH_SHORT).show();
                Log.d("AddBook", "Failed to add book: " + b.getTitle());
            }
        });


    }
}
