package com.helloworld.ma.practice;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class purchased_item_adapter extends RecyclerView.Adapter<purchased_item_adapter.purchased_viewhlder> {
   List<products> items_list;
   Context context;

    public purchased_item_adapter(List<products> items_list, Context context) {
        this.items_list = items_list;
        this.context = context;
    }

    @NonNull
    @Override
    public purchased_viewhlder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.purchased_item_layout,viewGroup,false);
        return new purchased_viewhlder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull purchased_viewhlder purchased_viewhlder, int i) {
        purchased_viewhlder.checkout_item_name.setText(items_list.get(i).product_name);
        purchased_viewhlder.checkout_item_price.setText("Rs "+String.valueOf(items_list.get(i).product_price));
        purchased_viewhlder.checkout_item_quantity.setText("x"+String.valueOf(items_list.get(i).product_quantity));
    }

    @Override
    public int getItemCount() {
        return items_list.size();
    }

    class purchased_viewhlder extends RecyclerView.ViewHolder {
        TextView checkout_item_name;
        TextView checkout_item_price;
        TextView checkout_item_quantity;
        public purchased_viewhlder(@NonNull View itemView) {
            super(itemView);
            checkout_item_name=itemView.findViewById(R.id.checkout_item_name);
            checkout_item_price=itemView.findViewById(R.id.checkout_item_price);
            checkout_item_quantity=itemView.findViewById(R.id.checkout_item_quantity);
        }
    }
}
