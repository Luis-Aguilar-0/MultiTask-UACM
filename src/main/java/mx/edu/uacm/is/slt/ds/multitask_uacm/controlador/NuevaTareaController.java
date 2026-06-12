package mx.edu.uacm.is.slt.ds.multitask_uacm.controlador;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mx.edu.uacm.is.slt.ds.multitask_uacm.modelo.Operacion;
import mx.edu.uacm.is.slt.ds.multitask_uacm.modelo.Tarea;

public class NuevaTareaController {

    // Componentes de la ventana vinculados al FXML
    @FXML private TextField txtNombreTarea;
    @FXML private TextField txtDescripcion;
    @FXML private TextField txtDependencias;
    @FXML private ComboBox<String> comboTipoTarea;

    // Referencias para el control de datos y las ventanas flotantes
    private Operacion operacionActual;
    private Stage stagePrecondiciones = null;
    private Stage stagePostcondiciones = null;

    // Se ejecuta automaticamente al cargar la ventana para llenar las opciones del combo
    @FXML
    public void initialize() {
        comboTipoTarea.getItems().addAll(
                "Secuencial (depende de otras tareas)",
                "Inicial (puede iniciar sola)",
                "Hoja (no genera otras tareas)"
        );
        comboTipoTarea.getSelectionModel().selectFirst();
    }

    // Recibe la operacion actual desde el visor de tareas
    public void setOperacion(Operacion op) {
        this.operacionActual = op;
    }

    // Guarda la nueva tarea con los datos del formulario
    @FXML
    private void guardarTarea(ActionEvent event) {
        String nombre = txtNombreTarea.getText().trim();
        String descripcion = txtDescripcion.getText().trim();
        String dependencia = txtDependencias.getText().trim();
        String tipo = comboTipoTarea.getValue();

        // Validamos que por lo menos pongan el nombre
        if (nombre.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "El nombre de la tarea no puede estar vacio.");
            return;
        }

        // Creamos el objeto tarea y le pasamos los textos
        Tarea tarea = new Tarea(nombre, descripcion);
        tarea.setTipoTarea(tipo);
        
        // Si el usuario escribio algo en dependencias lo agregamos
        if (!dependencia.isEmpty()) {
            tarea.agregarDependencia(dependencia);
        }

        // Si tenemos una operacion valida guardamos la tarea y cerramos
        if (operacionActual != null) {
            operacionActual.agregarTarea(tarea);
            mostrarAlerta(Alert.AlertType.INFORMATION, "Tarea guardada correctamente.");
            volver(event);
        } else {
            mostrarAlerta(Alert.AlertType.WARNING, "No hay una operacion para asociar esta tarea.");
        }
    }

    // Abre la ventana flotante de precondiciones
    @FXML
    private void abrirPrecondiciones(ActionEvent event) {
        if (stagePrecondiciones != null && stagePrecondiciones.isShowing()) {
            stagePrecondiciones.toFront();
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/edu/uacm/is/slt/ds/multitask_uacm/fxml/Precondiciones.fxml"));
            Parent root = loader.load();
            stagePrecondiciones = new Stage();
            stagePrecondiciones.setTitle("Precondiciones");
            stagePrecondiciones.setScene(new Scene(root));
            stagePrecondiciones.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Abre la ventana flotante de postcondiciones
    @FXML
    private void abrirPostcondiciones(ActionEvent event) {
        if (stagePostcondiciones != null && stagePostcondiciones.isShowing()) {
            stagePostcondiciones.toFront();
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/edu/uacm/is/slt/ds/multitask_uacm/fxml/Postcondiciones.fxml"));
            Parent root = loader.load();
            stagePostcondiciones = new Stage();
            stagePostcondiciones.setTitle("Postcondiciones");
            stagePostcondiciones.setScene(new Scene(root));
            stagePostcondiciones.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Limpia las cajas de texto y cierra la ventana activa
    @FXML
    private void cancelar(ActionEvent event) {
        txtNombreTarea.clear();
        txtDescripcion.clear();
        txtDependencias.clear();
        comboTipoTarea.getSelectionModel().selectFirst();
        volver(event);
    }

    // Metodo generico para cerrar la ventana desde cualquier boton
    @FXML
    private void volver(ActionEvent event) {
        Stage escena = (Stage) ((Node) event.getSource()).getScene().getWindow();
        escena.close();
    }

    // Muestra los mensajes de aviso o exito de forma sencilla
    private void mostrarAlerta(Alert.AlertType tipo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(tipo == Alert.AlertType.WARNING ? "Aviso" : "Exito");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}