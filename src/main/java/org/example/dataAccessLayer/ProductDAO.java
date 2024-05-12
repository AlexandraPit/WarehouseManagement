package org.example.dataAccessLayer;

import org.example.connection.ConnectionFactory;
import org.example.model.Client;
import org.example.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductDAO {
    protected static final Logger LOGGER = Logger.getLogger(ProductDAO.class.getName());
    private static final String insertStatementString = "INSERT INTO product (name, price,stock)" + "VALUES(?, ?,?)";
    private static final String findStatementString = "SELECT * FROM product WHERE p_id = ?";
    private static final String updateStatementString = "UPDATE product SET name = ?, price = ?, stock=? WHERE p_id = ?";
    private static final String deleteStatementString = "DELETE FROM product WHERE p_id = ?";
    private static final String viewAllStatementString = "SELECT * FROM product";

    // Method to retrieve all products from the database
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(viewAllStatementString);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("p_id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                int stock=resultSet.getInt("stock");
                Product product = new Product(id, name, price,stock);
                products.add(product);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ProductDAO: getAllProducts " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return products;
    }
    public static Product getById(int id) {

        Product toReturn=null;

        Connection connection= ConnectionFactory.getConnection();
        PreparedStatement findStatement=null;
        ResultSet rs=null;
        try {
            findStatement=connection.prepareStatement(findStatementString);
            findStatement.setLong(1, id);
            rs=findStatement.executeQuery();
            rs.next();
            String name=rs.getString("name");
            double price=rs.getDouble("price");
            int stock=rs.getInt("stock");
            toReturn=new Product(id, name, price,stock);
        }catch (SQLException e){
            LOGGER.log(Level.WARNING,"ProductDAO:getBId "+e.getMessage());
        }finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(connection);
        }
        return  toReturn;
    }

    // Method to insert a new product into the database
    public void insert(Product product) {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(insertStatementString);
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setInt(3,product.getStock());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ProductDAO: insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    // Method to update an existing product in the database
    public void update(Product product, int id) {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(updateStatementString);
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setInt(3, product.getStock());
            statement.setInt(4,id);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ProductDAO: update " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    // Method to delete a product from the database
    public void delete(int id) {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(deleteStatementString);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ProductDAO: delete " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }
}
