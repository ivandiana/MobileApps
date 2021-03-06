package com.example.dianaivan.booksapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dianaivan.booksapp.Models.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Diana Ivan on 11/5/2017.
 */

//Controls all the operations related t the student's table
public class TableControllerBook extends DatabaseHandler {


    public TableControllerBook(Context context)
    {
        super(context);
    }

    //creating a new record
    public boolean create(Book book)
    {
        ContentValues values=new ContentValues();

        values.put("author",book.getAuthor());
        values.put("title",book.getTitle());
        values.put("genre",book.getGenre());
        values.put("exchangeMethod",book.getExchangeMethod());
        values.put("location",book.getLocation());
        values.put("imageURL",book.getImageURL());
        values.put("rating",0);
        values.put("ratingsNo",0);

        SQLiteDatabase db=this.getWritableDatabase();

        boolean createSuccesful=db.insert("books",null,values)>0;
        db.close();

        return createSuccesful;
    }

    //count records
    public int count(){
        SQLiteDatabase db=this.getWritableDatabase();
        String sql="Select * From books";
        int recordCount=db.rawQuery(sql,null).getCount();
        db.close();

        return recordCount;
    }

    public List<Book> read()
    {
        List<Book> recordList=new ArrayList<Book>();
        String sql="Select * from books order by id DESC";
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor =db.rawQuery(sql,null);

        if(cursor.moveToFirst())
        {
            do{
                String id=cursor.getString(cursor.getColumnIndex("id"));
                String bookTitle=cursor.getString(cursor.getColumnIndex("title"));
                String bookAuthor=cursor.getString(cursor.getColumnIndex("author"));
                String genre=cursor.getString(cursor.getColumnIndex("genre"));
                String exchangeMethod=cursor.getString(cursor.getColumnIndex("exchangeMethod"));
                String location=cursor.getString(cursor.getColumnIndex("location"));
                String imageURL=cursor.getString(cursor.getColumnIndex("imageURL"));
                double rating=Double.parseDouble(cursor.getString(cursor.getColumnIndex("rating")));
                double ratingsNo=Double.parseDouble(cursor.getString(cursor.getColumnIndex("ratingsNo")));
                if(imageURL.isEmpty())
                   imageURL="https://www.google.ro/search?q=book&source=lnms&tbm=isch&sa=X&ved=0ahUKEwiUg4zo2u7XAhWEWRQKHTWjCrMQ_AUICigB&biw=1229&bih=568#imgrc=-5-48n6dvPGAAM:";
                Book b=new Book(id,bookTitle,bookAuthor,genre,exchangeMethod,location,imageURL,rating,ratingsNo);
                recordList.add(b);
            }while(cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return recordList;
    }

    public Book readSingleRecord(int bookId)
    {
        Book b=null;
        String sql="Select * from books where id= "+bookId;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            String id=cursor.getString(cursor.getColumnIndex("id"));
            String bookTitle=cursor.getString(cursor.getColumnIndex("title"));
            String bookAuthor=cursor.getString(cursor.getColumnIndex("author"));
            String genre=cursor.getString(cursor.getColumnIndex("genre"));
            String exchangeMethod=cursor.getString(cursor.getColumnIndex("exchangeMethod"));
            String location=cursor.getString(cursor.getColumnIndex("location"));
            String imageURL=cursor.getString(cursor.getColumnIndex("imageURL"));
            double rating=Double.parseDouble(cursor.getString(cursor.getColumnIndex("rating")));
            double ratingsNo=Double.parseDouble(cursor.getString(cursor.getColumnIndex("ratingsNo")));
            if(imageURL.isEmpty())
                imageURL="https://www.google.ro/search?q=book&source=lnms&tbm=isch&sa=X&ved=0ahUKEwiUg4zo2u7XAhWEWRQKHTWjCrMQ_AUICigB&biw=1229&bih=568#imgrc=-5-48n6dvPGAAM:";
            b=new Book(id,bookTitle,bookAuthor,genre,exchangeMethod,location,imageURL,rating,ratingsNo);
        }
        cursor.close();
        db.close();
        return b;
    }

    public boolean update(Book book)
    {
        ContentValues values=new ContentValues();
        values.put("author",book.getAuthor());
        values.put("title",book.getTitle());
        values.put("genre",book.getGenre());
        values.put("exchangeMethod",book.getExchangeMethod());
        values.put("location",book.getLocation());
        values.put("imageURL",book.getImageURL());
        values.put("rating",book.getRating());
        values.put("ratingsNo",book.getRatingsNo());
        String where = "id = ?";
        String[] whereArgs = { book.getId() };

        SQLiteDatabase db=this.getWritableDatabase();

        boolean updateSuccessful = db.update("books", values, where, whereArgs) > 0;
        db.close();

        return updateSuccessful;
    }

    public boolean delete(int id)
    {
        boolean deleteSuccessful = false;

        SQLiteDatabase db = this.getWritableDatabase();
        deleteSuccessful = db.delete("books", "id ='" + id + "'", null) > 0;
        db.close();

        return deleteSuccessful;
    }

}
