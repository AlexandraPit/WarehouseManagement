package org.example.model;

/**
 * Represents an order entity.
 */
public class Orders {
    private int order_id;
    private int client_id;
    private int product_id;
    private int quantity;

    /**
     * Constructs a new order with the specified attributes.
     *
     * @param order_id   the ID of the order
     * @param client_id  the ID of the client associated with the order
     * @param product_id the ID of the product in the order
     * @param quantity  the quantity of the product in the order
     */
    public Orders(int order_id, int client_id, int product_id, int quantity) {
        this.order_id = order_id;
        this.client_id = client_id;
        this.product_id = product_id;
        this.quantity = quantity;
    }

    /**
     * Constructs a new order with the specified attributes.
     *
     * @param client_id  the ID of the client associated with the order
     * @param product_id the ID of the product in the order
     * @param quantity  the quantity of the product in the order
     */
    public Orders(int client_id, int product_id, int quantity) {
        this.client_id = client_id;
        this.product_id = product_id;
        this.quantity = quantity;
    }

    /**
     * Gets the ID of the order.
     *
     * @return the ID of the order
     */
    public int getOrder_id() {
        return order_id;
    }

    /**
     * Sets the ID of the order.
     *
     * @param orderId the ID of the order
     */
    public void setOrder_id(int orderId) {
        this.order_id = orderId;
    }

    /**
     * Gets the ID of the client associated with the order.
     *
     * @return the ID of the client associated with the order
     */
    public int getClient_id() {
        return client_id;
    }

    /**
     * Sets the ID of the client associated with the order.
     *
     * @param clientId the ID of the client associated with the order
     */
    public void setClient_id(int clientId) {
        this.client_id = clientId;
    }

    /**
     * Gets the ID of the product in the order.
     *
     * @return the ID of the product in the order
     */
    public int getProduct_id() {
        return product_id;
    }

    /**
     * Sets the ID of the product in the order.
     *
     * @param productId the ID of the product in the order
     */
    public void setProduct_id(int productId) {
        this.product_id = productId;
    }

    /**
     * Gets the quantity of the product in the order.
     *
     * @return the quantity of the product in the order
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the product in the order.
     *
     * @param quantity the quantity of the product in the order
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
