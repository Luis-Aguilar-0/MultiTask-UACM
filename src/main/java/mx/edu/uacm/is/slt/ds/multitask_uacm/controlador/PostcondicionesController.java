/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.edu.uacm.is.slt.ds.multitask_uacm.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 *
 * @author USER
 */
public class PostcondicionesController {
    
    @FXML
    private void guardar(ActionEvent event){
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Guardado");
        alerta.setHeaderText(null);
        alerta.setContentText("Postcondición guardada exitosamente.");

        alerta.showAndWait();
    }
    
    @FXML
    private void cancelar(ActionEvent event){
        Stage stage= (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    
}
