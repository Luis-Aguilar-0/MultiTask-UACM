package mx.edu.uacm.is.slt.ds.multitask_uacm.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mx.edu.uacm.is.slt.ds.multitask_uacm.modelo.Tarea;

public class EditorTareasController {

    @FXML private TextField txtNombre;
    @FXML private TextField txtDescripcion;
    @FXML private ComboBox<String> comboTipo;
    @FXML private TextArea txtComportamiento;
    @FXML private CheckBox chkPausable;

    private Tarea tarea;

    @FXML
    public void initialize() {
        comboTipo.getItems().clear();
        comboTipo.getItems().addAll(
                "Inicial (puede iniciar sola)",
                "Secuencial (depende de otras tareas)",
                "Hoja (no genera otras tareas)"
        );
        comboTipo.getSelectionModel().selectFirst();
    }

    public void cargarTarea(Tarea tarea) {
        this.tarea = tarea;
        if (tarea != null) {
            txtNombre.setText(tarea.getNombre());
            txtDescripcion.setText(tarea.getDescripcion());
            txtComportamiento.setText(tarea.getComportamiento());
            chkPausable.setSelected(tarea.isPausable());
            comboTipo.setValue(tarea.getTipoTarea());
        }
    }

    @FXML
    public void guardarCambios() {
        if (tarea == null) {
            return;
        }

        tarea.setNombre(txtNombre.getText().trim());
        tarea.setDescripcion(txtDescripcion.getText().trim());
        tarea.setComportamiento(txtComportamiento.getText().trim());
        tarea.setPausable(chkPausable.isSelected());
        tarea.setTipoTarea(comboTipo.getValue());

        System.out.println("Cambios guardados en el Modelo para: " + tarea.getNombre());

        Stage stage = (Stage) txtNombre.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void cancelar() {
        Stage stage = (Stage) txtNombre.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void ejecutarTarea() {
        if (tarea != null) {
            tarea.ejecutar();
        }
    }

    @FXML
    public void pausarTarea() {
        if (tarea != null) {
            tarea.pausar();
        }
    }

    @FXML
    public void reanudarTarea() {
        if (tarea != null) {
            tarea.reanudar();
        }
    }

    @FXML
    public void detenerTarea() {
        if (tarea != null) {
            tarea.detener();
        }
    }
}