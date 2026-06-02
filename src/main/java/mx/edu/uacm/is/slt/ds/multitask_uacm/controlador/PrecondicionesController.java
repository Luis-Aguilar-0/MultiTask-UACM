/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.edu.uacm.is.slt.ds.multitask_uacm.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 *
 * @author USER
 */
public class PrecondicionesController {
    
    @FXML
    private TextArea txtAreaDescripcion;
    
    @FXML
    private void guardarPrecondicion(ActionEvent event){
        
        //String descripcion= txtAreaDescripcion.getText();
        
        Alert alerta =new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Éxito");
        alerta.setHeaderText(null);
        alerta.setContentText("Precondición guardada correctamente.");
        alerta.showAndWait();
        
        
    }
    
    @FXML
    private void cancelar(ActionEvent event){
        
        //txtAreaDescripcion.clear();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    
}
