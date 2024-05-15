package org.example.businessLayer;

import javafx.scene.control.Alert;

import org.example.businessLayer.validators.EmailValidator;
import org.example.businessLayer.validators.Validator;
import org.example.dataAccessLayer.ClientDAO;
import org.example.model.Client;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Business Logic Layer (BLL) for managing clients.
 */
public class ClientBLL {
    private List<Validator<Client>> validators;

    private ClientDAO clientDAO;

    /**
     * Constructs a ClientBLL object.
     * Initializes validators list and clientDAO.
     */
    public ClientBLL() {
        validators = new ArrayList<>();
        validators.add(new EmailValidator());
        clientDAO = new ClientDAO();
    }

    /**
     * Retrieves a client by ID.
     *
     * @param id the ID of the client to retrieve
     * @return the retrieved client, or null if not found
     * @throws SQLException if a database access error occurs
     * @throws NoSuchElementException if the client with the given ID is not found
     */
    public Client getClientById(int id) throws SQLException {
        Client client = clientDAO.getById(id, "id");
        if (client == null) {
            throw new NoSuchElementException("The client with id " + id + " was not found!");
        }
        return client;
    }

    /**
     * Inserts a new client into the database.
     *
     * @param client the client object to insert
     */
    public void insertClient(Client client) {
        try {
            for (Validator<Client> v : validators) {
                v.validate(client);
            }
            clientDAO.insert(client);
        } catch (IllegalArgumentException e) {
            // Validation failed, show error message
            showErrorAlert("Validation Error", "Invalid email format");
        }
    }

    /**
     * Updates an existing client in the database.
     *
     * @param client the updated client object
     * @param id     the ID of the client to update
     */
    public void editClient(Client client, int id) {
        for (Validator<Client> item : validators) {
            item.validate(client);
        }
        clientDAO.update(client, "id", id);
    }

    /**
     * Deletes a client from the database by ID.
     *
     * @param id the ID of the client to delete
     */
    public void deleteClient(int id) {
        clientDAO.delete("id", id);
    }

    /**
     * Retrieves a list of all clients from the database.
     *
     * @return a list of all clients
     */
    public List<Client> viewAllClients() {
        return clientDAO.getAll();
    }

    /**
     * Displays an error alert dialog with the specified title and message.
     *
     * @param title   the title of the alert dialog
     * @param message the message to display in the alert dialog
     */
    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
