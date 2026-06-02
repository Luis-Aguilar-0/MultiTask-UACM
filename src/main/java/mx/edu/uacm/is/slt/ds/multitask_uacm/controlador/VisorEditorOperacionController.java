package mx.edu.uacm.is.slt.ds.multitask_uacm.controlador;

import java.io.InputStream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import mx.edu.uacm.is.slt.ds.multitask_uacm.modelo.Estado;
import mx.edu.uacm.is.slt.ds.multitask_uacm.modelo.GestorOperaciones;
import mx.edu.uacm.is.slt.ds.multitask_uacm.modelo.Operacion;
import mx.edu.uacm.is.slt.ds.multitask_uacm.modelo.Tarea;

public class VisorEditorOperacionController {

    private Operacion operacionActual;
    private static Operacion operacionPuente;

    @FXML 
    private TextField txtNombreOperacionEdicion;
    
    @FXML 
    private ComboBox<String> comboEstadoOperacion;
    
    @FXML 
    private ListView<Tarea> listaTareasEdicion;
    
    @FXML 
    private TextArea txtDescripcionOperacion;
    
    @FXML 
    private ImageView logoImageView;
    
    @FXML 
    private Button btnGuardar;
    
    @FXML 
    private Button btnCancelar;

    public static void guardarReferenciaOperacion(Operacion operacion) {
        operacionPuente = operacion;
    }

    @FXML
    public void initialize() {
        cargarLogo();
        configurarComboBox();
        cargarOperacion();
    }

    private void cargarLogo() {
        if (logoImageView != null) {
            String ruta = "/mx/edu/uacm/is/slt/ds/multitask_uacm/logo/logo.png";
            InputStream inputStream = getClass().getResourceAsStream(ruta);
            if (inputStream != null) {
                Image imagen = new Image(inputStream);
                logoImageView.setImage(imagen);
            }
        }
    }

    private void configurarComboBox() {
        if (comboEstadoOperacion != null) {
            comboEstadoOperacion.getItems().clear();
            comboEstadoOperacion.getItems().addAll("NO_EJECUTADA", "EN_EJECUCION", "PAUSADA", "DETENIDA", "FINALIZADA");
        }
    }

    private void cargarOperacion() {
        if (operacionPuente != null) {
            this.operacionActual = operacionPuente;
            mostrarDatos();
        }
    }

    private void mostrarDatos() {
        if (operacionActual == null) return;
        
        txtNombreOperacionEdicion.setText(operacionActual.getNombre());
        txtDescripcionOperacion.setText(operacionActual.getDescripcion());
        
        if (operacionActual.getEstado() != null) {
            comboEstadoOperacion.setValue(operacionActual.getEstado().toString());
        }
        
        if (operacionActual.getTareas() != null) {
            ObservableList<Tarea> tareasEdicion = FXCollections.observableArrayList(operacionActual.getTareas());
            listaTareasEdicion.setItems(tareasEdicion);
        }
    }

    @FXML
    public void guardar() {
        if (operacionActual == null) {
            mostrarAlerta("Error", "No hay operacion para guardar");
            return;
        }
        
        try {
            String nuevoNombre = txtNombreOperacionEdicion.getText().trim();
            if (nuevoNombre.isEmpty()) {
                mostrarAlerta("Error", "El nombre de la operacion no puede estar vacio");
                return;
            }
            
            operacionActual.setNombre(nuevoNombre);
            operacionActual.setDescripcion(txtDescripcionOperacion.getText());
            
            if (comboEstadoOperacion.getValue() != null) {
                String estadoStr = comboEstadoOperacion.getValue();
                switch (estadoStr) {
                    case "NO_EJECUTADA": operacionActual.setEstado(Estado.NO_EJECUTADA); break;
                    case "EN_EJECUCION": operacionActual.setEstado(Estado.EN_EJECUCION); break;
                    case "PAUSADA": operacionActual.setEstado(Estado.PAUSADA); break;
                    case "DETENIDA": operacionActual.setEstado(Estado.DETENIDA); break;
                    case "FINALIZADA": operacionActual.setEstado(Estado.FINALIZADA); break;
                }
            }
            
            // Notificar cambios al gestor para actualizar la tabla
            GestorOperaciones.obtenerInstancia().notifyChanges();
            
            System.out.println("Operacion guardada: " + operacionActual.getNombre());
            mostrarAlertaExito("Exito", "Operacion guardada correctamente");
            cerrarVentana();
            
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo guardar la operacion");
        }
    }

    @FXML
    public void cerrarVentana() {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlertaExito(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}