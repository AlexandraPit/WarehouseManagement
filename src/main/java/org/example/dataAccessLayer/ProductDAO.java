package org.example.dataAccessLayer;

import org.example.model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Data Access Object (DAO) for managing products in the database.
 */
public class ProductDAO extends GenericDAO<Product> {

    /**
     * Maps a ResultSet to a Product entity object.
     *
     * @param resultSet the ResultSet containing data from a database query
     * @return the Product entity object mapped from the ResultSet data
     * @throws SQLException if a SQL exception occurs
     */
    @Override
    protected Product mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("p_id");
        String name = resultSet.getString("name");
        double price = resultSet.getDouble("price");
        int stock = resultSet.getInt("stock");
        return new Product(id, name, price, stock);
    }
}
