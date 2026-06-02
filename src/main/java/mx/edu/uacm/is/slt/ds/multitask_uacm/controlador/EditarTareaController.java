package mx.edu.uacm.is.slt.ds.multitask_uacm.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mx.edu.uacm.is.slt.ds.multitask_uacm.modelo.Tarea;

public class EditarTareaController {
    
    private Tarea tareaActual;
    
    @FXML private TextField txtNombre;
    @FXML private TextArea txtDescripcion;
    @FXML private Button btnGuardar;
    @FXML private Button btnCancelar;
    
    public void setTarea(Tarea tarea) {
        this.tareaActual = tarea;
        txtNombre.setText(tarea.getNombre());
        txtDescripcion.setText(tarea.getDescripcion());
    }
    
    @FXML
    public void guardar() {
        if (tareaActual != null) {
            String nuevoNombre = txtNombre.getText().trim();
            if (nuevoNombre.isEmpty()) {
                mostrarAlerta("Error", "El nombre no puede estar vacio");
                return;
            }
            tareaActual.setNombre(nuevoNombre);
            tareaActual.setDescripcion(txtDescripcion.getText());
            cerrar();
        }
    }
    
    @FXML
    public void cancelar() {
        cerrar();
    }
    
    private void cerrar() {
        Stage stage = (Stage) btnGuardar.getScene().getWindow();
        stage.close();
    }
    
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}