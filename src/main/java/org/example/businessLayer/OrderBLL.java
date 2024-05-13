package org.example.businessLayer;

import org.example.dataAccessLayer.OrderDAO;
import org.example.model.Order;
import org.example.model.Product;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Business Logic Layer (BLL) for managing orders.
 */
public class OrderBLL{
    private OrderDAO orderDAO;


    public OrderBLL() {
        this.orderDAO = new OrderDAO();
    }

    /**
     * Adds a new order to the database.
     *
     * @param order the order to add
     * @throws SQLException if a database access error occurs
     */
    public void addOrder(Order order)  {
        orderDAO.insert(order);
    }

    /**
     * Retrieves an order by ID.
     *
     * @param id the ID of the order to retrieve
     * @return the retrieved order, or null if not found
     * @throws SQLException if a database access error occurs
     */
    public Order getOrderById(int id)  {
        return orderDAO.getById(id);
    }

    public void editOrder(Order order, int id){
        orderDAO.update(order, id);
    }

    public void deleteOrder(int id){
        orderDAO.delete(id);
    }


    /**
     * Retrieves all orders.
     *
     * @return a list of all orders
     * @throws SQLException if a database access error occurs
     */
    public List<Order> getAllOrders()  {
        return orderDAO.getAllOrders();
    }

    // Additional methods for order-related business logic can be added here
}
