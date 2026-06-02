package mx.edu.uacm.is.slt.ds.multitask_uacm.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class PrecondicionesController {
    
    @FXML
    private TextArea txtAreaDescripcion;
    
    private String textoOriginal = "";
    
    public void setTexto(String texto) {
        this.textoOriginal = texto;
        if (txtAreaDescripcion != null) {
            txtAreaDescripcion.setText(texto);
        }
    }
    
    public String getTexto() {
        return txtAreaDescripcion != null ? txtAreaDescripcion.getText() : "";
    }
    
    @FXML
    private void guardarPrecondicion(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void cancelar(ActionEvent event){
        if (txtAreaDescripcion != null) {
            txtAreaDescripcion.setText(textoOriginal);
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}