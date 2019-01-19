package com.helloworld.ma.practice;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Add_product extends AppCompatActivity {
    ImageView imageView;
    EditText name,price,quantity,weight;
    Spinner category;
    Bitmap bitmap;
    Button submit;
    private int PICK_IMAGE_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        name=findViewById(R.id.product_add_name);
        price=findViewById(R.id.product_add_price);
        quantity=findViewById(R.id.product_add_quantity);
        weight=findViewById(R.id.product_add_weight);
        category=findViewById(R.id.product_add_category);
        submit=findViewById(R.id.product_add_btn);
        new Async_get_categories(category,this).execute();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                byte[] byteArrayImage = baos.toByteArray();
                String encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
                if(getIntent().getStringExtra("barcode")!=null) {
                    new Async_add_product(Add_product.this).execute(name.getText().toString(), quantity.getText().toString(), price.getText().toString(), weight.getText().toString(), category.getSelectedItem().toString(), encodedImage, getIntent().getStringExtra("barcode"));
                }
                else{
                    new Async_manually_add_product(Add_product.this).execute(name.getText().toString(), quantity.getText().toString(), price.getText().toString(), weight.getText().toString(), category.getSelectedItem().toString(), encodedImage);
                }
            }
        });
        imageView=findViewById(R.id.product_add_image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
// Show only images, no videos or anything else
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
// Always show the chooser (if there are multiple options available)
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                 bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));

                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
