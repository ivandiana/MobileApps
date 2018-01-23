package com.example.dianaivan.examprep.db;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by Diana Ivan on 1/22/2018.
 */

public class Converters {
    @TypeConverter
    public Date fromTimestamp(Long value)
    {
        return value==null ? null : new Date(value);
    }

    @TypeConverter
    public Long dateToTimestamp(Date date)
    {
        if(date==null)
        {
            return null;
        }
        else
            return date.getTime();
    }
}
