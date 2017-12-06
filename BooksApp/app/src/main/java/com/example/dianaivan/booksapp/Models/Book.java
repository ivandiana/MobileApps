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
    private String imageURL;
    private double rating;
    public Book(){}

    public Book(String _title,String _author, String _genre,String _exchangeMethod,String _location,String _imageURL,double _rating){
     author=_author;
     title=_title;
     genre=_genre;
     exchangeMethod=_exchangeMethod;
     location=_location;
     imageURL=_imageURL;
     rating=_rating;
    }

    public Book(int _id,String _title,String _author, String _genre,String _exchangeMethod,String _location,String _imageURL,double _rating){
        id=_id;
        author=_author;
        title=_title;
        genre=_genre;
        exchangeMethod=_exchangeMethod;
        location=_location;
        imageURL=_imageURL;
        rating=_rating;
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

    public String getImageURL(){return  imageURL;}

    public void setImageURL(String URL)
    {
        this.imageURL=URL;
    }
    public double getRating(){
        return rating;
    }

    public void setRating(double value)
    {
        rating=value;
    }
    public String toString()
    {
        return "\nTitle: "+title+"\nAuthor: "+author
                +"\nGenre: "+genre+"\nExchange method: "+exchangeMethod
                +"\nLocation: "+location;
    }
}
