package com.helloworld.ma.practice;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.webkit.URLUtil;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Async extends AsyncTask<String,Void,Void> {
    String email,password;
    List<UsersDB> users;
    Context context;
    StringBuffer stringbuffer;
    HttpURLConnection con;
    public Async(Context context) {

        this.context = context;

    }

    @Override
    protected Void doInBackground(String... strings) {
        email= strings[0];
        password=strings[1];
        stringbuffer = new StringBuffer();
        try {
            URL url=new URL("https://helloworldshopingmall.000webhostapp.com/login_myshop.php");
             con= (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));
            String emc=URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"+URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
            writer.write(emc);
            writer.flush();
            BufferedReader reader= new BufferedReader(new InputStreamReader(con.getInputStream()));
            String json;

            while ((json=reader.readLine())!=null){
                stringbuffer.append(json);
            }
            users = new Gson().fromJson(stringbuffer.toString(),new TypeToken< List<UsersDB>>(){}.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return  null;

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Intent intent=  new Intent(context,Home.class);
        Toast.makeText(context,"login",Toast.LENGTH_LONG).show();
        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putString("userinfo",new Gson().toJson(users)).apply();
        context.startActivity(intent);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
}
