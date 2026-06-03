package mx.edu.uacm.is.slt.ds.multitask_uacm.controlador;

import java.io.InputStream;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import mx.edu.uacm.is.slt.ds.multitask_uacm.modelo.Tarea;

public class EditarTareaController {
    
    private Tarea tareaActual;
    
    @FXML
    private TextField txtNombre;
    
    @FXML
    private ComboBox<String> cmbTipoTarea;
    
    @FXML
    private TextArea txtDescripcion;
    
    @FXML
    private TextArea txtPrecondiciones;
    
    @FXML
    private TextArea txtPostcondiciones;
    
    @FXML
    private Button btnGuardar;
    
    @FXML
    private Button btnCancelar;
    
    @FXML
    private ImageView logoImageView;
    
    // Inicializa el controlador
    @FXML
    public void initialize() {
        cargarLogo();
        configurarComboBox();
    }
    
    // Carga el logo
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
    
    // Configura el combo box
    private void configurarComboBox() {
        cmbTipoTarea.getItems().addAll("Normal", "Critica", "Opcional", "Urgente");
    }
    
    // Establece la tarea a editar
    public void setTarea(Tarea tarea) {
        this.tareaActual = tarea;
        cargarDatosTarea();
    }
    
    // Carga los datos de la tarea en los campos
    private void cargarDatosTarea() {
        if (tareaActual != null) {
            txtNombre.setText(tareaActual.getNombre());
            cmbTipoTarea.setValue(tareaActual.getTipoTarea());
            txtDescripcion.setText(tareaActual.getDescripcion());
            
            // Cargar precondiciones
            StringBuilder precons = new StringBuilder();
            for (String p : tareaActual.getPrecondiciones()) {
                if (p != null && !p.trim().isEmpty()) {
                    precons.append(p).append("\n");
                }
            }
            txtPrecondiciones.setText(precons.toString().trim());
            
            // Cargar postcondiciones
            StringBuilder postcons = new StringBuilder();
            for (String p : tareaActual.getPostcondiciones()) {
                if (p != null && !p.trim().isEmpty()) {
                    postcons.append(p).append("\n");
                }
            }
            txtPostcondiciones.setText(postcons.toString().trim());
        }
    }
    
    // Valida que todos los campos esten llenos
    private boolean validarCampos() {
        if (txtNombre.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "Debe llenar todos los campos");
            return false;
        }
        
        if (txtDescripcion.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "Debe llenar todos los campos");
            return false;
        }
        
        if (txtPrecondiciones.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "Debe llenar todos los campos");
            return false;
        }
        
        if (txtPostcondiciones.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "Debe llenar todos los campos");
            return false;
        }
        
        return true;
    }
    
    // Guarda los cambios
    @FXML
    public void guardar() {
        if (tareaActual == null) {
            mostrarAlerta("Error", "No hay tarea seleccionada");
            return;
        }
        
        // Validar campos
        if (!validarCampos()) {
            return;
        }
        
        // Guardar datos basicos
        tareaActual.setNombre(txtNombre.getText().trim());
        tareaActual.setTipoTarea(cmbTipoTarea.getValue());
        tareaActual.setDescripcion(txtDescripcion.getText().trim());
        
        // Guardar precondiciones
        tareaActual.getPrecondiciones().clear();
        String[] preconsArray = txtPrecondiciones.getText().split("\n");
        for (String p : preconsArray) {
            String precondicion = p.trim();
            if (!precondicion.isEmpty()) {
                tareaActual.agregarPrecondicion(precondicion);
            }
        }
        
        // Guardar postcondiciones
        tareaActual.getPostcondiciones().clear();
        String[] postconsArray = txtPostcondiciones.getText().split("\n");
        for (String p : postconsArray) {
            String postcondicion = p.trim();
            if (!postcondicion.isEmpty()) {
                tareaActual.agregarPostcondicion(postcondicion);
            }
        }
        
        cerrar();
    }
    
    // Cancela la edicion
    @FXML
    public void cancelar() {
        cerrar();
    }
    
    // Cierra la ventana
    private void cerrar() {
        Stage stage = (Stage) btnGuardar.getScene().getWindow();
        stage.close();
    }
    
    // Muestra alerta de error
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}