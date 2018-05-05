package com.bharat.booklist;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText nameOfBook;
    private Button searchButton;
    private ListView bookListView;
    private ArrayList<BookClass> bookArrayList;
    private BookAdaptar bookAdaptar;
    private String urlString;
    private String query;
    private String urlString1;
    private String urlString2;
    private TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialiseVariables();
        Boolean isInternetAvailable = isNetworkConnected();
        if(isInternetAvailable==true) {
            searchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    query = String.valueOf(nameOfBook.getText());
                    urlString = urlString1 + query + urlString2;
                    BookAsync bookAsync = new BookAsync();
                    bookAsync.execute(urlString);
                }
            });
        }
        else {
            bookListView.setVisibility(View.GONE);
            error.setText("Internet Not Available");
            error.setVisibility(View.VISIBLE);
            nameOfBook.clearFocus();
        }

    }

        private boolean isNetworkConnected() {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

            return cm.getActiveNetworkInfo() != null;
        }


    private void initialiseVariables() {
        urlString1 = "https://www.googleapis.com/books/v1/volumes?q=";
        urlString2 = "&maxResults=16";
        nameOfBook = findViewById(R.id.bookName);
        searchButton = findViewById(R.id.searchButton);
        bookListView = findViewById(R.id.booksList);
        error = findViewById(R.id.error);
    }

    private class BookAsync extends AsyncTask<String,Void,ArrayList<BookClass>>{

        @Override
        protected ArrayList<BookClass> doInBackground(String... strings) {
            QueryUtils queryUtils = new QueryUtils(strings[0]);
            try {
                bookArrayList = queryUtils.fetchArrayList();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return bookArrayList;
        }

        @Override
        protected void onPostExecute(ArrayList arrayList) {
            super.onPostExecute(arrayList);
            bookArrayList = new ArrayList<>();
            bookAdaptar = new BookAdaptar(getApplicationContext(),R.layout.list_layout,arrayList);
            bookListView.setAdapter(bookAdaptar);

        }
    }


}
