package com.helloworld.ma.practice;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Async_edit_product extends AsyncTask<String,Void,String> {
    StringBuffer stringbuffer;
    Context context;
HttpURLConnection con;
    String name,quantity,price,weight,id;

    public Async_edit_product(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        name=strings[0];
        quantity=strings[1];
        price=strings[2];
        weight=strings[3];
        id=strings[4];
        stringbuffer=new StringBuffer();
        try{
            URL url=new URL("https://helloworldshopingmall.000webhostapp.com/edit_product_detail.php");
            con= (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));
            String emc=URLEncoder.encode("Name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"+URLEncoder.encode("Quantity","UTF-8")+"="+URLEncoder.encode(quantity,"UTF-8")+"&"+URLEncoder.encode("Price","UTF-8")+"="+URLEncoder.encode(price,"UTF-8")+"&"+URLEncoder.encode("weight","UTF-8")+"="+URLEncoder.encode(weight,"UTF-8")+"&"+URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode(id,"UTF-8");
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
        Log.e("status",s);
        Toast.makeText(context,s,Toast.LENGTH_LONG).show();
    }
}
