/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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

    @FXML private TextField txtNombreTarea;
    @FXML private TextField txtDescripcion;
    @FXML private TextField txtDependencias;
    @FXML private ComboBox<String> comboTipoTarea;

    private Operacion operacionActual;


    private Stage stagePrecondiciones = null;
    private Stage stagePostcondiciones = null;

    @FXML
    public void initialize() {
        comboTipoTarea.getItems().addAll(
                "Secuencial (depende de otras tareas)",
                "Inicial (puede iniciar sola)",
                "Hoja (no genera otras tareas)"
        );
        comboTipoTarea.getSelectionModel().selectFirst();
    }

    public void setOperacion(Operacion op) {
        this.operacionActual = op;
    }

    @FXML
    private void guardarTarea(ActionEvent event) {
        String nombre = txtNombreTarea.getText().trim();
        String descripcion = txtDescripcion.getText().trim();
        String dependencia = txtDependencias.getText().trim();
        String tipo = comboTipoTarea.getValue();

        if (nombre.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "El nombre de la tarea no puede estar vacío.");
            return;
        }

        Tarea tarea = new Tarea(nombre, descripcion);
        tarea.setTipoTarea(tipo);
        if (!dependencia.isEmpty()) {
            tarea.agregarDependencia(dependencia);
        }

        if (operacionActual != null) {
            operacionActual.agregarTarea(tarea);
            mostrarAlerta(Alert.AlertType.INFORMATION, "Tarea '" + nombre + "' guardada correctamente.");
            volverAlVisor(event);
        } else {
            mostrarAlerta(Alert.AlertType.WARNING, "No hay operación seleccionada. No se pudo guardar la tarea.");
        }
    }

    @FXML
    private void abrirPrecondiciones(ActionEvent event) {

        if (stagePrecondiciones != null && stagePrecondiciones.isShowing()) {
            stagePrecondiciones.toFront();
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/mx/edu/uacm/is/slt/ds/multitask_uacm/fxml/Precondiciones.fxml")
            );
            Parent root = loader.load();
            stagePrecondiciones = new Stage();
            stagePrecondiciones.setTitle("Precondiciones");
            stagePrecondiciones.setScene(new Scene(root));
            stagePrecondiciones.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirPostcondiciones(ActionEvent event) {

        if (stagePostcondiciones != null && stagePostcondiciones.isShowing()) {
            stagePostcondiciones.toFront();
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/mx/edu/uacm/is/slt/ds/multitask_uacm/fxml/Postcondiciones.fxml")
            );
            Parent root = loader.load();
            stagePostcondiciones = new Stage();
            stagePostcondiciones.setTitle("Postcondiciones");
            stagePostcondiciones.setScene(new Scene(root));
            stagePostcondiciones.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void cancelar(ActionEvent event) {
        txtNombreTarea.clear();
        txtDescripcion.clear();
        txtDependencias.clear();
        comboTipoTarea.getSelectionModel().selectFirst();
        volver(event);
    }

    @FXML
    private void volver(ActionEvent event) {
        Stage ecena = (Stage) ((Node) event.getSource()).getScene().getWindow();
        ecena.close();
        //volverAlVisor(event);
    }

    private void volverAlVisor(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/mx/edu/uacm/is/slt/ds/multitask_uacm/fxml/VisorDeTareas.fxml")
            );
            Parent root = loader.load();

            VisorDeTareasController controlador = loader.getController();
            controlador.setOperacion(operacionActual);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Visor de Tareas");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(tipo == Alert.AlertType.WARNING ? "Aviso" : "Éxito");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}