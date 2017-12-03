package com.example.dianaivan.booksapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Diana Ivan on 11/5/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION=1;
    protected static final String DATABASE_NAME="BookDatabase";


    public DatabaseHandler(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db )
    {
        String sql="CREATE TABLE books"+"(id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "title TEXT, "+
                "author TEXT, "+
                "genre TEXT, "+
                "exchangeMethod TEXT, "+
                "location TEXT," +
                "imageURL TEXT ) ";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        String sql="DROP TABLE IF EXISTS books";
        db.execSQL(sql);
        onCreate(db);
    }
}
