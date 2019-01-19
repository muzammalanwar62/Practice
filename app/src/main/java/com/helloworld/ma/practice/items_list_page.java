package com.helloworld.ma.practice;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class items_list_page extends AppCompatActivity {
RecyclerView item_list;
ArrayList<products> products;
Button proceed;
Button discount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_list_page);
        SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(this);
        products=new ArrayList<>();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        proceed=findViewById(R.id.proceed);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(items_list_page.this,Billing_activity.class);
                startActivity(i);
            }
        });
        discount=findViewById(R.id.discount);
        discount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder discount_dialog=new AlertDialog.Builder(items_list_page.this);
                discount_dialog.setTitle(" Give Discount");
                View discount_view=LayoutInflater.from(items_list_page.this).inflate(R.layout.discount_layout,null);
                final EditText discount_txt=discount_view.findViewById(R.id.discount_rate);
                discount_dialog.setPositiveButton("give Discount", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i=new Intent(items_list_page.this,Billing_activity.class);
                        i.putExtra("dis",discount_txt.getText().toString());
                        startActivity(i);
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setView(discount_view).show();
            }
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        item_list=findViewById(R.id.items_list);
        item_list.setLayoutManager(new LinearLayoutManager(this));
        List<UsersDB> userinfo=new Gson().fromJson(prefs.getString("userinfo",""),new TypeToken<List<UsersDB>>(){}.getType());

        Cursor products_cursur= new dbhelper(this).get_products_in_cart(userinfo.get(0).email);
        if(products_cursur.getCount()==0){
            Toast.makeText(this,"No Products",Toast.LENGTH_LONG).show();
        }else{
            while(products_cursur.moveToNext()){
                products p=new products(products_cursur.getString(1),products_cursur.getString(6),products_cursur.getString(3),products_cursur.getInt(2),products_cursur.getInt(4),products_cursur.getInt(0));
                products.add(p);
            }
            item_list.setAdapter(new items_list_adapter(products,this));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu_item, menu);
        MenuItem searchViewItem = menu.findItem(R.id.action_search);
        final SearchView searchViewAndroidActionBar = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchViewAndroidActionBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
