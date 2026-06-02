package mx.edu.uacm.is.slt.ds.multitask_uacm.controlador;

import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class AcercaDeController implements Initializable {
    
    @FXML
    private TextField lblVersion;
    
    @FXML
    private TextField lblFecha;
    
    @FXML
    private TextArea txtEquipo;
    
    @FXML
    private Button btnCerrar;
    
    @FXML
    private ImageView logoImageView;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarLogo();
        cargarDatos();
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
    
    // Carga los datos de informacion del sistema
    private void cargarDatos() {
        // Version del sistema
        lblVersion.setText("3.0.0");
        
        // Fecha de lanzamiento
        lblFecha.setText("Enero 2026");
        
        // Equipo de desarrollo
        txtEquipo.setText("Erik Cruz Roldan - Lider | 22-003-0842\n"
                        + "Luis Armando Aguilar Castellanos - Lider | 22-003-1206\n"
                        + "Wendolyn Medina Chavez - Miembro | 21-003-0392\n"
                        + "Angel Juarez Andrade - Miembro | 22-003-0141");
    }
    
    // Cierra la ventana actual
    @FXML
    private void cerrarVentana() {
        Stage stage = (Stage) btnCerrar.getScene().getWindow();
        stage.close();
    }
}