package com.helloworld.ma.practice;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.android.gms.common.util.Strings;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

public class Async_product_by_catogery extends AsyncTask<String,Void,Void> {
    String catogery;
    Context context;
    List<products> productsList;
    RecyclerView recyclerView;
    StringBuffer stringbuffer;


    public Async_product_by_catogery(Context context, RecyclerView recyclerView) {
        this.context = context;
        this.recyclerView = recyclerView;
    }

    HttpURLConnection con;
    @Override
    protected Void doInBackground(String... strings) {
        catogery=strings[0];
        stringbuffer = new StringBuffer();
        try {
            URL url=new URL("https://helloworldshopingmall.000webhostapp.com/product_catagory_myshop.php");
            con= (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));
            String emc=URLEncoder.encode("Category","UTF-8")+"="+URLEncoder.encode(catogery,"UTF-8");
            writer.write(emc);
            writer.flush();
            BufferedReader reader= new BufferedReader(new InputStreamReader(con.getInputStream()));
            String json;

            while ((json=reader.readLine())!=null){
                stringbuffer.append(json);
            }
            productsList = new Gson().fromJson(stringbuffer.toString(),new TypeToken< List<products>>(){}.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return  null;

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.e("products",stringbuffer.toString());
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new products_by_catogery_adapter(productsList,context));
    }
}
