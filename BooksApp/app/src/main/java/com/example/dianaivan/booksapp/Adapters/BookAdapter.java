package com.example.dianaivan.booksapp.Adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dianaivan.booksapp.Models.Book;
import com.example.dianaivan.booksapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Diana Ivan on 12/3/2017.
 */

public class BookAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Book> mDataSource;

    public BookAdapter(Context context,List<Book> items)
    {
        mContext=context;
        mDataSource=items;
        mInflater=(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    //1
    @Override
    public int getCount() {
        return mDataSource.size();
    }

    //2
    @Override
    public Object getItem(int position) {
        return mDataSource.get(position);
    }

    //3
    @Override
    public long getItemId(int position) {
        return position;
    }

    //4
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get view for row item
        View rowView = mInflater.inflate(R.layout.list_item_book, parent, false);

        // Get title element
        TextView titleTextView = rowView.findViewById(R.id.book_list_title);

        // Get subtitle element
        TextView subtitleTextView = rowView.findViewById(R.id.book_list_author);

        // Get detail element
        TextView detailTextView = rowView.findViewById(R.id.book_list_detail);

        // Get thumbnail element
        ImageView thumbnailImageView = rowView.findViewById(R.id.book_list_thumbnail);

        Book book = (Book) getItem(position);

        titleTextView.setText(book.getTitle());
        subtitleTextView.setText(book.getAuthor());
        detailTextView.setText(book.getLocation());
        Picasso.with(mContext).load(book.getImageURL()).placeholder(R.mipmap.ic_launcher).into(thumbnailImageView);

        return rowView;
    }
}
