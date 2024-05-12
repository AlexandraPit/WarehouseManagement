package org.example.dataAccessLayer;

import org.example.model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    // You need a database connection
    private Connection connection;

    // Constructor to initialize the connection
    public OrderDAO(Connection connection) {
        this.connection = connection;
    }

    // Method to insert a new order into the database
    public void insert(Order order) throws SQLException {
        String sql = "INSERT INTO orders (id, product_id, client_id, quantity) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, order.getOrderId());
            statement.setInt(2, order.getProductId());
            statement.setInt(3, order.getClientId());
            statement.setInt(4, order.getQuantity());
            statement.executeUpdate();
        }
    }

    // Method to retrieve an order by ID from the database
    public Order getById(int id) throws SQLException {
        String sql = "SELECT * FROM orders WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Order(
                            resultSet.getInt("id"),
                            resultSet.getInt("product_id"),
                            resultSet.getInt("client_id"),
                            resultSet.getInt("quantity")
                    );
                } else {
                    return null; // Order not found
                }
            }
        }
    }

    // Method to retrieve all orders from the database
    public List<Order> getAll() throws SQLException {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                orders.add(new Order(
                        resultSet.getInt("id"),
                        resultSet.getInt("product_id"),
                        resultSet.getInt("client_id"),
                        resultSet.getInt("quantity")
                ));
            }
        }
        return orders;
    }

    // Method to update an order in the database
    public void update(Order order) throws SQLException {
        String sql = "UPDATE orders SET product_id = ?, client_id = ?, quantity = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, order.getProductId());
            statement.setInt(2, order.getClientId());
            statement.setInt(3, order.getQuantity());
            statement.setInt(4, order.getOrderId());
            statement.executeUpdate();
        }
    }

    // Method to delete an order from the database by ID
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM orders WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}
