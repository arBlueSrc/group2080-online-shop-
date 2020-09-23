package com.appnita.digikala;

import com.appnita.digikala.retrofit.basket.BuyProduct;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BuyProductClassForRecycler {
    public BuyProductClassForRecycler(String orderKey, String productID, String email, String key) {
        this.orderKey = orderKey;
        this.ProductID = productID;
        this.email = email;
        this.key = key;
    }

    public String getOrderKey() {
        return orderKey;
    }

    public void setOrderKey(String orderKey) {
        this.orderKey = orderKey;
    }


    private String orderKey;

    private String ProductID;

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String email;


    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
