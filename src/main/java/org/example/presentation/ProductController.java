package org.example.presentation;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.businessLayer.ProductBLL;
import org.example.model.Product;

import javafx.event.ActionEvent;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Controller class responsible for managing products in the application.
 */
public class ProductController {

    @FXML
    private TextField text_id, text_name, text_price, text_stock;
    @FXML
    private TableView<Product> productTableView;
    @FXML
    private Button back;

    private final ProductBLL productBLL = new ProductBLL();

    /**
     * Initializes the product management interface.
     */
    public void initialize() {
        // Generate table columns dynamically using reflection
        generateTableColumns(Product.class);
    }

    /**
     * Generates table columns dynamically using reflection.
     * @param clazz the class to generate columns for
     */
    private void generateTableColumns(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            TableColumn<Product, ?> column = null;
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
                productTableView.getColumns().add(column);
            }
        }
    }

    /**
     * Populates the table with the given list of products.
     * @param products the list of products to populate the table with
     */
    public void populateTable(List<Product> products) {
        productTableView.getItems().addAll(products);
    }

    /**
     * Handles the action event for viewing all products.
     * @param actionEvent the ActionEvent triggering the action
     */
    public void handleViewAll(ActionEvent actionEvent) {
        productTableView.getItems().clear();
        List<Product> products = productBLL.viewAllProducts();
        populateTable(products);
    }

    /**
     * Handles the action event for adding a new product.
     * @param e the ActionEvent triggering the action
     */
    public void handleAdd(ActionEvent e) {
        String name = text_name.getText();
        Double price = Double.parseDouble(text_price.getText());
        int stock = Integer.parseInt(text_stock.getText());
        Product product = new Product(name, price, stock);
        productBLL.insertProduct(product);
    }

    /**
     * Handles the action event for deleting a product.
     * @param e the ActionEvent triggering the action
     */
    public void handleDelete(ActionEvent e) {
        try {
            int id = Integer.parseInt(text_id.getText());
            productBLL.deleteProduct(id);
        } catch (NumberFormatException err) {
            showAlert("No id inserted", "Please insert the id of the product to be deleted!");
        }
    }

    /**
     * Handles the action event for editing a product.
     * @param e the ActionEvent triggering the action
     */
    public void handleEdit(ActionEvent e) {
        int id = Integer.parseInt(text_id.getText());
        String name = text_name.getText();
        Double price = Double.parseDouble(text_price.getText());
        int stock = Integer.parseInt(text_stock.getText());
        Product product = new Product(name, price, stock);
        productBLL.editProduct(product, id);
    }

    /**
     * Displays an alert with the given title and message.
     * @param title the title of the alert
     * @param message the message to display in the alert
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Redirects the user back to the main page.
     * @throws IOException if an I/O error occurs while loading the FXML file
     */
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
