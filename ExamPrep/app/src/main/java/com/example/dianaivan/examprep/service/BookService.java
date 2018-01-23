package com.example.dianaivan.examprep.service;


import com.example.dianaivan.examprep.domain.Book;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Diana Ivan on 1/22/2018.
 */

public interface BookService {
    String SERVICE_ENDPOINT="http://10.131.9.32:3000";//"http://10.131.7.63:3000";//"http://10.0.2.2:3000";

    @GET("books")
    Observable<List<Book>> getBooks();

    @POST("book")
    Observable<Book> addBook(@Body Book b);
}
