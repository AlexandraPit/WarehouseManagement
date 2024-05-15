package org.example.connection;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A factory class for managing database connections.
 */
public class ConnectionFactory {
    private static final String DBURL = "jdbc:postgresql://localhost:5432/Warehouse";
    private static final String USER = "postgres";
    private static final String PASS = "parola123";
    private static final String DRIVER = "org.postgresql.Driver";
    private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());

    private static ConnectionFactory singleInstance = new ConnectionFactory();

    /**
     * Constructs a ConnectionFactory object.
     */
    private ConnectionFactory() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a new database connection.
     *
     * @return a Connection object representing the database connection
     */
    private Connection createConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DBURL, USER, PASS);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "An error occurred while trying to connect to the database");
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Gets a database connection.
     *
     * @return a Connection object representing the database connection
     */
    public static Connection getConnection() {
        return singleInstance.createConnection();
    }

    /**
     * Closes a database connection.
     *
     * @param connection the Connection object to close
     */
    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occurred while trying to close the connection");
            }
        }
    }

    /**
     * Closes a database statement.
     *
     * @param statement the Statement object to close
     */
    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occurred while trying to close the statement");
            }
        }
    }

    /**
     * Closes a ResultSet.
     *
     * @param resultSet the ResultSet object to close
     */
    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occurred while trying to close the ResultSet");
            }
        }
    }
}
