package org.example.presentation;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.businessLayer.ProductBLL;
import org.example.model.Product;

import javafx.event.ActionEvent;

import javax.swing.*;
import java.lang.reflect.Field;
import java.util.List;

public class ProductController {

    @FXML
    private TextField text_id, text_name, text_price, text_stock;
    @FXML
    private TableView<Product> productTableView;


    ProductBLL productBLL = new ProductBLL();
    public void initialize() {
        // Generate table columns dynamically using reflection
        generateTableColumns(Product.class);
    }

    // Method to generate table columns dynamically using reflection
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


    // Method to populate table with data
    public void populateTable(List<Product> products) {
        productTableView.getItems().addAll(products);
    }

    public void handleViewAll(ActionEvent actionEvent) {

        productTableView.getItems().clear();
        List<Product> products = productBLL.viewAllProducts(); // Assuming you have a method to retrieve all products from your database
        populateTable(products);
    }
    public void handleAdd(ActionEvent e)
    {
       // int id=Integer.parseInt(text_id.getText());
        String name=text_name.getText();
        Double price=Double.parseDouble(text_price.getText());
        int stock=Integer.parseInt(text_stock.getText());
        Product product = new Product(name,price,stock);
        productBLL.insertProduct(product);


    }
    public void handleDelete(ActionEvent e)
    {
        int id=Integer.parseInt(text_id.getText());
        productBLL.deleteProduct(id);
    }
    public void handleEdit(ActionEvent e)
    {
        int id=Integer.parseInt(text_id.getText());
        String name=text_name.getText();
        Double price=Double.parseDouble(text_price.getText());
        int stock=Integer.parseInt(text_stock.getText());
        Product product = new Product(name,price,stock);
        productBLL.editProduct(product ,id);
    }


}
