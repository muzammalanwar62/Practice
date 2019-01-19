package com.helloworld.ma.practice;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

public class items_list_adapter extends RecyclerView.Adapter<items_list_adapter.item_list_viewholder> {
    List<products> items;
    Context context;
    int quantity;

    public items_list_adapter(List<products> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public item_list_viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.shopping_list_layout,viewGroup,false);
       return new item_list_viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final item_list_viewholder item_list_viewholder, final int i) {
        Picasso.get().load(items.get(i).product_image).into(item_list_viewholder.productimage);
        item_list_viewholder.productname.setText(items.get(i).product_name);
        item_list_viewholder.productprice.setText("Rs"+String.valueOf(items.get(i).product_price));
        item_list_viewholder.productweight.setText(items.get(i).product_weight);
        item_list_viewholder.productquantity.setText("x"+String.valueOf(items.get(i).product_quantity));
        item_list_viewholder.itemcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(context,item_list_viewholder.itemcard);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.pop_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(context,"You Clicked : " + String.valueOf(i), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });

                popup.show();//showing popup menu

            }
        });
        Log.e("product_id",String.valueOf(items.get(i).id));
        item_list_viewholder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer rows = new dbhelper(context).delete(String.valueOf(items.get(i).id));
                if (rows > 0) {
                    items.remove(item_list_viewholder.getAdapterPosition());
                    notifyItemRemoved(i);
                    notifyItemRangeChanged(item_list_viewholder.getAdapterPosition(),getItemCount());
                    notifyDataSetChanged();
                }else{
                    Toast.makeText(context,"Item not Removed From Cart",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class item_list_viewholder extends RecyclerView.ViewHolder{
        ImageView productimage;
        TextView productname,productweight,productprice,productquantity;
        Button remove;
        CardView itemcard;
       public item_list_viewholder(@NonNull View itemView) {
           super(itemView);
           productimage=itemView.findViewById(R.id.productImage);
           productname=itemView.findViewById(R.id.productName);
           productweight=itemView.findViewById(R.id.productweight);
           productprice=itemView.findViewById(R.id.productPrice);
           productquantity=itemView.findViewById(R.id.productquantity);
           remove=itemView.findViewById(R.id.excludefromcart);
           itemcard=itemView.findViewById(R.id.itemCard);
       }
   }
}
