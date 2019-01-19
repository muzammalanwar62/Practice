package com.helloworld.ma.practice;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class products_by_catogery_adapter extends RecyclerView.Adapter<products_by_catogery_adapter.productsviewholder> {

    List<products> productlist;
    SharedPreferences sharedPreferences;
    Context context;

    public products_by_catogery_adapter(List<products> productlist, Context context) {
        this.productlist = productlist;
        this.context = context;
    }

    @NonNull
    @Override
    public productsviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.catogery_list_layout,viewGroup,false);
        return new productsviewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull productsviewholder productsviewholder, final int i) {
        productsviewholder.catogery_name.setText(productlist.get(i).product_name);
        Picasso.get().load(productlist.get(i).product_image).into(productsviewholder.catogery_image);
        productsviewholder.catogery_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences=PreferenceManager.getDefaultSharedPreferences(context);
                List<UsersDB> userinfo=new Gson().fromJson(sharedPreferences.getString("userinfo",""),new TypeToken<List<UsersDB>>(){}.getType());
                if(userinfo.get(0).user_role.equals("retailer")){

                }else {
                    String products = new Gson().toJson(productlist.get(i));
                    context.startActivity(new Intent(context, Edit_product_detail.class).putExtra("products", products));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return productlist.size();
    }

    class productsviewholder extends RecyclerView.ViewHolder{
        ImageView catogery_image;
        TextView catogery_name;
        CardView catogery_card;

        public productsviewholder(@NonNull View itemView) {
            super(itemView);
            catogery_image=itemView.findViewById(R.id.catogery_image);
            catogery_name=itemView.findViewById(R.id.catogery_name);
            catogery_card=itemView.findViewById(R.id.catogery_card);
        }
    }
}
