package com.example.dianaivan.booksapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dianaivan.booksapp.Adapters.BookAdapter;
import com.example.dianaivan.booksapp.Listeners.OnClickListenerAddBook;
import com.example.dianaivan.booksapp.Models.Book;
import com.example.dianaivan.booksapp.Services.RemoteBookServiceImpl;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageBooksActivity extends AppCompatActivity {

    private RemoteBookServiceImpl.RemoteBookServiceInterface remoteService= RemoteBookServiceImpl.getInstance();
    private ListView myListView;
    final List<Book> bookList=new ArrayList<>();

    private static final String TAG="ManageBActivity";
    private boolean isAdmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_books);

        Button buttonAddBook=findViewById(R.id.buttonAddBook);
        buttonAddBook.setOnClickListener(new OnClickListenerAddBook(remoteService));

        isAdmin=getIntent().getBooleanExtra("isAdmin",false);
        if(!isAdmin)
        {
            //TODO hide the add button
            buttonAddBook.setVisibility(View.INVISIBLE);
        }

        //for observer pattern:
       // RemoteBookServiceImpl.attach(this);
        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        FirebaseUser currentUser=mAuth.getCurrentUser();
        Log.d(TAG,"Logged user:"+currentUser.getEmail()+ " is admin: "+isAdmin);

        readRecords();
        countRecords();
    }

    //count all book records
    public void countRecords(){
        int recordCount=bookList.size();
        TextView textViewRecordCount=(TextView) findViewById(R.id.textViewRecordCount);
        textViewRecordCount.setText(recordCount + " records found.");
    }


    //read all records
    public void readRecords() {

        myListView = findViewById(R.id.books_list_view);

        //  final List<Book> bookList=new TableControllerBook(this).read();
        Call<Map<String, Book>> call = remoteService.getAllBooks();
        call.enqueue(new Callback<Map<String, Book>>() {
            @Override
            public void onResponse(Call<Map<String, Book>> call, Response<Map<String, Book>> response) {
                final Map<String, Book> books = response.body();
                if (books != null && !books.isEmpty()) {

                    loadData(books);
                    countRecords();
                    Log.d("HomeActivity", "Read data of size: " + books.size());
                } else {
                    Log.d("HomeActivity", "Data is empty. ");
                }
            }

            @Override
            public void onFailure(Call<Map<String, Book>> call, Throwable t) {
                Log.d("HomeActivity", "Failed to read data.");
            }
        });
    }
    public void loadData(Map<String,Book> books)
    {
        //bookList=new ArrayList<>();
        bookList.clear();
        for(Map.Entry<String,Book> entry:books.entrySet())
        {
            if(entry.getValue().getImageURL().isEmpty())
                entry.getValue().setImageURL("https://www.google.ro/search?q=book&source=lnms&tbm=isch&sa=X&ved=0ahUKEwiUg4zo2u7XAhWEWRQKHTWjCrMQ_AUICigB&biw=1229&bih=568#imgrc=-5-48n6dvPGAAM:");
            bookList.add(entry.getValue());
        }
        BookAdapter adapter=new BookAdapter(this,bookList);
        myListView.setAdapter(adapter);

        final Context context=this;
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position,long id)
            {
                final Book selectedBook=bookList.get(position);
                final CharSequence[] items={"View","Edit","Delete"};

                new AlertDialog.Builder(context).setTitle("Book Record")
                        .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int item) {
                                if(item==0)
                                {
                                    Intent intent=new Intent(context,ViewBookActivity.class);
                                    intent.putExtra("bookTitle",selectedBook.getTitle());

                                    startActivity(intent);

                                }
                                else if(item==1){
                                    if(isAdmin)
                                        getRecord(selectedBook.getTitle(),context);
                                    else
                                        Toast.makeText(context,"You need admin account in order to perform this operation.",Toast.LENGTH_SHORT).show();
                                }
                                else if (item==2){
                                    if(isAdmin)
                                    {
                                        new AlertDialog.Builder(context).setTitle("Delete").setMessage("Are you sure you want to permanently delete this record?")
                                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        deleteBook(selectedBook.getTitle(),context);
                                                    /* boolean deleteSuccessful=new TableControllerBook(context).delete(selectedBook.getId());
                                                   f(deleteSuccessful)
                                                    {
                                                        Toast.makeText(context, "Book record was deleted.", Toast.LENGTH_SHORT).show();
                                                    }
                                                    else
                                                    {
                                                        Toast.makeText(context, "Unable to delete book record.", Toast.LENGTH_SHORT).show();
                                                    }*/
                                                        //((ManageBooksActivity)context).countRecords();
                                                        //((ManageBooksActivity)context).readRecords();
                                                    }
                                                })
                                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        //pass;

                                                    }
                                                }).show();
                                        countRecords();
                                    }
                                    else
                                    {
                                        Toast.makeText(context,"You need admin account in order to perform this operation.",Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }
                        }).show();
                return ;
            }
        });

    }

    public void editRecord(final Book book,final Context context)
    {
        //read a single record
        //final TableControllerBook tableControllerBook=new TableControllerBook(context);
       // final Book book;//=tableControllerBook.readSingleRecord(bookId);
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

                                //boolean updateSuccessful=tableControllerBook.update(book);

                               /* if(updateSuccessful){
                                    Toast.makeText(context, "Book record was updated.", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(context, "Unable to update book record.", Toast.LENGTH_SHORT).show();
                                }*/

                                updateBook(book,context);
                               // ((ManageBooksActivity)context).countRecords();
                               // ((ManageBooksActivity)context).readRecords();
                                dialog.cancel();

                            }
                        }).show();
    }

    private void getRecord(final String title,final Context context)
    {
        Call<Book> call=remoteService.getBook(title);
        call.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                final Book book=response.body();
                if(book!=null)
                {
                    editRecord(book,context);
                    Log.d("HomeActivity", "Read book: "+book.getTitle());
                }
                else
                {
                    Log.d("HomeActivity", "No book with title: "+title);
                }
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {
                Log.d("HomeActivity", "Failed finding book: "+title);
            }
        });
    }

    private void updateBook(final Book book,final Context context)
    {
        Call<Book> call=remoteService.createBook(book.getTitle(),book);
        call.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {

                Toast.makeText(context, "Book record was updated.", Toast.LENGTH_SHORT).show();
                Log.d("HomeActivity", "Updated book: "+book.getTitle());
                ((ManageBooksActivity)context).readRecords();
                countRecords();

            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {
                Log.d("HomeActivity", "Update failed for "+book.getTitle());
                Toast.makeText(context, "Unable to update book.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteBook(final String title,final Context context)
    {
        Call<Book> call=remoteService.deleteBook(title);
        call.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                Log.d("HomeActivity", "Deleted book: "+title);
                Toast.makeText(context, "Book record was deleted.", Toast.LENGTH_SHORT).show();
                ((ManageBooksActivity)context).readRecords();
                countRecords();
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {
                Log.d("HomeActivity", "Failed to delete book: "+title);
                Toast.makeText(context, "Unable to delete book.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
