package com.example.cakestore;

import java.lang.reflect.Array;
import java.util.List;

public class OrderItem {
    private List<SubItem> orderitems;
    private String time_stamp;
    private String user_id;
    private String total_cost;
    private String payment_mode;


    public OrderItem() {

    }

    public OrderItem(List<SubItem> orderitems, String time_stamp, String user_id, String total_cost, String payment_mode) {
        this.orderitems = orderitems;
        this.time_stamp = time_stamp;
        this.user_id = user_id;
        this.total_cost = total_cost;
        this.payment_mode = payment_mode;
    }

    public String getTime_stamp() {
        return this.time_stamp;
    }

    public String getUser_id() {
        return this.user_id;
    }

    public String getTotal_cost() {
        return this.total_cost;
    }
    public String getPayment_mode() {
        return this.payment_mode;
    }

    public List<SubItem> getOrderitems() {
        return this.orderitems;
    }


    public void setOrderitems(List<SubItem> orderitems) {
        this.orderitems = orderitems;
    }

    public void setTime_stamp(String time_stamp) {
        this.time_stamp = time_stamp;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setTotal_cost(String user_id) {
        this.total_cost = total_cost;
    }

    public void setPayment_mode(String user_id) {
        this.payment_mode = payment_mode;
    }
}