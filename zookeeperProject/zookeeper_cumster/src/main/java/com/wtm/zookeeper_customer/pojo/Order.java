package com.wtm.zookeeper_customer.pojo;

import java.io.Serializable;

public class Order implements Serializable {
    String id;
    String ordername;
    private Product product;

    public Order(String id, String ordername, Product product) {
        this.id = id;
        this.ordername = ordername;
        this.product = product;
    }

    public Order() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrdername() {
        return ordername;
    }

    public void setOrdername(String ordername) {
        this.ordername = ordername;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
