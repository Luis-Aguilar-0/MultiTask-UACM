package mx.edu.uacm.is.slt.ds.multitask_uacm.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mx.edu.uacm.is.slt.ds.multitask_uacm.modelo.Estado;
import mx.edu.uacm.is.slt.ds.multitask_uacm.modelo.GestorOperaciones;
import mx.edu.uacm.is.slt.ds.multitask_uacm.modelo.Operacion;

public class NuevaOperacion {

    private final GestorOperaciones gestor = GestorOperaciones.obtenerInstancia();
    private Stage stage;

    @FXML
    private Button btn_guardarOp;
    @FXML
    private Button btn_cancelar;
    @FXML
    private TextArea txtArea_descripcion;
    @FXML
    private TextField txtField_nombreOp;
    @FXML
    private Label lbl_msgError;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void onClosedVentana() {
        Stage escena = stage != null ? stage : (Stage) btn_cancelar.getScene().getWindow();
        escena.close();
    }

    @FXML
    public void ocClickNuevaOperacion() {
        if (!txtField_nombreOp.getText().isEmpty() && !txtArea_descripcion.getText().isEmpty()) {
            lbl_msgError.setVisible(false);

            Operacion operacion = new Operacion();
            operacion.setNombre(txtField_nombreOp.getText().trim());
            operacion.setDescripcion(txtArea_descripcion.getText().trim());
            operacion.setEstado(Estado.NO_EJECUTADA);

            gestor.agregarOperacion(operacion);

            System.out.println("Operación guardada reactivamente: " + operacion.getNombre());

            onClosedVentana();
        } else {
            lbl_msgError.setText("Error, campos de texto vacíos...");
            lbl_msgError.setVisible(true);
        }
    }
}
