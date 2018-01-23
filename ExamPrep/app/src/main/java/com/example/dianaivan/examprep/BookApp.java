package com.example.dianaivan.examprep;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.example.dianaivan.examprep.db.BookDatabase;
import com.example.dianaivan.examprep.domain.Book;

import timber.log.Timber;

/**
 * Created by Diana Ivan on 1/22/2018.
 */

public class BookApp extends Application{

    public BookDatabase db;

    @Override
    public void onCreate(){
        super.onCreate();

        Timber.plant(new Timber.DebugTree());
        db= Room.databaseBuilder(getApplicationContext(),
                BookDatabase.class,"database-name").build();

    }
}
