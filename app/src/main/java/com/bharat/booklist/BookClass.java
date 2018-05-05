package com.bharat.booklist;

/**
 * Created by Bharat on 2/26/2018.
 */

public class BookClass {

    String mName;
    String mAuthor;

    public BookClass(String bookName, String author){
        mName = bookName;
        mAuthor = author;
    }

    public String getName(){
        return mName;
    }

    public String getAuthor(){
        return mAuthor;
    }

}
