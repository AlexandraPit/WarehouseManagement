package org.example.dataAccessLayer;

import org.example.model.Orders;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Data Access Object (DAO) for managing orders in the database.
 */
public class OrderDAO extends GenericDAO<Orders> {

    /**
     * Maps a ResultSet to an Orders entity object.
     *
     * @param resultSet the ResultSet containing data from a database query
     * @return the Orders entity object mapped from the ResultSet data
     * @throws SQLException if a SQL exception occurs
     */
    @Override
    protected Orders mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("order_id");
        int c_id = resultSet.getInt("client_id");
        int p_id = resultSet.getInt("product_id");
        int quantity = resultSet.getInt("quantity");
        return new Orders(id, c_id, p_id, quantity);
    }
}
