package com.appnita.digikala.retrofit.basket;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BuyProduct {

    public BuyProduct(String orderKey, List<ProductId> lineItems, Billing billing, String key) {
        this.orderKey = orderKey;
        this.lineItems = lineItems;
        this.billing = billing;
        this.key = key;
    }

    @SerializedName("id")
    @Expose
    private int id;


    @SerializedName("order_key")
    @Expose
    private String orderKey;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderKey() {
        return orderKey;
    }

    public void setOrderKey(String orderKey) {
        this.orderKey = orderKey;
    }

    public List<ProductId> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<ProductId> lineItems) {
        this.lineItems = lineItems;
    }

    public Billing getBilling() {
        return billing;
    }

    public void setBilling(Billing billing) {
        this.billing = billing;
    }

    @SerializedName("line_items")
    @Expose
    private List<ProductId> lineItems = null;

    public class ProductId {
        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        @SerializedName("product_id")
        @Expose
        private String productId;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @SerializedName("name")
        @Expose
        private String name;
    }


    @SerializedName("billing")
    @Expose
    private Billing billing;

    public class Billing {
        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        @SerializedName("email")
        @Expose
        private String email;

    }

    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
