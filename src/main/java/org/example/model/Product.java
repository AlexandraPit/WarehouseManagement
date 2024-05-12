package org.example.model;

public class Product {

    private int p_id;
    private String name;
    private double price;
    private int stock;

    public Product(int p_id, String name, double price, int stock) {
        this.p_id = p_id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }
    public Product( String name, double price, int stock) {

        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public int getP_id() {
        return p_id;
    }
    public void setP_id(int id) {
        this.p_id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }

}
