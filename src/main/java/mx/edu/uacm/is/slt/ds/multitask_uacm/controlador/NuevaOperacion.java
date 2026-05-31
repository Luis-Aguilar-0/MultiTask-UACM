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
     * Accion de crear una nueva Operacion, y agregarla a la obsebable list cuando se preciona el botón de guardar
     **/
    @FXML
    public void ocClickNuevaOperacion(){
        Operacion operacion = new Operacion();
        if(!txtField_nombreOp.getText().isEmpty() && !txtArea_descripcion.getText().isEmpty()) {

            lbl_msgError.setVisible(false);
            operacion.setNombre(txtField_nombreOp.getText());
            operacion.setDescripcion(txtArea_descripcion.getText());
            operacion.setEstado(Estado.NO_EJECUTADA);
            gestor.agregarOperacion(operacion);
            btn_guardarOp.setOnMouseClicked(event->{
                onClosedVentana();
            });
        }else{
            lbl_msgError.setText("Error, campos de texto vacios...");
            lbl_msgError.setVisible(true);
        }

    }


    /**
     * Accion encargada de cerrar la ventana.
     * */
    @FXML
    public void onClosedVentana(){
        Stage ecena = (Stage) btn_cancelar.getScene().getWindow();
        ecena.close();
    }

}
