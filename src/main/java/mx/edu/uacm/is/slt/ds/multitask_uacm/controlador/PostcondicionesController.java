package mx.edu.uacm.is.slt.ds.multitask_uacm.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import mx.edu.uacm.is.slt.ds.multitask_uacm.modelo.Tarea;

public class PostcondicionesController {

    @FXML private TextArea txtAreaDescripcionPost;
    private Tarea tareaActual; 

    // Recibe la tarea activa en construccion
    public void setTarea(Tarea tarea) {
        this.tareaActual = tarea; 
    }

    // Guarda el texto dentro de la lista de postcondiciones del modelo
    @FXML
    private void guardar(ActionEvent event) {
        if (txtAreaDescripcionPost == null || txtAreaDescripcionPost.getText().trim().isEmpty()) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setContentText("El campo de texto de postcondicion esta vacio.");
            alerta.showAndWait();
            return;
        }

        String descripcion = txtAreaDescripcionPost.getText().trim();
        if (tareaActual != null) {
            tareaActual.agregarPostcondicion(descripcion); 
        }

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Guardado");
        alerta.setHeaderText(null);
        alerta.setContentText("Postcondicion guardada exitosamente.");
        alerta.showAndWait();

        cancelar(event);
    }

    // Cierra la vista flotante
    @FXML
    private void cancelar(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}