/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.edu.uacm.is.slt.ds.multitask_uacm.controlador;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author USER
 */
public class NuevaTareaController {
    
    @FXML
    private TextField txtNombreTarea;
    @FXML
    private TextField txtDescripcion;
    @FXML
    private TextField txtDependencias;
    @FXML
    private ComboBox<String> comboTipoTarea;
    
    @FXML
    public void initialize(){
        
        //carga las opciones del comboBox
        comboTipoTarea.getItems().addAll(
        "Secuencial (depende de otras tareas",
        "Inicial (puede iniciar sola)",
        "Hoja (no genera otras tareas)" 
    );
    
    //sleecciona la primera opcion por defecto
    comboTipoTarea.getSelectionModel().selectFirst();
    }
    
    @FXML
    private void guardarTarea(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText(null);
        alert.setContentText("La tarea se guardó correctamente");
        alert.showAndWait();
    }
    @FXML
    private void abrirPrecondiciones(ActionEvent event){
        try {

        FXMLLoader loader = new FXMLLoader(
            getClass().getResource("/mx/edu/uacm/is/slt/ds/multitask_uacm/fxml/Precondiciones.fxml")
        );

        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setTitle("Precondiciones");
        stage.setScene(new Scene(root));
        stage.show();

        }catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    @FXML
    private void abrirPostcondiciones(ActionEvent event){
        try {

        FXMLLoader loader = new FXMLLoader(
            getClass().getResource(
                "/mx/edu/uacm/is/slt/ds/multitask_uacm/fxml/Postcondiciones.fxml"
            )
        );

        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setTitle("Postcondiciones");
        stage.setScene(new Scene(root));
        stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void cancelar(ActionEvent event){
        txtNombreTarea.clear();
        txtDescripcion.clear();
        txtDependencias.clear();

        comboTipoTarea.getSelectionModel().selectFirst();
    }
    @FXML
    private void volver(ActionEvent event){
        try {

        FXMLLoader loader = new FXMLLoader(
            getClass().getResource(
                "/mx/edu/uacm/is/slt/ds/multitask_uacm/fxml/VisorDeTareas.fxml"
            )
        );

        Parent root = loader.load();

        Stage stage = (Stage)((Node)event.getSource())
                .getScene()
                .getWindow();

        stage.setScene(new Scene(root));
        stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
