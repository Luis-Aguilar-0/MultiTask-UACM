package mx.edu.uacm.is.slt.ds.multitask_uacm.controlador;

import java.io.InputStream;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import mx.edu.uacm.is.slt.ds.multitask_uacm.modelo.Operacion;
import mx.edu.uacm.is.slt.ds.multitask_uacm.modelo.Tarea;

public class NuevaTareaController {
    
    private Operacion operacionActual;
    private String precondiciones = "";
    private String postcondiciones = "";
    
    @FXML 
    private TextField txtNombreTarea;
    
    @FXML 
    private TextArea txtDescripcion;
    
    @FXML 
    private TextField txtDependencias;
    
    @FXML 
    private ComboBox<String> comboTipoTarea;
    
    @FXML 
    private Button btnGuardarTarea;
    
    @FXML 
    private Button btnCancelar;
    
    @FXML 
    private ImageView logoImageView;
    
    @FXML
    public void initialize() {
        cargarLogo();
        cargarComboBox();
    }
    
    private void cargarLogo() {
        String ruta = "/mx/edu/uacm/is/slt/ds/multitask_uacm/logo/logo.png";
        InputStream inputStream = getClass().getResourceAsStream(ruta);
        if (inputStream != null) {
            logoImageView.setImage(new Image(inputStream));
        }
    }
    
    private void cargarComboBox() {
        comboTipoTarea.getItems().addAll("Normal", "Urgente", "Critica", "Opcional");
        comboTipoTarea.setValue("Normal");
    }
    
    public void setOperacion(Operacion operacion) {
        this.operacionActual = operacion;
    }
    
    @FXML
    public void abrirPrecondiciones() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/edu/uacm/is/slt/ds/multitask_uacm/fxml/Precondiciones.fxml"));
            Parent root = loader.load();
            
            PrecondicionesController controller = loader.getController();
            controller.setTexto(precondiciones);
            
            Stage stage = new Stage();
            stage.setTitle("Precondiciones");
            stage.setScene(new Scene(root));
            stage.initModality(javafx.stage.Modality.WINDOW_MODAL);
            stage.initOwner(btnGuardarTarea.getScene().getWindow());
            stage.showAndWait();
            
            precondiciones = controller.getTexto();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    public void abrirPostcondiciones() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/edu/uacm/is/slt/ds/multitask_uacm/fxml/Postcondiciones.fxml"));
            Parent root = loader.load();
            
            PostcondicionesController controller = loader.getController();
            controller.setTexto(postcondiciones);
            
            Stage stage = new Stage();
            stage.setTitle("Postcondiciones");
            stage.setScene(new Scene(root));
            stage.initModality(javafx.stage.Modality.WINDOW_MODAL);
            stage.initOwner(btnGuardarTarea.getScene().getWindow());
            stage.showAndWait();
            
            postcondiciones = controller.getTexto();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    public void guardarTarea() {
        String nombre = txtNombreTarea.getText().trim();
        String descripcion = txtDescripcion.getText().trim();
        String dependencias = txtDependencias.getText().trim();
        String tipo = comboTipoTarea.getValue();
        
        if (nombre.isEmpty()) {
            mostrarAlerta("Error", "El nombre de la tarea no puede estar vacio");
            return;
        }
        
        Tarea nuevaTarea = new Tarea(nombre, descripcion);
        
        // Usar setTipoTarea
        nuevaTarea.setTipoTarea(tipo);
        
        // Agregar dependencias
        if (!dependencias.isEmpty()) {
            String[] deps = dependencias.split(",");
            for (String dep : deps) {
                nuevaTarea.agregarDependencia(dep.trim());
            }
        }
        
        // Agregar precondiciones
        if (!precondiciones.isEmpty()) {
            String[] precons = precondiciones.split("\n");
            for (String precon : precons) {
                if (!precon.trim().isEmpty()) {
                    nuevaTarea.agregarPrecondicion(precon.trim());
                }
            }
        }
        
        // Agregar postcondiciones
        if (!postcondiciones.isEmpty()) {
            String[] postcons = postcondiciones.split("\n");
            for (String postcon : postcons) {
                if (!postcon.trim().isEmpty()) {
                    nuevaTarea.agregarPostcondicion(postcon.trim());
                }
            }
        }
        
        operacionActual.agregarTarea(nuevaTarea);
        
        System.out.println("Tarea agregada: " + nombre);
        cerrarVentana();
    }
    
    @FXML
    public void cancelar() {
        cerrarVentana();
    }
    
    private void cerrarVentana() {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }
    
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}