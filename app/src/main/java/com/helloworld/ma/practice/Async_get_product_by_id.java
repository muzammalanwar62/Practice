package com.helloworld.ma.practice;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
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

public class Async_get_product_by_id extends AsyncTask<String,Void,Void> {
    String catogery;
    Context context;
    List<products> productsList;
    StringBuffer stringbuffer;
    List<UsersDB> userinfo;
    HttpURLConnection con;

    public Async_get_product_by_id(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(String... strings) {
        catogery=strings[0];
        stringbuffer = new StringBuffer();
        try {
            URL url=new URL("https://helloworldshopingmall.000webhostapp.com/get_product_by_id_myshop.php");
            con= (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));
            String emc=URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode(catogery,"UTF-8");
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

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(context);
        userinfo = new Gson().fromJson(sharedPreferences.getString("userinfo",""),new TypeToken< List<UsersDB>>(){}.getType());
        if(productsList!=null&& productsList.size()>0) {
            boolean inserted = new dbhelper(context).insert_product_toshoppingcart(productsList.get(0).product_name, productsList.get(0).product_price, productsList.get(0).product_image,1 , userinfo.get(0).email,productsList.get(0).product_weight, String.valueOf(productsList.get(0).id));
            if (inserted) {
                Toast.makeText(context, "item inserted", Toast.LENGTH_LONG).show();
                Cursor cursor= new dbhelper(context).select_product_by_name_and_weight(productsList.get(0).product_name,productsList.get(0).product_weight,String.valueOf(productsList.get(0).product_price));
                if(cursor.getCount()>0){

                      new dbhelper(context).update_quantity(cursor.getString(1),cursor.getString(6),cursor.getInt(2),cursor.getInt(4));
                }
                Intent intent = new Intent(context, items_list_page.class);
                context.startActivity(intent);
            } else {
                Toast.makeText(context, "item not inserted", Toast.LENGTH_LONG).show();

            }
        }
    }
}
