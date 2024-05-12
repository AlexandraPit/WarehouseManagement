package org.example.dataAccessLayer;

import org.example.connection.ConnectionFactory;
import org.example.model.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientDAO {
    protected static final Logger LOGGER= Logger.getLogger(ClientDAO.class.getName());
    private static final String insertStatementString="INSERT INTO client (name, phone,email)"+"VALUES(?,?,?)";
    private final static String findStatementString="SELECT * FROM client where id = ?";
    private static final String updateStatementString = "UPDATE client SET name = ?, phone = ?, email = ? WHERE id = ?";
    private static final String deleteStatementString = "DELETE FROM client WHERE id = ?";
    private static final String viewAllStatementString = "SELECT * FROM client";

    // You need a database connection

    // Method to retrieve a client by ID from the database
    public static Client getById(int id) {

        Client toReturn=null;

        Connection connection= ConnectionFactory.getConnection();
        PreparedStatement findStatement=null;
        ResultSet rs=null;
        try {
            findStatement=connection.prepareStatement(findStatementString);
            findStatement.setLong(1, id);
            rs=findStatement.executeQuery();
            rs.next();
            String name=rs.getString("name");
            String phone=rs.getString("phone");
            String email=rs.getString("email");
            toReturn=new Client(id, name, phone,email);
        }catch (SQLException e){
            LOGGER.log(Level.WARNING,"ClientDAO:getBId "+e.getMessage());
        }finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(connection);
        }
        return  toReturn;
    }


    // Method to insert a new client into the database
    public static int insert(Client client) {
        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement insertStatement = null;
        int insertedId = -1;
        try {
            insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setString(1, client.getName());
            insertStatement.setString(2, client.getPhone());
            insertStatement.setString(3, client.getEmail());
            insertStatement.executeUpdate();

            ResultSet rs = insertStatement.getGeneratedKeys();
            if (rs.next()) {
                insertedId = rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "StudentDAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(dbConnection);
        }
        return insertedId;
    }

    // Method to update an existing client in the database
    public static void update(Client client) {
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement updateStatement = null;
        try {
            updateStatement = dbConnection.prepareStatement(updateStatementString);
            updateStatement.setString(1, client.getName());
            updateStatement.setString(2, client.getPhone());
            updateStatement.setString(3, client.getEmail());
            updateStatement.setInt(4, client.getId());
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDAO:update " + e.getMessage());
        } finally {
            ConnectionFactory.close(updateStatement);
            ConnectionFactory.close(dbConnection);
        }
    }

    // Method to delete a client from the database by ID
    public static void delete(int id) {
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement deleteStatement = null;
        try {
            deleteStatement = dbConnection.prepareStatement(deleteStatementString);
            deleteStatement.setInt(1, id);
            deleteStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDAO:delete " + e.getMessage());
        } finally {
            ConnectionFactory.close(deleteStatement);
            ConnectionFactory.close(dbConnection);
        }
    }

    // Method to retrieve all clients from the database
    public static List<Client> viewAll() {
        List<Client> clients = new ArrayList<>();
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(viewAllStatementString);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("c_id");
                String name = resultSet.getString("name");
                String phone = resultSet.getString("phone");
                String email = resultSet.getString("email");
                clients.add(new Client(id, name, phone, email));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDAO:viewAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return clients;
    }
}


