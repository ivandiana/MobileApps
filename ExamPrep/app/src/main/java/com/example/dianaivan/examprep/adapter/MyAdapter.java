package com.example.dianaivan.examprep.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dianaivan.examprep.R;
import com.example.dianaivan.examprep.domain.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Diana Ivan on 1/22/2018.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>
{
    private List<Book> mValues;

    public MyAdapter()
    {
        mValues=new ArrayList<>();
    }
    public void setData(List<Book> books)
    {
        mValues=books;
        notifyDataSetChanged();

    }

    public void clear()
    {
        mValues.clear();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_book_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem=mValues.get(position);
        holder.mIdView.setText(String.valueOf(mValues.get(position).getId()));
        holder.mContentView.setText(mValues.get(position).getTitle());

        holder.mViews.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Context context=v.getContext();
                //TODO: add the detail activity here
               // Intent intent=new Intent(context,Ev)
            }

        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        final View mViews;
        final TextView mIdView;
        final TextView mContentView;
        Book mItem;

        ViewHolder(View view)
        {
            super(view);
            mViews=view;
            mIdView=view.findViewById(R.id.id);
            mContentView=view.findViewById(R.id.content);
        }
        @Override
        public String toString()
        {
            return super.toString()+" '"+mContentView.getText()+"'";
        }
    }
}
