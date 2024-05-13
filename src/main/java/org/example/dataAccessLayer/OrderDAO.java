package org.example.dataAccessLayer;

import org.example.connection.ConnectionFactory;
import org.example.model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderDAO {
    protected static final Logger LOGGER = Logger.getLogger(OrderDAO.class.getName());
    private static final String insertStatementString = "INSERT INTO orders (c_id, p_id, quantity)" + "VALUES(?, ?, ?)";
    private static final String findStatementString = "SELECT * FROM orders WHERE o_id = ?";
    private static final String updateStatementString = "UPDATE orders SET c_id = ?, p_id = ?, quantity=? WHERE o_id = ?";
    private static final String deleteStatementString = "DELETE FROM orders WHERE o_id = ?";
    private static final String viewAllStatementString = "SELECT * FROM orders";

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(viewAllStatementString);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("o_id");
               int c_id = resultSet.getInt("c_id");
               int p_id = resultSet.getInt("p_id");
                int quantity = resultSet.getInt("quantity");
                // Assuming you have a constructor in your Order class that accepts these parameters
                Order order = new Order(id, c_id, p_id, quantity);
                orders.add(order);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "OrderDAO: getAllOrders " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return orders;
    }

    public Order getById(int id) {
        Order toReturn = null;
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;

        try {
            findStatement = connection.prepareStatement(findStatementString);
            findStatement.setInt(1, id);
            rs = findStatement.executeQuery();
            if (rs.next()) {
               int c_id = rs.getInt("c_id");
                int p_id = rs.getInt("p_id");
                int quantity = rs.getInt("quantity");
                // Assuming you have a constructor in your Order class that accepts these parameters
                toReturn = new Order(id, c_id, p_id, quantity);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "OrderDAO: getById " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(connection);
        }
        return toReturn;
    }

    public void insert(Order order) {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(insertStatementString);
            statement.setInt(1, order.getClientId());
            statement.setInt(2, order.getProductId());
            statement.setInt(3, order.getQuantity());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "OrderDAO: insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    public void update(Order order, int id) {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(updateStatementString);
            statement.setInt(1, order.getClientId());
            statement.setInt(2, order.getProductId());
            statement.setInt(3, order.getQuantity());
            statement.setInt(4, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "OrderDAO: update " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    public void delete(int id) {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(deleteStatementString);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "OrderDAO: delete " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }
}
