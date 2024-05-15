package org.example.businessLayer;

import org.example.dataAccessLayer.OrderDAO;
import org.example.model.Orders;

import java.sql.SQLException;
import java.util.List;

/**
 * Business Logic Layer (BLL) for managing orders.
 */
public class OrderBLL {
    private OrderDAO orderDAO;

    /**
     * Constructs an OrderBLL object and initializes the OrderDAO.
     */
    public OrderBLL() {
        this.orderDAO = new OrderDAO();
    }

    /**
     * Adds a new order to the database.
     *
     * @param orders the order to add
     * @throws SQLException if a database access error occurs
     */
    public void addOrder(Orders orders) {
        orderDAO.insert(orders);
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
        orderDAO.update(orders, "order_id", id);
    }

    /**
     * Deletes an order from the database by ID.
     *
     * @param id the ID of the order to delete
     */
    public void deleteOrder(int id) {
        orderDAO.delete("order_id", id);
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
