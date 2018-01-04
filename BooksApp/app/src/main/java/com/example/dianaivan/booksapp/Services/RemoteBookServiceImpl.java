package com.example.dianaivan.booksapp.Services;

import com.example.dianaivan.booksapp.Constants.Strings;
import com.example.dianaivan.booksapp.Models.Book;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;


/**
 * Created by Diana Ivan on 1/3/2018.
 */

public class RemoteBookServiceImpl {
    private static Retrofit retrofit=new Retrofit.Builder()
            .baseUrl(Strings.REMOTE_SERVICE_URL)
            .addConverterFactory(GsonConverterFactory.create()) //convert objects to/from json
            .build();

    private static RemoteBookServiceInterface serviceInterface=null;

    public static RemoteBookServiceInterface getInstance()
    {
        if(serviceInterface==null)
        {
            serviceInterface=retrofit.create(RemoteBookServiceInterface.class);
        }
        return serviceInterface;
    }

    public interface RemoteBookServiceInterface {

        @PUT("/books/{title}.json")
        Call<Book> createBook(@Path("title") String title, @Body Book book);

        @GET("/books/{title}.json")
        Call<Book> getBook(@Path("title") String title);

        @GET("/books/.json")
        Call<Map<String,Book>> getAllBooks();

        @DELETE("/books/{title}.json")
        Call<Book> deleteBook(@Path("title") String title);
    }

}
