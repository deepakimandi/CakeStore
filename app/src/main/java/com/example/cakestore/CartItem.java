package com.example.cakestore;

public class CartItem {
    private String product_title;
    private int product_image;
    private int qty;
    private int cost;
    private int id;


    public CartItem(){

    }

    public CartItem(String product_title, int product_image, int qty, int cost, int id){
        this.product_title = product_title;
        this.product_image = product_image;
        this.qty = qty;
        this.cost = cost;
        this.id = id;
    }

    public String getProduct_title(){
        return this.product_title;
    }

    public int getProduct_image(){
        return this.product_image;
    }

    public int getQty(){
        return this.qty;
    }

    public int getCost(){
        return this.cost;
    }

    public int getId(){
        return this.id;
    }

    public void setProduct_title(String product_title){
        this.product_title = product_title;
    }

    public void setProduct_image(int product_image){
        this.product_image = product_image;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setId(int id) {
        this.id = id;
    }
}
