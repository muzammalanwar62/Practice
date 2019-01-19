package com.helloworld.ma.practice;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class Edit_product_detail extends AppCompatActivity {
     EditText id,name,quantity,price,weight;
     Button submit;
     products productsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        id=findViewById(R.id.edit_id);
        name=findViewById(R.id.edit_name);
        quantity=findViewById(R.id.edit_quantity);
        price=findViewById(R.id.edit_price);
        weight=findViewById(R.id.edit_weight);
        submit=findViewById(R.id.edit_save);
       productsList= new Gson().fromJson(getIntent().getStringExtra("products"),products.class);
        name.setText(productsList.product_name);
        id.setText(String.valueOf(productsList.product_ID));
        quantity.setText(String.valueOf(productsList.product_quantity));
        price.setText(String.valueOf(productsList.product_price));
        weight.setText(productsList.product_weight);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Async_edit_product(Edit_product_detail.this).execute(name.getText().toString(),quantity.getText().toString(),price.getText().toString(),weight.getText().toString(),id.getText().toString());
            }
        });
    }

}
