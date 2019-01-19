package com.helloworld.ma.practice;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Async_add_category extends AsyncTask<String,Void,String> {
    StringBuffer stringbuffer;
    Context context;
HttpURLConnection con;
    String name,image;

    public Async_add_category(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        name=strings[0];
        image=strings[1];
        stringbuffer=new StringBuffer();
        try{
            URL url=new URL("https://helloworldshopingmall.000webhostapp.com/add_category_myshop.php");
            con= (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));
            String emc=URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"+URLEncoder.encode("Image","UTF-8")+"="+URLEncoder.encode(image,"UTF-8");
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
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Toast.makeText(context,s,Toast.LENGTH_LONG).show();
    }
}
