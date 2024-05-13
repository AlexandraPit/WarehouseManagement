package org.example.presentation;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.businessLayer.ClientBLL;
import org.example.businessLayer.OrderBLL;
import org.example.businessLayer.ProductBLL;
import org.example.model.Client;
import org.example.model.Order;
import org.example.model.Product;

import java.lang.reflect.Field;
import java.util.List;

public class OrderController {
    @FXML
    private TableView<Order> orderTableView;
    @FXML
    private TableView<Client> clientTableView;
    @FXML
    private TableView<Product> productTableView;
    @FXML
    private TableColumn<Client, Integer>id;
    @FXML
    private TableColumn<Client, String>name;
    @FXML
    private TableColumn<Client, String>phone;
    @FXML
    private TableColumn<Client, String>email;
    @FXML
    private TableColumn<Client, Integer>p_id;
    @FXML
    private TableColumn<Client, String>p_name;
    @FXML
    private TableColumn<Client, Double>price;
    @FXML
    private TableColumn<Client, Integer>stock;

    @FXML
    private TextField text_oid, text_cid, text_pid, text_quantity;

    OrderBLL orderBLL = new OrderBLL();
    ClientBLL clientBLL=new ClientBLL();
    ProductBLL productBLL=new ProductBLL();


    public void initialize() {
        // Generate table columns dynamically using reflection
        generateTableColumns(Order.class);

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
public void refreshProductTable(ProductBLL productBLL){

}

    // Method to generate table columns dynamically using reflection
    private void generateTableColumns(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            TableColumn<Order, ?> column = null;
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


    // Method to populate table with data
    public void populateTable(List<Order> products) {
        orderTableView.getItems().addAll(products);
    }


    public void handleViewAll(ActionEvent actionEvent) {

        orderTableView.getItems().clear();
        List<Order> orders = orderBLL.getAllOrders(); // Assuming you have a method to retrieve all products from your database
        populateTable(orders);
    }
    public void handleAdd(ActionEvent e)
    {
        Client selectedClient = clientTableView.getSelectionModel().getSelectedItem();
        if (selectedClient == null) {
            showAlert("No client selected","Please select an existing client!");
            return;
        }
        Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            showAlert("No product selected","Please select an existing product!");
            return;
        }
        int c_id = selectedClient.getId();
        int p_id=selectedProduct.getP_id();
        int quantity=Integer.parseInt(text_quantity.getText());
        if(quantity>selectedProduct.getStock())
        {
           showAlert("Not enough products in stock!", "The max quantity you can buy is "+selectedProduct.getStock());
        }else {
            Order order = new Order(c_id, p_id, quantity);
            orderBLL.addOrder(order);

            int updatedStock = selectedProduct.getStock() - quantity;
            selectedProduct.setStock(updatedStock);

            // Update the product's stock in the database
            productBLL.editProduct(selectedProduct, selectedProduct.getP_id());
            populateProductTable(productTableView, productBLL.viewAllProducts());
        }

    }
    public void handleDelete(ActionEvent e)
    {
        int id=Integer.parseInt(text_oid.getText());

        orderBLL.deleteOrder(id);
    }
    public void handleEdit(ActionEvent e)
    {
        int id=Integer.parseInt(text_oid.getText());
        Client selectedClient = clientTableView.getSelectionModel().getSelectedItem();
        if (selectedClient == null) {
            // Handle case where no client is selected
            // You may want to display a message to the user indicating that a client must be selected
            return;
        }
        Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();
        int c_id = selectedClient.getId();
        int p_id=selectedProduct.getP_id();
        int quantity=Integer.parseInt(text_quantity.getText());
        Order order = new Order(c_id,p_id,quantity);
        orderBLL.editOrder(order ,id);
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

}
