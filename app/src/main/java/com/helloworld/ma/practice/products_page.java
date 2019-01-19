package com.helloworld.ma.practice;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class products_page extends AppCompatActivity {
 RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getIntent().getStringExtra("catogery"));
        recyclerView=findViewById(R.id.product_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(products_page.this));
        new Async_product_by_catogery(products_page.this,recyclerView).execute(getIntent().getStringExtra("catogery"));

    }

}
