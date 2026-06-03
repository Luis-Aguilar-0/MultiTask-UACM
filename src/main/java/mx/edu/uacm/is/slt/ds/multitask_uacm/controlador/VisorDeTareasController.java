/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.edu.uacm.is.slt.ds.multitask_uacm.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import mx.edu.uacm.is.slt.ds.multitask_uacm.modelo.Estado;
import mx.edu.uacm.is.slt.ds.multitask_uacm.modelo.GestorOperaciones;
import mx.edu.uacm.is.slt.ds.multitask_uacm.modelo.Operacion;
import mx.edu.uacm.is.slt.ds.multitask_uacm.modelo.Tarea;

public class VisorDeTareasController implements Initializable {

    @FXML private Button btnNuevaTarea;
    @FXML private Button btn_cancelar;
    @FXML private Button btn_PausarOperacion;
    @FXML private Button btn_detenerOperacion;
    @FXML private Button btn_reanudarOéracion;
    @FXML private Button btn_bolver;

    @FXML private TextField txt_nombreOperacion;
    @FXML private TextField txt_estado;

    @FXML private TableView<Tarea> tableView;
    @FXML private TableColumn<Tarea, String> tlc_nombre;
    @FXML private TableColumn<Tarea, String> tlb_descripcion;
    @FXML private TableColumn<Tarea, String> tlb_acciones;

    private GestorOperaciones gestor;
    private Operacion operacionActual;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gestor = GestorOperaciones.obtenerInstancia();

        tlc_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tlb_descripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        tlb_acciones.setCellValueFactory(new PropertyValueFactory<>("estado"));

        if (!gestor.getOperaciones().isEmpty()) {
            operacionActual = gestor.obtenerOperacion(0);
            cargarDatosOperacion();
        }

        tableView.getSelectionModel().selectedItemProperty().addListener(
                (obs, anterior, actual) -> {
                    if (actual != null) {
                        txt_estado.setText(actual.getEstado() != null ? actual.getEstado() : "");
                    }
                }
        );
    }

    public void setOperacion(Operacion op) {
        this.operacionActual = op;
        cargarDatosOperacion();
    }

    private void cargarDatosOperacion() {
        if (operacionActual == null) return;
        txt_nombreOperacion.setText(operacionActual.getNombre());
        txt_estado.setText(estadoTexto(operacionActual.getEstado()));
        ObservableList<Tarea> tareas =
                FXCollections.observableArrayList(operacionActual.getTareas());
        tableView.setItems(tareas);
    }


    private String estadoTexto(Estado estado) {
        if (estado == null) return "";
        switch (estado) {
            case EN_EJECUCION:  return "En ejecución";
            case PAUSADO:       return "Pausada";
            case DETENIDA:      return "Detenida";
            case NO_EJECUTADA:  return "No ejecutada";
            default:            return estado.toString();
        }
    }

    @FXML
    private void pausarOperacion(ActionEvent event) {
        if (operacionActual == null) { mostrarAlerta("No hay operación cargada."); return; }
        operacionActual.pausar();
        txt_estado.setText(estadoTexto(operacionActual.getEstado()));
        tableView.refresh();
        mostrarInfo("Pausada", "La operación fue pausada.");
    }

    @FXML
    private void detenerOperacion(ActionEvent event) {
        if (operacionActual == null) { mostrarAlerta("No hay operación cargada."); return; }
        operacionActual.detener();
        txt_estado.setText(estadoTexto(operacionActual.getEstado()));
        tableView.refresh();
        mostrarInfo("Detenida", "La operación fue detenida.");
    }

    @FXML
    private void reanudarOperacion(ActionEvent event) {
        if (operacionActual == null) { mostrarAlerta("No hay operación cargada."); return; }
        operacionActual.reanudar();
        txt_estado.setText(estadoTexto(operacionActual.getEstado()));
        tableView.refresh();
        mostrarInfo("Reanudada", "La operación fue reanudada.");
    }

    @FXML
    private void volver(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void cancelar(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void abrirNuevaTarea(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/mx/edu/uacm/is/slt/ds/multitask_uacm/fxml/NuevaTarea.fxml")
            );
            Parent root = loader.load();

            NuevaTareaController controlador = loader.getController();
            controlador.setOperacion(operacionActual);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Nueva Tarea");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Aviso");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarInfo(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}