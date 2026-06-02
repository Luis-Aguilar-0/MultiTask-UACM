package mx.edu.uacm.is.slt.ds.multitask_uacm.controlador;

import java.io.InputStream;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import mx.edu.uacm.is.slt.ds.multitask_uacm.modelo.Estado;
import mx.edu.uacm.is.slt.ds.multitask_uacm.modelo.GestorOperaciones;
import mx.edu.uacm.is.slt.ds.multitask_uacm.modelo.Operacion;

public class NuevaOperacion {

    // Obtengo la instancia del gestor de operaciones
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

    @FXML
    private ImageView logoImageView;

    // Se ejecuta automaticamente despues de cargar el archivo FXML
    @FXML
    public void initialize() {
        cargarLogo();
    }

    // Carga la imagen del logo desde la carpeta de recursos
    private void cargarLogo() {
        String ruta = "/mx/edu/uacm/is/slt/ds/multitask_uacm/logo/logo.png";
        InputStream inputStream = getClass().getResourceAsStream(ruta);
        
        if (inputStream != null) {
            Image imagen = new Image(inputStream);
            logoImageView.setImage(imagen);
        } else {
            System.err.println("Error: No se encontro el logo en " + ruta);
        }
    }

    // Cierra la ventana actual
    @FXML
    public void onClosedVentana() {
        Stage escena = (Stage) btn_cancelar.getScene().getWindow();
        escena.close();
    }

    // Guarda una nueva operacion cuando el usuario hace clic en el boton
    @FXML
    public void ocClickNuevaOperacion() {
        if (!txtField_nombreOp.getText().isEmpty() && !txtArea_descripcion.getText().isEmpty()) {

            lbl_msgError.setVisible(false);
            
            Operacion operacion = new Operacion();
            operacion.setNombre(txtField_nombreOp.getText().trim());
            operacion.setDescripcion(txtArea_descripcion.getText().trim());
            operacion.setEstado(Estado.NO_EJECUTADA);
            
            // Al agregarse al gestor, al ser una ObservableList, la pantalla principal se actualizara sola al instante
            gestor.agregarOperacion(operacion);
            
            System.out.println("Operacion guardada: " + operacion.getNombre());
            
            // Cerramos la ventana de inmediato
            onClosedVentana();
            
        } else {
            lbl_msgError.setText("Error, campos de texto vacios...");
            lbl_msgError.setVisible(true);
        }
    }

}