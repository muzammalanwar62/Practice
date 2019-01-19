package com.helloworld.ma.practice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by MA on 7/17/2017.
 */

public class CartDB extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "cart.db";
    public static final String TABLE_NAME = "cart_item_table";
    public static final String COL_1 = "product_id";
    public static final String COL_2 = "product_name";
    public static final String COL_3 = "product_price";
   // public static final String COL_4 = "product_discount";
    public static final String COL_5 = "product_quantity";
    public static final String COL_6 = "product_image";
    public static final String COL_7 = "weight";

   String [] column = {COL_1, COL_2,COL_3,COL_5,COL_6,COL_7};
    public CartDB(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" ( product_id INTEGER PRIMARY KEY AUTOINCREMENT,product_name TEXT,product_price INTEGER,product_quantity INTEGER, product_image TEXT,weight TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
    public Boolean insert_item_to_cart(String name,int price,int quantity,String image,String username){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COL_2,name);
        cv.put(COL_3,price);
        cv.put(COL_5,quantity);
        cv.put(COL_6,image);
        cv.put(COL_7,username);
     long i=db.insert(TABLE_NAME, null, cv);
        if(i==-1){
            return false;
        }else{
            return true;
        }
    }
    public Cursor get_products_by_username(String username) {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(TABLE_NAME, column,"username = ?", new String[]{username}, null, null, null, null);
    }
    public void delete_product (String username){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME,"username = ?",new String[]{username});
    }
    public void delete_by_id(int id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME,"product_id = ?",new String[]{String.valueOf(id)});
    }
}
