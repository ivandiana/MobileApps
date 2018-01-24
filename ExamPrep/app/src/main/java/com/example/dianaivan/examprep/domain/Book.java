package com.example.dianaivan.examprep.domain;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

/**
 * Created by Diana Ivan on 1/22/2018.
 */

@Entity(tableName="books")
public class Book {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private Date date;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Book(int id, String title, Date date)
    {
        this.id=id;
        this.title=title;
        this.date=date;

    }
    public Book()
    {
        this.id=1;
        this.title="";
        this.date=new Date();
    }


}
