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
    private TableColumn<Tarea, Void> tlb_accionTarea;

    @FXML
    private ImageView logoImageView;

    @FXML
    private Button btnNuevaTarea;

    @FXML
    private Button btnCerrar;

    // Establece la operacion actual a visualizar
    public static void setOperacionActual(Operacion op) {
        operacionActual = op;
    }

    // Obtiene la operacion actual
    public static Operacion getOperacionActual() {
        return operacionActual;
    }

    // Inicializa el controlador
    @FXML
    public void initialize() {
        cargarLogo();
        configurarColumnas();
        configurarColumnaAcciones();
        cargarDatos();
    }

    // Carga el logo desde la carpeta de recursos
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

    // Configura las columnas de la tabla de tareas
    private void configurarColumnas() {
        tlc_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tlb_tipo.setCellValueFactory(new PropertyValueFactory<>("tipoTarea"));
    }

    // Configura los botones de accion para cada tarea
    private void configurarColumnaAcciones() {
        tlb_accionTarea.setCellFactory(param -> new TableCell<Tarea, Void>() {
            private final Button btnEditar = new Button("Editar");
            private final Button btnEliminar = new Button("Eliminar");
            private final Button btnInfo = new Button("Info");
            private final HBox contenedor = new HBox(8, btnEditar, btnEliminar, btnInfo);

            {
                // Aplica la clase CSS a los botones
                btnEditar.getStyleClass().add("button");
                btnEliminar.getStyleClass().add("button");
                btnInfo.getStyleClass().add("button");

                // Estilo para que los botones se vean bien
                String estiloBoton = "-fx-font-size: 12px; -fx-padding: 5 12; -fx-background-radius: 6; -fx-min-width: 70;";
                btnEditar.setStyle(estiloBoton);
                btnEliminar.setStyle(estiloBoton);
                btnInfo.setStyle(estiloBoton);

                contenedor.setAlignment(Pos.CENTER);

                // Accion del boton Editar
                btnEditar.setOnAction(e -> {
                    Tarea tarea = getTableRow().getItem();
                    if (tarea != null) {
                        editarTarea(tarea);
                    }
                });

                // Accion del boton Eliminar
                btnEliminar.setOnAction(e -> {
                    Tarea tarea = getTableRow().getItem();
                    if (tarea != null) {
                        // Ventana de confirmacion
                        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
                        confirmacion.setTitle("Confirmar");
                        confirmacion.setHeaderText("Eliminar Tarea");
                        confirmacion.setContentText("¿Desea eliminar la tarea: " + tarea.getNombre() + "?");

                        if (confirmacion.showAndWait().get() == javafx.scene.control.ButtonType.OK) {
                            operacionActual.getTareas().remove(tarea);
                            tablaTareasVisor.refresh();
                            hayCambios = true;
                            GestorOperaciones.obtenerInstancia().notifyChanges();
                            System.out.println("Tarea eliminada: " + tarea.getNombre());
                        }
                    }
                });

                // Accion del boton Info
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
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setGraphic(null);
                } else {
                    setGraphic(contenedor);
                    setAlignment(Pos.CENTER);
                }
            }
        });
    }

    // Carga los datos de la operacion y sus tareas
    private void cargarDatos() {
        if (operacionActual != null) {
            txt_nombreOperacion.setText(operacionActual.getNombre());
            txt_estado.setText(operacionActual.getEstado().toString());

            ObservableList<Tarea> tareas = (ObservableList<Tarea>) operacionActual.getTareas();
            tablaTareasVisor.setItems(tareas);
        }
    }

    // Abre ventana para editar una tarea
    private void editarTarea(Tarea tarea) {
        try {
            String rutaFXML = "/mx/edu/uacm/is/slt/ds/multitask_uacm/fxml/EditarTarea.fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));

            if (loader.getLocation() == null) {
                System.err.println("Error: No se encuentra el archivo " + rutaFXML);
                mostrarAlerta("Error", "No se encuentra el editor de tareas");
                return;
            }

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
            GestorOperaciones.obtenerInstancia().notifyChanges();

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo abrir el editor de tarea: " + e.getMessage());
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

    // Muestra una alerta de error
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    // Abre ventana para crear nueva tarea
    @FXML
    private void abrirNuevaTarea(ActionEvent event) {
        try {
            String rutaFXML = "/mx/edu/uacm/is/slt/ds/multitask_uacm/fxml/NuevaTarea.fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));

            if (loader.getLocation() == null) {
                System.err.println("Error: No se encontro el archivo " + rutaFXML);
                mostrarAlerta("Error", "No se encontro el archivo NuevaTarea.fxml");
                return;
            }

            Parent root = loader.load();

            Object controller = loader.getController();
            System.out.println("Controlador cargado: " + controller.getClass().getName());

            if (controller instanceof NuevaTareaController) {
                NuevaTareaController nuevaTareaController = (NuevaTareaController) controller;
                nuevaTareaController.setOperacion(operacionActual);
            } else {
                System.err.println("Error: El controlador no es de tipo NuevaTareaController");
                mostrarAlerta("Error", "Error interno al cargar la ventana");
                return;
            }

            Stage stage = new Stage();
            stage.setTitle("Nueva Tarea");
            stage.setScene(new Scene(root));
            stage.initModality(javafx.stage.Modality.WINDOW_MODAL);
            stage.initOwner(tablaTareasVisor.getScene().getWindow());
            stage.showAndWait();

            tablaTareasVisor.refresh();
            hayCambios = true;
            GestorOperaciones.obtenerInstancia().notifyChanges();

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo abrir la ventana de nueva tarea: " + e.getMessage());
        }
    }

    // Cierra el visor y notifica cambios si los hay
    @FXML
    private void cerrarVisor(ActionEvent event) {
        if (hayCambios && operacionActual != null) {
            GestorOperaciones.obtenerInstancia().notifyChanges();
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
