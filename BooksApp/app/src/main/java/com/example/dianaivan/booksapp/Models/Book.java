package com.example.dianaivan.booksapp.Models;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Diana Ivan on 11/5/2017.
 */

@IgnoreExtraProperties
public class Book {
    private String id;
    private String title;
    private String author;
    private String genre;
    private String exchangeMethod;
    private String location;
    private String imageURL;
    private double rating;
    private double ratingsNo;
    public Book(){}

    public Book(String _title,String _author, String _genre,String _exchangeMethod,String _location,String _imageURL,double _rating,double _ratingsNo){
     author=_author;
     title=_title;
     genre=_genre;
     exchangeMethod=_exchangeMethod;
     location=_location;
     imageURL=_imageURL;
     rating=_rating;
     ratingsNo=_ratingsNo;
    }

    public Book(String _id,String _title,String _author, String _genre,String _exchangeMethod,String _location,String _imageURL,double _rating,double _ratingsNo){
        id=_id;
        author=_author;
        title=_title;
        genre=_genre;
        exchangeMethod=_exchangeMethod;
        location=_location;
        imageURL=_imageURL;
        rating=_rating;
        ratingsNo=_ratingsNo;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
    public double getRatingsNo(){
        return ratingsNo;
    }

    public void setRatingsNo(double value)
    {
        ratingsNo=value;
    }
    public String toString()
    {
        return "\nTitle: "+title+"\nAuthor: "+author
                +"\nGenre: "+genre+"\nExchange method: "+exchangeMethod
                +"\nLocation: "+location;
    }

    @Exclude
    public Map<String,Object> toMap()
    {
        HashMap<String,Object> res=new HashMap<>();
        res.put("id",id);
        res.put("title",title);
        res.put("author",author);
        res.put("genre",genre);
        res.put("exchangeMethod",exchangeMethod);
        res.put("location",location);
        res.put("imageURL",imageURL);
        res.put("rating",rating);
        res.put("ratingsNo",ratingsNo);
        return res;
    }
}
