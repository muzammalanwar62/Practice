package com.helloworld.ma.practice;

public class products {
    String product_name,product_catagory,product_weight,product_image;
    int product_price,product_quantity;
    long product_ID;
     int id;
    public products(String product_name, String product_weight, String product_image, int product_price, int product_quantity,int id) {
        this.product_name = product_name;
        this.product_weight = product_weight;
        this.product_image = product_image;
        this.product_price = product_price;
        this.product_quantity = product_quantity;
         this.id=id;
    }
}
