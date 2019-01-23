package com.helloworld.ma.practice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class dbhelper extends SQLiteOpenHelper {
    Context c;
    String Table_Name="shoppingcart";
    String[] pricecolumn={"Sum(price)"};
    String[] columns={"id","productname","price","image","quantity","email","weight"};
    String creat_table="create table shoppingcart (id integer primary key autoincrement,productname text,price integer,image text,quantity integer,email text,weight text);";
    public dbhelper(Context context) {
        super(context,"shoppingcartdb", null, 1);
        this.c=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
sqLiteDatabase.execSQL(creat_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS shoppingcart");
        onCreate(sqLiteDatabase);
    }
    public Cursor select_product_by_name_and_weight(String name,String weight,String price){
        SQLiteDatabase db=getReadableDatabase();
// Execute
       Cursor cursor = db.query(Table_Name, columns, "productname = ? and weight = ? and price = ?", new String[]{name,weight,price}, null, null, null);
       return cursor;
    }
    public int update_quantity(String name,String weight,int price,int quantity){
        SQLiteDatabase db=getReadableDatabase();
        ContentValues cv = new ContentValues();
       // quantity=quantity+1;
        cv.put("quantity",quantity++); //These Fields should be your String values of actual column names
       return db.update(Table_Name, cv,"productname = ? AND weight = ? AND price = ?", new String[]{name,weight, String.valueOf(price)});

    }
    public Boolean insert_product_toshoppingcart(String productname,int price,String imageurl,int quantity,String Username,String weight,String id){
        Cursor c=select_product_by_name_and_weight(productname,weight,String.valueOf(price));
        if(c.getCount()==0) {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("productname ", productname);
            cv.put("price ", price);
            cv.put("image", imageurl);
            cv.put("quantity", quantity);
            cv.put("email", Username);
            cv.put("weight", weight);
            cv.put("id", id);
            long i = db.insert("shoppingcart", null, cv);
            if (i == -1) {
                return false;
            } else {
                return true;
            }
        }else{
            while(c.moveToNext()) {
              int updated=  update_quantity(productname, weight, price,c.getCount());
                Log.e("update",String.valueOf(updated));
              if(updated==0){
                  return false;
              }else {
                  return true;
              }
            }
        }
         return true;
    }
    public  Cursor get_products_in_cart(String Username)throws SQLException {
        SQLiteDatabase db= getReadableDatabase();
        Cursor products=db.query("shoppingcart",columns,"email = ?",new String[]{Username},null,null,null,null);
        return products;
    }
    public int get_num_of_rows(String Username){
        SQLiteDatabase db= getReadableDatabase();
        Cursor products=db.query("shoppingcart",columns,"email = ?",new String[]{Username},null,null,null,null);
        return products.getCount();
    }

    public int getTotalOfAmount(String Username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.query("shoppingcart",pricecolumn,"email = ?",new String[] {Username},null,null,null,null);
        cur.moveToFirst();
        int i = cur.getInt(0);
        cur.close();
        return i;
    }
    public Integer delete(String id){
        SQLiteDatabase sdb = this.getReadableDatabase();
        return sdb.delete(Table_Name, "id = ?", new String[] {id});
    }
    public void delete_all(String Username){
        SQLiteDatabase sdb = this.getReadableDatabase();
         sdb.delete("shoppingcart", "email = ?", new String[] {Username});
    }

}
