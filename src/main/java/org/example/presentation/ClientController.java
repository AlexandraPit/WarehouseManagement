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
import org.example.businessLayer.ClientBLL;
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

    private ObservableList<Client> clients; // Assuming you have a reference to the list of clients
    ClientBLL clientBLL=new ClientBLL();
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




    public void handleAddClient(ActionEvent event) {

        String name=text_name.getText();
        String phone=text_phone.getText();
        String email=text_email.getText();
        Client client=new Client(name,phone,email);
        clientBLL.insertClient(client);

    }


    public void handleEditClient(ActionEvent event) {
        int id=Integer.parseInt(text_id.getText());
        String name=text_name.getText();
        String phone=text_phone.getText();
        String email=text_email.getText();
        Client client=new Client(name,phone,email);
        clientBLL.editClient(client ,id);
    }


    public void handleDeleteClient(ActionEvent event) {
        try{int id=Integer.parseInt(text_id.getText());
        clientBLL.deleteClient(id);}
         catch(NumberFormatException err)
        {
            showAlert("No id inserted", "Please insert the id of the order to be edited!");
            return;
        }
    }


    @FXML
    public void handleViewAll(ActionEvent event) {

        clientTableView.getItems().clear(); // Clear existing items in the table view
        List<Client> clients = clientBLL.viewAllClients(); // Assuming you have a method to retrieve all clients from your database
        populateTable(clients, this.clientTableView);
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void populateTable(List<Client> clients, TableView<Client>clientTableView) {
        clientTableView.getItems().clear(); // Clear the table first
        clientTableView.getItems().addAll(clients); // Add the retrieved clients to the table
    }

}
