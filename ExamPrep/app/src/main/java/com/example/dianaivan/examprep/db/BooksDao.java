package com.example.dianaivan.examprep.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.dianaivan.examprep.domain.Book;

import java.util.List;

/**
 * Created by Diana Ivan on 1/22/2018.
 */
//Data Objects - Class used to access the app's data :)

@Dao
public interface BooksDao {
    @Insert
    void addBook(Book book);

    @Insert
    void addBooks(List<Book> books);

    @Delete
    void deleteBook(Book b);

    @Query("delete from books")
    void deleteBooks();

    @Update
    void updateBook(Book b);

    @Query("select * from books")
    LiveData<List<Book>> getBooks();
}
