package com.example.cakestore;

public class Product {
    private String product_title;
    private String product_desc;

    private int product_id;
    private int product_cat;
    private int product_image;
    private int product_price;

    public Product(){

    }

    public Product(String product_title, String product_desc, int product_id, int product_cat, int product_image, int product_price){
        this.product_title = product_title;
        this.product_desc = product_desc;
        this.product_id = product_id;
        this.product_cat = product_cat;
        this.product_image = product_image;
        this.product_price = product_price;
    }

    public String getProduct_title(){
        return this.product_title;
    }

    public String getProduct_desc(){
        return this.product_desc;
    }

    public int getProduct_id(){
        return this.product_id;
    }

    public int getProduct_cat(){
        return this.product_cat;
    }

    public int getProduct_image(){
        return this.product_image;
    }

    public int getProduct_price(){
        return this.product_price;
    }

    public void setProduct_title(String product_title){
        this.product_title = product_title;
    }

    public void setProduct_desc(String product_desc){
        this.product_desc = product_desc;
    }

    public void setProduct_id(int product_id){
        this.product_id = product_id;
    }

    public void setProduct_cat(int product_cat){
        this.product_cat = product_cat;
    }

    public void setProduct_image(int product_image){
        this.product_image = product_image;
    }
    public void setProduct_price(int product_price){
        this.product_price = product_price;
    }
}
