package mx.edu.uacm.is.slt.ds.multitask_uacm.controlador;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mx.edu.uacm.is.slt.ds.multitask_uacm.modelo.Estado;
import mx.edu.uacm.is.slt.ds.multitask_uacm.modelo.GestorOperaciones;
import mx.edu.uacm.is.slt.ds.multitask_uacm.modelo.Operacion;
import mx.edu.uacm.is.slt.ds.multitask_uacm.modelo.Tarea;

public class VisorEditorOperacionController {

    private Operacion operacionActual;
    private static Operacion operacionPuente;

    @FXML private TextField txtNombreOperacionEdicion;
    @FXML private ComboBox<String> comboEstadoOperacion;
    @FXML private ListView<Tarea> listaTareasEdicion;

    // Metodo estatico para transferir la operacion seleccionada entre pantallas
    public static void guardarReferenciaOperacion(Operacion operacion) {
        operacionPuente = operacion;
    }

    // Llena los combos y los datos del modelo al arrancar la vista
    @FXML
    public void initialize() {
        comboEstadoOperacion.getItems().setAll(
            Estado.NO_EJECUTADA.name(),
            Estado.EN_EJECUCION.name(),
            Estado.PAUSADO.name(),
            Estado.DETENIDA.name(),
            Estado.FINALIZADA.name()
        );

        if (operacionPuente != null) {
            this.operacionActual = operacionPuente;
            mostrarDatos();
        }
    }

    // Pinta la informacion del modelo en los controles de JavaFX
    private void mostrarDatos() {
        if (operacionActual == null) return;
        txtNombreOperacionEdicion.setText(operacionActual.getNombre());
        Estado e = operacionActual.getEstado();
        comboEstadoOperacion.setValue(e != null ? e.name() : Estado.NO_EJECUTADA.name());
        ObservableList<Tarea> lista = FXCollections.observableArrayList(operacionActual.getTareas());
        listaTareasEdicion.setItems(lista);
    }

    public void setOperacionActual(Operacion operacion) {
        this.operacionActual = operacion;
        mostrarDatos();
    }

    // Valida y guarda las modificaciones de la operacion de regreso en la lista global
    @FXML
    public void guardar() {
        if (operacionActual == null) return;

        String nombre = txtNombreOperacionEdicion.getText().trim();
        if (nombre.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "El nombre no puede estar vacio.").showAndWait();
            return;
        }

        operacionActual.setNombre(nombre);
        String estadoStr = comboEstadoOperacion.getValue();
        if (estadoStr != null) {
            try {
                operacionActual.setEstado(Estado.valueOf(estadoStr));
            } catch (IllegalArgumentException ex) {
                System.err.println("Estado invalido: " + estadoStr);
            }
        }

        // Buscamos el indice de la operacion vieja para reemplazarla con la nueva reactivamente
        int indice = GestorOperaciones.obtenerInstancia().getOperaciones().indexOf(operacionActual);
        if (indice >= 0) {
            GestorOperaciones.obtenerInstancia().getOperaciones().set(indice, operacionActual);
        }

        new Alert(Alert.AlertType.INFORMATION, "Datos guardados correctamente.").showAndWait();
        descartar();
    }

    // Cierra el cuadro flotante
    @FXML
    public void descartar() {
        if (txtNombreOperacionEdicion != null && txtNombreOperacionEdicion.getScene() != null) {
            ((Stage) txtNombreOperacionEdicion.getScene().getWindow()).close();
        }
    }

    // Metodos complementarios de la interfaz
    public void abrirVisor() {}
    public void modificarNombre(String nombre) {}
    public void modificarTareas() {}
    public void modificarOrden() {}
    public void pausar() {}
    public void reanudar() {}
    public void detener() {}
    public void moverTareaArribaEdicion() {}
    public void moverTareaAbajoEdicion() {}
    public void eliminarTareaSeleccionada() {}
    public void agregarNuevaTarea() {}
}