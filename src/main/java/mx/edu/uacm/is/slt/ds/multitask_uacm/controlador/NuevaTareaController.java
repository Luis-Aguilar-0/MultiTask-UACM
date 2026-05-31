/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.edu.uacm.is.slt.ds.multitask_uacm.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

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
    
}
