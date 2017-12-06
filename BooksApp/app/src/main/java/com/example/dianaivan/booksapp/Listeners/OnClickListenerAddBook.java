package com.example.dianaivan.booksapp.Listeners;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dianaivan.booksapp.MainActivity;
import com.example.dianaivan.booksapp.ManageBooksActivity;
import com.example.dianaivan.booksapp.Models.Book;
import com.example.dianaivan.booksapp.R;
import com.example.dianaivan.booksapp.database.TableControllerBook;

/**
 * Created by Diana Ivan on 11/5/2017.
 */

public class OnClickListenerAddBook implements View.OnClickListener{

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

        //create an alert dialog with the inflated book_input_form.xml

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


                        Book b=new Book(bookTitle,bookAuthor,genre,exchangeMethod,location,imageURL,0);

                        boolean createSuccesful=new TableControllerBook(context).create(b);
                        if(createSuccesful)
                        {
                            Toast.makeText(context,"Book info was saved",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(context,"Unable to save book information",Toast.LENGTH_SHORT).show();
                        }
                        ((ManageBooksActivity)context).countRecords();
                        ((ManageBooksActivity)context).readRecords();
                        dialog.cancel();
                    }
                        }).show();

        ((ManageBooksActivity)context).readRecords();
    }
}
