package com.example.dianaivan.booksapp.Models;

/**
 * Created by Diana Ivan on 12/4/2017.
 */

public class Rating {
    private int id;
    private int bookId;
    private int rate;

    public Rating(int _bookId,int _rate)
    {
        bookId=_bookId;
        rate=_rate;
    }

    public Rating(int _id,int _bookId,int _rate)
    {
        id=_id;
        bookId=_bookId;
        rate=_rate;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
