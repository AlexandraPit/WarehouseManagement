package org.example.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javax.swing.*;

public class MainController {

    @FXML
    private Button btnClient, btnProduct, btnOrder;

    @FXML
    private void handleButton(ActionEvent event) throws Exception{
        Stage stage;
        Parent root;
        if(event.getSource() == btnClient){
            stage = (Stage) btnClient.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/org/example/clientPage.fxml"));
        }
        else{
            if(event.getSource() == btnProduct){
                stage = (Stage) btnProduct.getScene().getWindow();
                root = FXMLLoader.load(getClass().getResource("/org/example/productPage.fxml"));
            }
            else{
                stage = (Stage) btnOrder.getScene().getWindow();
                root = FXMLLoader.load(getClass().getResource("/org/example/orderPage.fxml"));
            }
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
