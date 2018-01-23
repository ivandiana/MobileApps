package com.example.dianaivan.examprep.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.example.dianaivan.examprep.domain.Book;

/**
 * Created by Diana Ivan on 1/22/2018.
 */

@Database(entities={Book.class},version=1)
@TypeConverters({Converters.class})
public abstract class BookDatabase extends RoomDatabase{
    public abstract  BooksDao getBooksDao();
}
