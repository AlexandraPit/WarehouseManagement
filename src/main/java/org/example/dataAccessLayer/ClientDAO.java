package org.example.dataAccessLayer;

import org.example.model.Client;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Data Access Object (DAO) for handling client entities.
 */
public class ClientDAO extends GenericDAO<Client> {

    /**
     * Maps a ResultSet to a Client entity.
     *
     * @param resultSet the ResultSet containing data from a database query
     * @return a Client object mapped from the ResultSet data
     * @throws SQLException if a SQL exception occurs
     */
    @Override
    protected Client mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String phone = resultSet.getString("phone");
        String email = resultSet.getString("email");
        return new Client(id, name, phone, email);
    }
}
