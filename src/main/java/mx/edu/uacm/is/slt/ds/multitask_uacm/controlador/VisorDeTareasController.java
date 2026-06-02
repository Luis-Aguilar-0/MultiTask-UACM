package mx.edu.uacm.is.slt.ds.multitask_uacm.controlador;

import java.io.IOException;
import java.io.InputStream;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import mx.edu.uacm.is.slt.ds.multitask_uacm.modelo.GestorOperaciones;
import mx.edu.uacm.is.slt.ds.multitask_uacm.modelo.Operacion;
import mx.edu.uacm.is.slt.ds.multitask_uacm.modelo.Tarea;

public class VisorDeTareasController {

    private static Operacion operacionActual;
    private boolean hayCambios = false;

    @FXML
    private TextField txt_nombreOperacion;

    @FXML
    private TextField txt_estado;

    @FXML
    private TableView<Tarea> tablaTareasVisor;

    @FXML
    private TableColumn<Tarea, String> tlc_nombre;

    @FXML
    private TableColumn<Tarea, String> tlb_tipo;

    @FXML
    private TableColumn<Tarea, String> tlb_descripcion;

    @FXML
    private TableColumn<Tarea, Void> tlb_accionTarea;

    @FXML
    private ImageView logoImageView;

    public static void setOperacionActual(Operacion op) {
        operacionActual = op;
    }

    public static Operacion getOperacionActual() {
        return operacionActual;
    }

    @FXML
    public void initialize() {
        cargarLogo();
        configurarColumnas();
        configurarColumnaAcciones();
        cargarDatos();
    }

    private void cargarLogo() {
        String ruta = "/mx/edu/uacm/is/slt/ds/multitask_uacm/logo/logo.png";
        InputStream inputStream = getClass().getResourceAsStream(ruta);
        if (inputStream != null) {
            Image imagen = new Image(inputStream);
            logoImageView.setImage(imagen);
        }
    }

    private void configurarColumnas() {
        tlc_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tlb_tipo.setCellValueFactory(new PropertyValueFactory<>("tipoTarea"));
        tlb_descripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
    }

    private void configurarColumnaAcciones() {
        tlb_accionTarea.setCellFactory(param -> new TableCell<Tarea, Void>() {
            private final Button btnEditar = new Button("Editar");
            private final Button btnEliminar = new Button("Eliminar");
            private final Button btnInfo = new Button("Info");
            private final HBox contenedor = new HBox(5, btnEditar, btnEliminar, btnInfo);

            {
                String estiloBoton = "-fx-background-color: #C3A495; -fx-font-family: 'Arial Black'; -fx-font-size: 10px; -fx-text-fill: white; -fx-background-radius: 6; -fx-padding: 4 8;";
                btnEditar.setStyle(estiloBoton);
                btnEliminar.setStyle("-fx-background-color: #D9534F; -fx-font-family: 'Arial Black'; -fx-font-size: 10px; -fx-text-fill: white; -fx-background-radius: 6; -fx-padding: 4 8;");
                btnInfo.setStyle(estiloBoton);

                contenedor.setAlignment(Pos.CENTER);

                btnEditar.setOnAction(e -> {
                    Tarea tarea = getTableRow().getItem();
                    if (tarea != null) {
                        editarTarea(tarea);
                    }
                });

                btnEliminar.setOnAction(e -> {
                    Tarea tarea = getTableRow().getItem();
                    if (tarea != null) {
                        eliminarTarea(tarea);
                    }
                });

                btnInfo.setOnAction(e -> {
                    Tarea tarea = getTableRow().getItem();
                    if (tarea != null) {
                        mostrarInfoTarea(tarea);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty || getTableRow() == null || getTableRow().getItem() == null ? null : contenedor);
            }
        });
    }

    private void cargarDatos() {
        if (operacionActual != null) {
            txt_nombreOperacion.setText(operacionActual.getNombre());
            txt_estado.setText(operacionActual.getEstado().toString());

            ObservableList<Tarea> tareas = (ObservableList<Tarea>) operacionActual.getTareas();
            tablaTareasVisor.setItems(tareas);
        }
    }

    private void editarTarea(Tarea tarea) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/edu/uacm/is/slt/ds/multitask_uacm/fxml/EditarTarea.fxml"));
            Parent root = loader.load();

            EditarTareaController controller = loader.getController();
            controller.setTarea(tarea);

            Stage stage = new Stage();
            stage.setTitle("Editar Tarea");
            stage.setScene(new Scene(root));
            stage.initModality(javafx.stage.Modality.WINDOW_MODAL);
            stage.initOwner(tablaTareasVisor.getScene().getWindow());
            stage.showAndWait();

            tablaTareasVisor.refresh();
            hayCambios = true;

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo abrir el editor de tarea");
        }
    }

    private void eliminarTarea(Tarea tarea) {
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Desea eliminar la tarea: " + tarea.getNombre() + "?");

        if (confirmacion.showAndWait().get() == javafx.scene.control.ButtonType.OK) {
            operacionActual.getTareas().remove(tarea);
            tablaTareasVisor.refresh();
            hayCambios = true;
            System.out.println("Tarea eliminada: " + tarea.getNombre());
        }
    }

    private void mostrarInfoTarea(Tarea tarea) {
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("Informacion de la Tarea");
        info.setHeaderText(tarea.getNombre());

        String dependenciasStr = String.join(", ", tarea.getDependencias());
        String precondicionesStr = String.join("\n", tarea.getPrecondiciones());
        String postcondicionesStr = String.join("\n", tarea.getPostcondiciones());

        String mensaje = "Tipo: " + tarea.getTipoTarea() + "\n\n";
        mensaje += "Descripcion: " + tarea.getDescripcion() + "\n\n";
        mensaje += "Dependencias: " + (dependenciasStr.isEmpty() ? "Ninguna" : dependenciasStr) + "\n\n";
        mensaje += "Precondiciones: " + (precondicionesStr.isEmpty() ? "Ninguna" : "\n" + precondicionesStr) + "\n\n";
        mensaje += "Postcondiciones: " + (postcondicionesStr.isEmpty() ? "Ninguna" : "\n" + postcondicionesStr);

        info.setContentText(mensaje);
        info.showAndWait();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    @FXML
    private void abrirNuevaTarea(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/edu/uacm/is/slt/ds/multitask_uacm/fxml/NuevaTarea.fxml"));
            Parent root = loader.load();

            NuevaTareaController controller = loader.getController();
            controller.setOperacion(operacionActual);

            Stage stage = new Stage();
            stage.setTitle("Nueva Tarea");
            stage.setScene(new Scene(root));
            stage.initModality(javafx.stage.Modality.WINDOW_MODAL);
            stage.initOwner(tablaTareasVisor.getScene().getWindow());
            stage.showAndWait();

            tablaTareasVisor.refresh();
            hayCambios = true;

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo abrir la ventana de nueva tarea");
        }
    }

    @FXML
    private void cerrarVisor(ActionEvent event) {
        if (hayCambios && operacionActual != null) {
            GestorOperaciones.obtenerInstancia().notifyChanges();
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
