package com.example.cakestore;

public class SubItem {
//    private int subItemImage;
    private String subItemTitle;
    private String cost;
    private String qty;


    public SubItem(String subItemTitle, String cost, String qty) {
        this.subItemTitle = subItemTitle;
        this.cost = cost;
        this.qty = qty;
    }

    public String getCost() {
        return cost;
    }

    public String getQty() {
        return qty;
    }

//    public void setSubItemImage(int subItemImage) {
//        this.subItemImage = subItemImage;
//    }

    public String getSubItemTitle() {
        return subItemTitle;
    }

    public void setSubItemTitle(String subItemTitle) {
        this.subItemTitle = subItemTitle;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

//    public String getSubItemDesc() {
//        return subItemDesc;
//    }
//
//    public void setSubItemDesc(String subItemDesc) {
//        this.subItemDesc = subItemDesc;
//    }
}