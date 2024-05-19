package org.example.presentation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.businessLayer.BillGenerator;
import org.example.businessLayer.ClientBLL;
import org.example.businessLayer.OrderBLL;
import org.example.businessLayer.ProductBLL;
import org.example.model.Client;
import org.example.model.Orders;
import org.example.model.Product;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Controller class responsible for managing orders in the application.
 */
public class OrderController {
    @FXML
    private TableView<Orders> orderTableView;
    @FXML
    private TableView<Client> clientTableView;
    @FXML
    private TableView<Product> productTableView;
    @FXML
    private TableColumn<Client, Integer> id;
    @FXML
    private TableColumn<Client, String> name;
    @FXML
    private TableColumn<Client, String> phone;
    @FXML
    private TableColumn<Client, String> email;
    @FXML
    private TableColumn<Client, Integer> p_id;
    @FXML
    private TableColumn<Client, String> p_name;
    @FXML
    private TableColumn<Client, Double> price;
    @FXML
    private TableColumn<Client, Integer> stock;
    @FXML
    private Button back;
    @FXML
    private TextField text_oid, text_quantity;

    private final OrderBLL orderBLL = new OrderBLL();
    private final ClientBLL clientBLL = new ClientBLL();
    private final ProductBLL productBLL = new ProductBLL();

    /**
     * Initializes the order management interface.
     */
    public void initialize() {
        // Generate table columns dynamically using reflection
        generateTableColumns(Orders.class);

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        populateClientTable(clientTableView, clientBLL.viewAllClients());

        p_id.setCellValueFactory(new PropertyValueFactory<>("p_id"));
        p_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        stock.setCellValueFactory(new PropertyValueFactory<>("stock"));

        populateProductTable(productTableView, productBLL.viewAllProducts());
    }


    /**
     * Generates table columns dynamically using reflection.
     *
     * @param clazz the class to generate columns for
     */
    private void generateTableColumns(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            TableColumn<Orders, ?> column = null;
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

                orderTableView.getColumns().add(column);


            }
        }
    }


    /**
     * Populates the table with the given list of products.
     *
     * @param products the list of products to populate the table with
     */
    public void populateTable(List<Orders> products) {
        orderTableView.getItems().addAll(products);
    }

    /**
     * Handles the action event for viewing all orders.
     *
     * @param actionEvent the ActionEvent triggering the action
     */
    public void handleViewAll(ActionEvent actionEvent) {

        orderTableView.getItems().clear();
        List<Orders> orders = orderBLL.getAllOrders(); // Assuming you have a method to retrieve all products from your database
        populateTable(orders);
    }

    public void handleAdd(ActionEvent e) {
        Client selectedClient = clientTableView.getSelectionModel().getSelectedItem();
        if (selectedClient == null) {
            showAlert("No client selected", "Please select an existing client!");
            return;
        }
        Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            showAlert("No product selected", "Please select an existing product!");
            return;
        }

        int c_id = selectedClient.getId();
        int p_id = selectedProduct.getP_id();
        int quantity = Integer.parseInt(text_quantity.getText());

        try {
            Orders orders = new Orders(c_id, p_id, quantity);
            orderBLL.addOrder(orders);
            populateProductTable(productTableView, productBLL.viewAllProducts());
        } catch (IllegalArgumentException ex) {
            showAlert("Not enough products in stock!", "The max quantity you can buy is " + selectedProduct.getStock());
        }
    }

    public void handleGenerateBills() {
        List<Client> clients = clientBLL.viewAllClients();
        BillGenerator billGenerator=new BillGenerator();
        billGenerator.generateBillsForClients(clients);

        showAlert("Success!", "Bills were generated in the pdf Bills");
    }

    public void handleDelete(ActionEvent e) {
        int id = Integer.parseInt(text_oid.getText());
        orderBLL.deleteOrder(id);
    }

    public void handleEdit(ActionEvent e) {
        int id = Integer.parseInt(text_oid.getText());
        Client selectedClient = clientTableView.getSelectionModel().getSelectedItem();
        if (selectedClient == null) {
            showAlert("Error", "No client was selected.");
            return;
        }
        Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            showAlert("Error", "No product was selected.");
            return;
        }

        int c_id = selectedClient.getId();
        int p_id = selectedProduct.getP_id();
        int quantity = Integer.parseInt(text_quantity.getText());

        try {
            Orders orders = new Orders(c_id, p_id, quantity);
            orderBLL.editOrder(orders, id);
            populateProductTable(productTableView, productBLL.viewAllProducts());
        } catch (IllegalArgumentException ex) {
            showAlert("Not enough products in stock!", "The max quantity you can buy is " + selectedProduct.getStock());
        }
    }


    private void populateClientTable(TableView<Client> tableView, List<Client> clients) {
        ObservableList<Client> clientData = FXCollections.observableArrayList(clients);
        tableView.setItems(clientData);
    }

    private void populateProductTable(TableView<Product> tableView, List<Product> products) {
        ObservableList<Product> productData = FXCollections.observableArrayList(products);
        tableView.setItems(productData);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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


}