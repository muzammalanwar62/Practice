package com.helloworld.ma.practice;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

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

public class Async_add_product extends AsyncTask<String,Void,String> {
    StringBuffer stringbuffer;
    Context context;


    HttpURLConnection con;
    String id,name,quantity,price,weight,category,image;
    public Async_add_product(Context context) {
        this.context = context;
    }
    @Override
    protected String doInBackground(String... strings) {

        name=strings[0];
        quantity=strings[1];
        price=strings[2];
        weight=strings[3];
        category=strings[4];
        image=strings[5];
        id=strings[6];
        stringbuffer=new StringBuffer();
        try{
        URL url=new URL("https://helloworldshopingmall.000webhostapp.com/myshop_products.php");
        con= (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));
        String emc=URLEncoder.encode("Name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"+URLEncoder.encode("Catagory","UTF-8")+"="+URLEncoder.encode(category,"UTF-8")+"&"+URLEncoder.encode("Quantity","UTF-8")+"="+URLEncoder.encode(quantity,"UTF-8")+"&"+URLEncoder.encode("Price","UTF-8")+"="+URLEncoder.encode(price,"UTF-8")+"&"+URLEncoder.encode("weight","UTF-8")+"="+URLEncoder.encode(weight,"UTF-8")+"&"+URLEncoder.encode("Image","UTF-8")+"="+URLEncoder.encode(image,"UTF-8")+"&"+URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode(id,"UTF-8");
        writer.write(emc);
        writer.flush();
        BufferedReader reader= new BufferedReader(new InputStreamReader(con.getInputStream()));
        String json;

        while ((json=reader.readLine())!=null){
            stringbuffer.append(json);
        }
       return stringbuffer.toString();
    } catch (Exception e) {
        e.printStackTrace();
    }

        return stringbuffer.toString();
    }

    @Override
    protected void onPostExecute(String s) {

        super.onPostExecute(s);
        Log.e("add product",s);
        Toast.makeText(context,s,Toast.LENGTH_LONG).show();
    }
}
