package mx.edu.uacm.is.slt.ds.multitask_uacm.controlador;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AcercaDeController implements Initializable{
    @FXML
    private TextField lblVersion;
    
    @FXML
    private TextField lblFecha;
    
    @FXML
    private TextArea txtEquipo;
    
    @FXML
    private Button btnCerrar;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //datos sobre la aplicacion
        
        lblVersion.setText("3.0.0");
        
        lblFecha.setText("12/06/2026");
        
        txtEquipo.setText("• Erik Cruz Roldan - Líder | 22-003-0842 \n"
                +"• Luis Armando Aguilar Castellanos- Líder |  22-003-1206 \n"
                + "• Wendolyn Medina Chávez - Miembro | 21-003-0392\n"
                + "• Ángel Juárez Andrade - Miembro |  22-003-0141");
        
                
    }
    
    @FXML
    private void cerrarVentana(){
        Stage stage= (Stage) btnCerrar.getScene().getWindow();
        stage.close();
    }
    
    

}
