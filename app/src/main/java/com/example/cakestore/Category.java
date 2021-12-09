package com.example.cakestore;

public class Category {
    private String cat_name;
    private int cat_image;
    private int cat_id;

    public Category(){

    }

    public Category(String cat_name, int cat_image, int cat_id){
        this.cat_name = cat_name;
        this.cat_image = cat_image;
        this.cat_id = cat_id;
    }

    public String getCat_name(){
        return this.cat_name;
    }

    public int getCat_image(){
        return this.cat_image;
    }

    public int getCat_id(){
        return this.cat_id;
    }

    public void setCat_name(String cat_name){
        this.cat_name = cat_name;
    }

    public void setCat_image(int cat_image){
        this.cat_image = cat_image;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }
}
