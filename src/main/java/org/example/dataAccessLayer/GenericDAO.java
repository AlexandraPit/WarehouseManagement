package org.example.dataAccessLayer;

import org.example.connection.ConnectionFactory;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class GenericDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(GenericDAO.class.getName());
    private final String tableName;

    public GenericDAO() {
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        Class<T> clazz = (Class<T>) type.getActualTypeArguments()[0];
        this.tableName = clazz.getSimpleName().toLowerCase();
    }

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

    public T getById(int id) {
        T entity = null;
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE id = ?");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                entity = mapResultSetToEntity(resultSet);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error retrieving " + tableName + " by ID: " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return entity;
    }

    public void insert(T entity) {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = null;

        try {
            StringBuilder queryBuilder = new StringBuilder("INSERT INTO ").append(tableName).append(" (");
            StringBuilder valuesBuilder = new StringBuilder(") VALUES (");
            Field[] fields = entity.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                queryBuilder.append(field.getName()).append(",");
                valuesBuilder.append("?,");
            }
            queryBuilder.deleteCharAt(queryBuilder.length() - 1).append(valuesBuilder.deleteCharAt(valuesBuilder.length() - 1)).append(")");
            statement = connection.prepareStatement(queryBuilder.toString());

            int parameterIndex = 1;
            for (Field field : fields) {
                field.setAccessible(true);
                statement.setObject(parameterIndex++, field.get(entity));
            }

            statement.executeUpdate();
        } catch (SQLException | IllegalAccessException e) {
            LOGGER.log(Level.WARNING, "Error inserting into " + tableName + ": " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    public void update(T entity, int id) {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = null;

        try {
            StringBuilder queryBuilder = new StringBuilder("UPDATE ").append(tableName).append(" SET ");
            Field[] fields = entity.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                queryBuilder.append(field.getName()).append("=?,");
            }
            queryBuilder.deleteCharAt(queryBuilder.length() - 1).append(" WHERE id = ?");
            statement = connection.prepareStatement(queryBuilder.toString());

            int parameterIndex = 1;
            for (Field field : fields) {
                field.setAccessible(true);
                statement.setObject(parameterIndex++, field.get(entity));
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

    public void delete(int id) {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("DELETE FROM " + tableName + " WHERE id = ?");
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error deleting from " + tableName + ": " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    protected abstract T mapResultSetToEntity(ResultSet resultSet) throws SQLException;
}
