package com.helloworld.ma.practice;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class Billing_activity extends AppCompatActivity {
RecyclerView checkout_items_list;
Toolbar toolbar;
ArrayList<products> products;
String discount_rate;
int total_price_of_item;
int discounted_rate;
int total_price_after_discount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
       setSupportActionBar(toolbar);
        products=new ArrayList<>();
        SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(this);
        checkout_items_list=findViewById(R.id.checkout_items_list);
        checkout_items_list.setLayoutManager(new LinearLayoutManager(this));
        List<UsersDB> userinfo=new Gson().fromJson(prefs.getString("userinfo",""),new TypeToken<List<UsersDB>>(){}.getType());

        Cursor products_cursur= new dbhelper(this).get_products_in_cart(userinfo.get(0).email);
        if(products_cursur.getCount()==0){
            Toast.makeText(this,"No Products",Toast.LENGTH_LONG).show();
        }else{
            while(products_cursur.moveToNext()){
                products p=new products(products_cursur.getString(1),products_cursur.getString(6),products_cursur.getString(3),products_cursur.getInt(2),products_cursur.getInt(4),products_cursur.getInt(0));
                products.add(p);
            }
            checkout_items_list.setAdapter(new purchased_item_adapter(products,this));
        }
        discount_rate=getIntent().getStringExtra("dis");
        total_price_of_item=new dbhelper(Billing_activity.this).getTotalOfAmount(userinfo.get(0).email);
          discounted_rate=(Integer.parseInt(discount_rate)*total_price_of_item)/100;
        total_price_after_discount=total_price_of_item-discounted_rate;
        Log.e("discounted_rate",discount_rate);
        Log.e("total_price",String.valueOf(discounted_rate));
        Log.e("total_price_after_dis",String.valueOf(total_price_after_discount));
    }
}
