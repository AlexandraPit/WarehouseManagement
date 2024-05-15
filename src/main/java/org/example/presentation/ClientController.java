package org.example.presentation;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.businessLayer.ClientBLL;
import org.example.model.Client;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;


public class ClientController {

    @FXML
    private TextField text_id, text_name, text_phone, text_email;

    @FXML
    private TableView<Client> clientTableView;
    @FXML
    private Button back;

    private ObservableList<Client> clients;
    ClientBLL clientBLL = new ClientBLL();

    public void initialize() {
        generateTableColumns(Client.class);
    }

    private void generateTableColumns(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            TableColumn<Client, ?> column = null;
            if (field.getType() == int.class || field.getType() == String.class || field.getType() == double.class) {
                column = new TableColumn<>(field.getName());
                column.setCellValueFactory(new PropertyValueFactory<>(field.getName()));
            }
            if (column != null) {
                clientTableView.getColumns().add(column);
            }
        }
    }

    public void goBack() throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) back.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("/org/example/mainPage.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void handleAddClient(ActionEvent event) {
        String name = text_name.getText();
        String phone = text_phone.getText();
        String email = text_email.getText();
        Client client = new Client(name, phone, email);
        clientBLL.insertClient(client);
    }


    public void handleEditClient(ActionEvent event) {
        try {
            int id = Integer.parseInt(text_id.getText());
            String name = text_name.getText();
            String phone = text_phone.getText();
            String email = text_email.getText();
            Client client = new Client(name, phone, email);
            clientBLL.editClient(client, id);
        } catch (NumberFormatException err) {
            showAlert("No id inserted", "Please insert the id of the order to be edited!");
        }
    }


    public void handleDeleteClient(ActionEvent event) {
        try {
            int id = Integer.parseInt(text_id.getText());
            clientBLL.deleteClient(id);
        } catch (NumberFormatException err) {
            showAlert("No id inserted", "Please insert the id of the order to be edited!");
        }
    }


    @FXML
    public void handleViewAll(ActionEvent event) {
        clientTableView.getItems().clear();
        List<Client> clients = clientBLL.viewAllClients();
        populateTable(clients, this.clientTableView);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void populateTable(List<Client> clients, TableView<Client> clientTableView) {
        clientTableView.getItems().clear();
        clientTableView.getItems().addAll(clients);
    }

}
