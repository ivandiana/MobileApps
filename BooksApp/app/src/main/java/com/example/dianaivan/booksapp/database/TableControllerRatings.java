package com.example.dianaivan.booksapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dianaivan.booksapp.Models.Book;
import com.example.dianaivan.booksapp.Models.Rating;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Diana Ivan on 12/4/2017.
 */

public class TableControllerRatings extends DatabaseHandler{

    public TableControllerRatings(Context context)
    {
        super(context);
    }

    //creating a new record
    public boolean create(Rating rating)
    {
        ContentValues values=new ContentValues();

        values.put("bookId",rating.getBookId());
        values.put("rating",rating.getRate());

        SQLiteDatabase db=this.getWritableDatabase();

        boolean createSuccesful=db.insert("ratings",null,values)>0;
        db.close();

        return createSuccesful;
    }

    //count records
    public int count(){
        SQLiteDatabase db=this.getWritableDatabase();
        String sql="Select * From ratings";
        int recordCount=db.rawQuery(sql,null).getCount();
        db.close();

        return recordCount;
    }

    public List<Rating> readByBookId(int bookId)
    {
        List<Rating> recordList=new ArrayList<Rating>();
        String sql="Select * from ratings where bookId= "+bookId;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor =db.rawQuery(sql,null);

        if(cursor.moveToFirst())
        {
            do{
                int id=Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                int bookid=Integer.parseInt(cursor.getString(cursor.getColumnIndex("bookId")));
                int rate=Integer.parseInt(cursor.getString(cursor.getColumnIndex("rating")));
                Rating r=new Rating(id,bookid,rate);
                recordList.add(r);
            }while(cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return recordList;
    }
}
