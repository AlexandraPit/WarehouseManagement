package org.example.presentation;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.dataAccessLayer.ClientDAO;
import org.example.model.Client;
import org.example.model.Product;

import java.lang.reflect.Field;
import java.util.List;


public class ClientController {

    @FXML
    private TextField text_id, text_name, text_phone, text_email;

    @FXML
    private TableView<Client> clientTableView;
    @FXML
    private TableColumn<Client, Integer> c_id;
    @FXML
    private TableColumn<Client, String> name;
    @FXML
    private TableColumn<Client, String> phone;
    @FXML
    private TableColumn<Client, String> email;

    private ObservableList<Client> clients; // Assuming you have a reference to the list of clients
    ClientDAO clientDAO=new ClientDAO();
    public void initialize() {
        generateTableColumns(Client.class);
    }

    private void generateTableColumns(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            TableColumn<Client, ?> column = null;
            if (field.getType() == int.class) {
                column = new TableColumn<>(field.getName());
                column.setCellValueFactory(new PropertyValueFactory<>(field.getName()));
            } else if (field.getType() == String.class) {
                column = new TableColumn<>(field.getName());
                column.setCellValueFactory(new PropertyValueFactory<>(field.getName()));
            } else if (field.getType() == double.class) {
                column = new TableColumn<>(field.getName());
                column.setCellValueFactory(new PropertyValueFactory<>(field.getName()));
            }

            if (column != null) {
                clientTableView.getColumns().add(column);
            }
        }
    }


    @FXML
    private void handleAddClient(ActionEvent event) {
        int id=Integer.parseInt(text_id.getText());
        String name=text_name.getText();
        String phone=text_phone.getText();
        String email=text_email.getText();
        Client client=new Client(id,name,phone,email);
        clientDAO.insert(client);

    }

    @FXML
    public void handleEditClient(ActionEvent event) {
        Client selectedClient = clientTableView.getSelectionModel().getSelectedItem();
        if (selectedClient != null) {
            // Implementation for editing the selected client
        } else {
            showAlert("No Client Selected", "Please select a client to edit.");
        }
    }

    @FXML
    public void handleDeleteClient(ActionEvent event) {
        Client selectedClient = clientTableView.getSelectionModel().getSelectedItem();
        if (selectedClient != null) {
            // Implementation for deleting the selected client
        } else {
            showAlert("No Client Selected", "Please select a client to delete.");
        }
    }

    @FXML
    public void handleViewAll(ActionEvent event) {
        List<Client> clients = clientDAO.viewAll(); // Assuming you have a method to retrieve all products from your database
        populateTable(clients);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void populateTable(List<Client> clients) {
        clientTableView.getItems().addAll(clients);
    }
}
