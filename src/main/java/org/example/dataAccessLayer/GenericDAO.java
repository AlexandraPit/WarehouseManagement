package org.example.dataAccessLayer;

import org.example.connection.ConnectionFactory;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Generic Data Access Object (DAO) providing common methods for database operations.
 *
 * @param <T> the type of entity handled by this DAO
 */
public abstract class GenericDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(GenericDAO.class.getName());
    private final String tableName;

    /**
     * Constructs a GenericDAO instance.
     * It uses reflection to determine the class type of the entity and deduce the table name.
     */
    public GenericDAO() {
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        Class<T> clazz = (Class<T>) type.getActualTypeArguments()[0];
        this.tableName = clazz.getSimpleName().toLowerCase();
    }

    /**
     * Retrieves all entities from the database.
     *
     * @return a list of all entities retrieved from the database
     */
    public List<T> getAll() {
        List<T> entities = new ArrayList<>();
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement("SELECT * FROM " + tableName);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                T entity = mapResultSetToEntity(resultSet);
                entities.add(entity);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error retrieving all " + tableName + ": " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return entities;
    }

    /**
     * Retrieves an entity by its ID.
     *
     * @param id          the ID of the entity to retrieve
     * @param columnName  the name of the column containing the ID in the database
     * @return the retrieved entity, or null if not found
     */
    public T getById(int id, String columnName) {
        T entity = null;
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE " + columnName + " = ?");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                entity = mapResultSetToEntity(resultSet);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error retrieving " + tableName + " by " + columnName + ": " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return entity;
    }

    /**
     * Inserts a new entity into the database.
     *
     * @param entity the entity to insert
     */
    public void insert(T entity) {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = null;

        try {
            StringBuilder queryBuilder = new StringBuilder("INSERT INTO ").append(tableName).append(" (");
            StringBuilder valuesBuilder = new StringBuilder(") VALUES (");
            Field[] fields = entity.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (!isIdentityColumn(field)) {
                    queryBuilder.append(field.getName()).append(",");
                    valuesBuilder.append("?,");
                }
            }
            queryBuilder.deleteCharAt(queryBuilder.length() - 1).append(valuesBuilder.deleteCharAt(valuesBuilder.length() - 1)).append(")");
            statement = connection.prepareStatement(queryBuilder.toString());

            int parameterIndex = 1;
            for (Field field : fields) {
                field.setAccessible(true);
                if (!isIdentityColumn(field)) {
                    statement.setObject(parameterIndex++, field.get(entity));
                }
            }

            statement.executeUpdate();
        } catch (SQLException | IllegalAccessException e) {
            LOGGER.log(Level.WARNING, "Error inserting into " + tableName + ": " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * Updates an existing entity in the database.
     *
     * @param entity      the entity with updated data
     * @param idColumnName the name of the column containing the ID in the database
     * @param id          the ID of the entity to update
     */
    public void update(T entity, String idColumnName, int id) {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = null;

        try {
            StringBuilder queryBuilder = new StringBuilder("UPDATE ").append(tableName).append(" SET ");
            Field[] fields = entity.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (!isIdentityColumn(field)) {
                    field.setAccessible(true);
                    queryBuilder.append(field.getName()).append("=?,");
                }
            }
            queryBuilder.deleteCharAt(queryBuilder.length() - 1).append(" WHERE ").append(idColumnName).append(" = ?");
            statement = connection.prepareStatement(queryBuilder.toString());

            int parameterIndex = 1;
            for (Field field : fields) {
                if (!isIdentityColumn(field)) {
                    field.setAccessible(true);
                    statement.setObject(parameterIndex++, field.get(entity));
                }
            }
            statement.setInt(parameterIndex, id);

            statement.executeUpdate();
        } catch (SQLException | IllegalAccessException e) {
            LOGGER.log(Level.WARNING, "Error updating " + tableName + ": " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * Deletes an entity from the database.
     *
     * @param columnName the name of the column containing the value to match for deletion
     * @param value      the value to match for deletion
     */
    public void delete(String columnName, int value) {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = null;

        try {
            StringBuilder queryBuilder = new StringBuilder("DELETE FROM ").append(tableName).append(" WHERE ")
                    .append(columnName).append(" = ?");
            statement = connection.prepareStatement(queryBuilder.toString());
            statement.setInt(1, value);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error deleting from " + tableName + ": " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * Maps a ResultSet to an entity object.
     *
     * @param resultSet the ResultSet containing data from a database query
     * @return the entity object mapped from the ResultSet data
     * @throws SQLException if a SQL exception occurs
     */
    protected abstract T mapResultSetToEntity(ResultSet resultSet) throws SQLException;

    /**
     * Checks if a field represents an identity column in the database.
     *
     * @param field the field to check
     * @return true if the field represents an identity column, otherwise false
     */
    private boolean isIdentityColumn(Field field) {
        // Implement logic to check if the field is an identity column
        // You can use annotations or naming conventions to identify identity columns
        return field.getName().equals("id") || field.getName().equals("p_id") || field.getName().equals("order_id");
    }
}
