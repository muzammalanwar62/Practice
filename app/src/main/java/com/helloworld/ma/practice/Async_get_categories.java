package com.helloworld.ma.practice;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Async_get_categories extends AsyncTask {
    HttpURLConnection con;
    StringBuffer stringbuffer;
    RecyclerView recyclerView;
    List<catogery> categories;
    Spinner spinner;
    Context context;
    ArrayList<String> spinner_catergeries;
    public Async_get_categories(Spinner spinner,Context context) {
        this.spinner = spinner;
        this.context=context;
        spinner_catergeries=new ArrayList<>();
    }

    public Async_get_categories(RecyclerView recyclerView, Context context) {
        this.recyclerView = recyclerView;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if(categories!=null&&categories.size()>0) {
            if(spinner!=null){
          for(int i=0;i<categories.size();i++){
              spinner_catergeries.add(categories.get(i).name);
          }
          Log.e("cat_names",String.valueOf(spinner_catergeries.size()));
           ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,spinner_catergeries);
          spinner.setAdapter(adapter);
       }else{
               Log.e("catorgery", stringbuffer.toString());
               recyclerView.setAdapter(new catogery_adapter(categories, context));
           }
       }

    }

    @Override
    protected Object doInBackground(Object[] objects) {
        stringbuffer=new StringBuffer();
        try {
            URL url=new URL("https://helloworldshopingmall.000webhostapp.com/getCategories_myshop.php");
            con= (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");


            BufferedReader reader= new BufferedReader(new InputStreamReader(con.getInputStream()));
            String json;

            while ((json=reader.readLine())!=null){
                stringbuffer.append(json);
            }
        categories=new Gson().fromJson(stringbuffer.toString(),new TypeToken< List<catogery>>(){}.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
