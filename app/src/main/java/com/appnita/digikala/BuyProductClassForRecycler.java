package com.appnita.digikala;

public class BuyProductClassForRecycler {


    public String getOrderKey() {
        return orderKey;
    }

    public void setOrderKey(String orderKey) {
        this.orderKey = orderKey;
    }


    private String orderKey;

    private String ProductID;

    private String image;

    private String name;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    private String fileName;

    public BuyProductClassForRecycler(String orderKey, String productID, String image, String email, String key, String name, String fileName) {
        this.orderKey = orderKey;
        ProductID = productID;
        this.image = image;
        this.email = email;
        this.key = key;
        this.name = name;
        this.fileName = fileName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

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
