package org.example.businessLayer;

import org.example.dataAccessLayer.OrderDAO;
import org.example.model.Order;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Business Logic Layer (BLL) for managing orders.
 */
public class OrderBLL {
    private OrderDAO orderDAO;

    /**
     * Constructs a new OrderBLL with the specified database connection.
     *
     * @param connection the database connection
     */
    public OrderBLL(Connection connection) {
        this.orderDAO = new OrderDAO(connection);
    }

    /**
     * Adds a new order to the database.
     *
     * @param order the order to add
     * @throws SQLException if a database access error occurs
     */
    public void addOrder(Order order) throws SQLException {
        orderDAO.insert(order);
    }

    /**
     * Retrieves an order by ID.
     *
     * @param id the ID of the order to retrieve
     * @return the retrieved order, or null if not found
     * @throws SQLException if a database access error occurs
     */
    public Order getOrderById(int id) throws SQLException {
        return orderDAO.getById(id);
    }

    /**
     * Retrieves all orders.
     *
     * @return a list of all orders
     * @throws SQLException if a database access error occurs
     */
    public List<Order> getAllOrders() throws SQLException {
        return orderDAO.getAll();
    }

    // Additional methods for order-related business logic can be added here
}
