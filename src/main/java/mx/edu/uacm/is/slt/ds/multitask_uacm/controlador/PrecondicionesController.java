package mx.edu.uacm.is.slt.ds.multitask_uacm.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import mx.edu.uacm.is.slt.ds.multitask_uacm.modelo.Tarea;

public class PrecondicionesController {
    
    @FXML private TextArea txtAreaDescripcion;
    private Tarea tareaActual; 

    // Recibe la tarea desde el formulario principal de tareas
    public void setTarea(Tarea tarea) {
        this.tareaActual = tarea; 
    }
    
    // Almacena el texto ingresado en la lista de la tarea activa
    @FXML
    private void guardarPrecondicion(ActionEvent event){
        if (txtAreaDescripcion == null || txtAreaDescripcion.getText().trim().isEmpty()) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setContentText("El campo de texto esta vacio.");
            alerta.showAndWait();
            return;
        }

        String descripcion = txtAreaDescripcion.getText().trim();
        if (tareaActual != null) {
            tareaActual.agregarPrecondicion(descripcion); 
        }
        
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Exito");
        alerta.setHeaderText(null);
        alerta.setContentText("Precondicion guardada correctamente.");
        alerta.showAndWait();
        
        cancelar(event);
    }
    
    // Cierra la ventana actual
    @FXML
    private void cancelar(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}