package com.helloworld.ma.practice;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class catogery_adapter extends RecyclerView.Adapter<catogery_adapter.catogery_viewholder> {
List<catogery> catogeries;
Context context;

    public catogery_adapter(List<catogery> catogeries, Context context) {
        this.catogeries = catogeries;
        this.context = context;
    }

    @NonNull
    @Override
    public catogery_viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.catogery_list_layout,viewGroup,false);
       return new catogery_viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final catogery_viewholder catogery_viewholder, int i) {
        catogery_viewholder.catogery_name.setText(catogeries.get(i).name);
        Picasso.get().load(catogeries.get(i).image).into(catogery_viewholder.catogery_image);
        catogery_viewholder.catogery_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             context.startActivity(new Intent(context,products_page.class).putExtra("catogery",catogery_viewholder.catogery_name.getText().toString()));
            }
        });

    }

    @Override
    public int getItemCount() {
      return  catogeries.size();
    }

    class catogery_viewholder extends RecyclerView.ViewHolder{
        ImageView catogery_image;
        TextView catogery_name;
        CardView catogery_card;
        public catogery_viewholder(@NonNull View itemView) {
            super(itemView);
            catogery_image=itemView.findViewById(R.id.catogery_image);
            catogery_name=itemView.findViewById(R.id.catogery_name);
            catogery_card=itemView.findViewById(R.id.catogery_card);

        }
    }
}
