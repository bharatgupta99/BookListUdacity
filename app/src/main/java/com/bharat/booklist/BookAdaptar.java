package com.bharat.booklist;

import android.app.LauncherActivity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Bharat on 2/26/2018.
 */


public class    BookAdaptar extends ArrayAdapter {

    private int LayoutResource;

    public BookAdaptar(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        LayoutResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(LayoutResource,parent,false);
        }
        TextView bookName = convertView.findViewById(R.id.bookName);
        TextView authorName = convertView.findViewById(R.id.authorName);

        BookClass book = (BookClass) getItem(position);

        bookName.setText(book.getName());
        authorName.setText(book.getAuthor());


        return convertView;
    }
}
