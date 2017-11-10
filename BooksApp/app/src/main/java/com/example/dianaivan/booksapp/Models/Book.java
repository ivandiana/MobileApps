package com.example.dianaivan.booksapp.Models;

/**
 * Created by Diana Ivan on 11/5/2017.
 */

public class Book {
    private int id;
    private String title;
    private String author;
    private String genre;
    private String exchangeMethod;
    private String location;
    public Book(){}

    public Book(String _title,String _author, String _genre,String _exchangeMethod,String _location){
     author=_author;
     title=_title;
     genre=_genre;
     exchangeMethod=_exchangeMethod;
     location=_location;
    }

    public Book(int _id,String _title,String _author, String _genre,String _exchangeMethod,String _location){
        id=_id;
        author=_author;
        title=_title;
        genre=_genre;
        exchangeMethod=_exchangeMethod;
        location=_location;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getExchangeMethod() {
        return exchangeMethod;
    }

    public void setExchangeMethod(String exchangeMethod) {
        this.exchangeMethod = exchangeMethod;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    public String toString()
    {
        return "\nTitle: "+title+"\nAuthor: "+author
                +"\nGenre: "+genre+"\nExchange method: "+exchangeMethod
                +"\nLocation: "+location;
    }
}
