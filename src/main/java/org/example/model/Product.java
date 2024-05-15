package org.example.model;

/**
 * Represents a product entity.
 */
public class Product {

    private int p_id;
    private String name;
    private double price;
    private int stock;

    /**
     * Constructs a new product with the specified attributes.
     *
     * @param p_id the ID of the product
     * @param name      the name of the product
     * @param price     the price of the product
     * @param stock     the stock quantity of the product
     */
    public Product(int productId, String name, double price, int stock) {
        this.p_id = productId;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    /**
     * Constructs a new product with the specified attributes.
     *
     * @param name  the name of the product
     * @param price the price of the product
     * @param stock the stock quantity of the product
     */
    public Product(String name, double price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    /**
     * Gets the ID of the product.
     *
     * @return the ID of the product
     */
    public int getP_id() {
        return p_id;
    }

    /**
     * Sets the ID of the product.
     *
     * @param productId the ID of the product
     */
    public void setP_id(int productId) {
        this.p_id = productId;
    }

    /**
     * Gets the name of the product.
     *
     * @return the name of the product
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the product.
     *
     * @param name the name of the product
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the price of the product.
     *
     * @return the price of the product
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of the product.
     *
     * @param price the price of the product
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets the stock quantity of the product.
     *
     * @return the stock quantity of the product
     */
    public int getStock() {
        return stock;
    }

    /**
     * Sets the stock quantity of the product.
     *
     * @param stock the stock quantity of the product
     */
    public void setStock(int stock) {
        this.stock = stock;
    }
}
