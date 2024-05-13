package org.example.model;

public class Order {
    private int orderId;
    private int clientId;
    private int productId;
    private int quantity;
    public Order(int orderId, int clientId, int productId, int quantity) {
        this.orderId = orderId;
        this.clientId = clientId;
        this.productId = productId;
        this.quantity = quantity;

    }

    public Order( int clientId, int productId, int quantity) {

        this.clientId = clientId;
        this.productId = productId;
        this.quantity = quantity;

    }
    public int getOrderId() {
        return orderId;

    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    public int getClientId() {
        return clientId;
    }
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
    public int getProductId() {
        return productId;
    }
    public void setProductId(int productId) {
        this.productId = productId;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
