package mx.edu.uacm.is.slt.ds.multitask_uacm.controlador;

import java.io.InputStream;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class PrecondicionesController {
    
    @FXML
    private TextArea txtAreaDescripcion;
    
    @FXML
    private Button btnGuardar;
    
    @FXML
    private Button btnCancelar;
    
    @FXML
    private ImageView logoImageView;
    
    private String textoOriginal = "";
    private boolean textoModificado = false;
    
    @FXML
    public void initialize() {
        cargarLogo();
    }
    
    // Carga el logo
    private void cargarLogo() {
        String ruta = "/mx/edu/uacm/is/slt/ds/multitask_uacm/logo/logo.png";
        InputStream inputStream = getClass().getResourceAsStream(ruta);
        if (inputStream != null) {
            Image imagen = new Image(inputStream);
            logoImageView.setImage(imagen);
        }
    }
    
    // Establece el texto inicial
    public void setTexto(String texto) {
        this.textoOriginal = texto;
        if (txtAreaDescripcion != null) {
            txtAreaDescripcion.setText(texto);
        }
    }
    
    // Obtiene el texto actual
    public String getTexto() {
        return txtAreaDescripcion != null ? txtAreaDescripcion.getText() : "";
    }
    
    // Guarda las precondiciones
    @FXML
    private void guardarPrecondicion(ActionEvent event){
        String texto = txtAreaDescripcion.getText().trim();
        if (texto.isEmpty()) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Advertencia");
            alerta.setHeaderText(null);
            alerta.setContentText("No se puede guardar precondiciones vacias");
            alerta.showAndWait();
            return;
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    
    // Cancela y restaura el texto original
    @FXML
    private void cancelar(ActionEvent event){
        if (txtAreaDescripcion != null) {
            txtAreaDescripcion.setText(textoOriginal);
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}