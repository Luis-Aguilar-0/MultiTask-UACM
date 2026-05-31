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


    //obtengo la intancia Gestor
    private GestorOperaciones gestor = GestorOperaciones.obtenerInstancia();

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


    /**
     * Accion encargada de cerrar la ventana.
     * */
    @FXML
    public void onClosedVentana(){
        Stage ecena = (Stage) btn_cancelar.getScene().getWindow();
        ecena.close();
    }

    @FXML
    public void ocClickNuevaOperacion(){
        if(!txtField_nombreOp.getText().isEmpty() && !txtArea_descripcion.getText().isEmpty()) {

            lbl_msgError.setVisible(false);
            
            Operacion operacion = new Operacion();
            operacion.setNombre(txtField_nombreOp.getText().trim());
            operacion.setDescripcion(txtArea_descripcion.getText().trim());
            operacion.setEstado(Estado.NO_EJECUTADA);
            
            // Al agregarse al gestor, al ser una ObservableList, la pantalla principal se actualizará sola al instante
            gestor.agregarOperacion(operacion);
            
            System.out.println("Operación guardada reactivamente: " + operacion.getNombre());
            
            // Cerramos la ventana de inmediato de forma natural
            onClosedVentana();
            
        } else {
            lbl_msgError.setText("Error, campos de texto vacíos...");
            lbl_msgError.setVisible(true);
        }
    }

}
