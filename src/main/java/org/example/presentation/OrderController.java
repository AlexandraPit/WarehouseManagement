package org.example.presentation;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.example.model.Order;

public class OrderController {
    @FXML
    private TableView<Order> orderTable;
    @FXML
    private TableColumn<Order, Integer> orderId;
    @FXML
    private TableColumn<Order, String> client;
    @FXML
    private TableColumn<Order, String> product;
    @FXML
    private TableColumn<Order, Integer> quantity;

}
