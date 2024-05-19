package org.example.businessLayer;

import org.example.dataAccessLayer.OrderDAO;
import org.example.dataAccessLayer.ProductDAO;
import org.example.model.Orders;
import org.example.model.Product;

import java.sql.SQLException;
import java.util.List;

/**
 * Business Logic Layer (BLL) for managing orders.
 */
public class OrderBLL {
    private OrderDAO orderDAO;
    private ProductDAO productDAO;


    /**
     * Constructs an OrderBLL object and initializes the OrderDAO.
     */
    public OrderBLL() {
        this.orderDAO = new OrderDAO();
        this.productDAO = new ProductDAO();
    }

    /**
     * Adds a new order to the database.
     *
     * @param orders the order to add
     * @throws SQLException if a database access error occurs
     */
    public void addOrder(Orders orders) {
        Product product = productDAO.getById(orders.getProduct_id(),"p_id");
        if (orders.getQuantity() > product.getStock()) {
            throw new IllegalArgumentException("Not enough products in stock!");
        }

        orderDAO.insert(orders);
        product.setStock(product.getStock() - orders.getQuantity());
        productDAO.update(product,"p_id",product.getP_id());

    }

    /**
     * Retrieves an order by ID.
     *
     * @param id the ID of the order to retrieve
     * @return the retrieved order, or null if not found
     * @throws SQLException if a database access error occurs
     */
    public Orders getOrderById(int id) {
        return orderDAO.getById(id, "order_id");
    }

    /**
     * Updates an existing order in the database.
     *
     * @param orders the updated order object
     * @param id     the ID of the order to update
     */
    public void editOrder(Orders orders, int id) {
        Orders existingOrder = orderDAO.getById(id, "order_id");
        Product product = productDAO.getById(existingOrder.getProduct_id(),"p_id");

        // Restock the product with the quantity of the existing order
        product.setStock(product.getStock() + existingOrder.getQuantity());

        if (orders.getQuantity() > product.getStock()) {
            throw new IllegalArgumentException("Not enough products in stock!");
        }

        orderDAO.update(orders, "order_id", id);

        // Update product stock after the order edit
        product.setStock(product.getStock() - orders.getQuantity());
        productDAO.update(product,"p_id",product.getP_id());
    }

    /**
     * Deletes an order from the database by ID.
     *
     * @param id the ID of the order to delete
     */
    public void deleteOrder(int id) {
        Orders existingOrder = orderDAO.getById(id, "order_id");
        Product product = productDAO.getById(existingOrder.getProduct_id(),"p_id");

        // Restock the product with the quantity of the deleted order
        product.setStock(product.getStock() + existingOrder.getQuantity());

        orderDAO.delete("order_id", id);
        productDAO.update(product,"p_id",product.getP_id());
    }

    /**
     * Retrieves all orders from the database.
     *
     * @return a list of all orders
     * @throws SQLException if a database access error occurs
     */
    public List<Orders> getAllOrders() {
        return orderDAO.getAll();
    }

}
