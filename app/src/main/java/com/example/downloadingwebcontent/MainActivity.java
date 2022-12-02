package com.example.downloadingwebcontent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    // first we call download task to extends the class Aysnctask so that we can do the download work on ackground thread.
    //Async task is for background work . the class.

public class DownloadTask extends AsyncTask<String,Void,String>{

    @Override
    protected String doInBackground(String... urls) {
//        final  String TAG="URL";
//        Log.i(TAG, strings[0]);
        String result="";
        //converting the urls that we get in function as url objects
        //a url object;
        URL url;
        //like browser reads text from url
        HttpURLConnection urlConnection= null;
        try {
            //converts string to url else no url or error go to catch
            url = new URL(urls[0]);
            //url connection made
            urlConnection = (HttpURLConnection) url.openConnection();
            //grapping data that comes to url
            InputStream in = urlConnection.getInputStream();
            //in gets the data, reader reads the data from in
            InputStreamReader reader = new InputStreamReader(in);
            //now start reading
            reader.read();
            //now collect data you get in data variable (reading in form of binary )
            int data = reader.read();
            while(data!=-1){//unless no character left
                //coverting data int char form;
                char current= (char) data;
                //now adding characters to form lines and words etc.
                result+= current;
                data=reader.read();
            }

            return result;



        } catch (Exception e) {
            e.printStackTrace();
            final String TAG="error";
            Log.i(TAG, "Something didn't work");
            return "failed";
        }

    }
}


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadTask task=new DownloadTask();
        // to receive download text here http of page from the doinbackground
        String res=null;
        try{
            //whatever comes from execution add to res
           res = task.execute("https://in.search.yahoo.com/?fr2=inr").get();

        }catch (Exception e){
           e.printStackTrace();
        }
        //checking if it works.
        final String TAG="result";
        Log.i(TAG,res);
    }
}