package mx.edu.uacm.is.slt.ds.multitask_uacm.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class NuevaOperacion {

    @FXML
    private Button btn_guardarOp;

    @FXML
    private Button btn_cancelar;


    /**
     * Accion encargada de cerrar la ventana.
     * */
    @FXML
    public void onClosedVentana(){
        Stage ecena = (Stage) btn_cancelar.getScene().getWindow();
        ecena.close();
    }

}
