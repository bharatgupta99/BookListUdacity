package com.bharat.booklist;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by Bharat on 2/26/2018.
 */

public class QueryUtils {

    private String stringURL;

    public QueryUtils(String sURL){
        stringURL = sURL;
    }

    public ArrayList<BookClass> fetchArrayList() throws IOException {
        URL url = getURL(stringURL);
        String jsonString = fetchJSONandHTTP(url);
        ArrayList<BookClass> arrayList = fetchInfoFromJSON(jsonString);
        return arrayList;
    }

    private ArrayList<BookClass> fetchInfoFromJSON(String Json) {
        ArrayList<BookClass> arrayList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(Json);
            JSONArray itemArray = jsonObject.getJSONArray("items");
            for(int i=0;i<itemArray.length();i++){
                JSONObject specificItem = itemArray.getJSONObject(i);
                JSONObject volumeInfo = specificItem.getJSONObject("volumeInfo");
                String title = volumeInfo.getString("title");
                JSONArray authorArray = volumeInfo.getJSONArray("authors");
                String author = authorArray.getString(0)+"  ";

                arrayList.add(new BookClass(title,author));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            return arrayList;
        }
}

    private String fetchJSONandHTTP(URL url) throws IOException {
        String jsonString="";
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        try {
            if(httpURLConnection==null) {
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setReadTimeout(1000);
                httpURLConnection.setConnectTimeout(1500);
                httpURLConnection.connect();
                inputStream = httpURLConnection.getInputStream();
                jsonString = getJSON(inputStream);
                return jsonString;
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(httpURLConnection!=null) {
                httpURLConnection.disconnect();
            }
            if(inputStream != null){
                inputStream.close();
            }
        }
        return jsonString;
    }

    private String getJSON(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder;
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        stringBuilder = new StringBuilder();
        String line = bufferedReader.readLine();
        while (line!=null){
            stringBuilder.append(line);
            line = bufferedReader.readLine();
        }
        return stringBuilder.toString();
    }

    private URL getURL(String stringURL) {

        URL api_url = null;
        try {
            api_url = new URL(stringURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return api_url;
    }
}
