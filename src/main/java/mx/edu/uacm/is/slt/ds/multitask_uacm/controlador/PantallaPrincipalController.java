package mx.edu.uacm.is.slt.ds.multitask_uacm.controlador;

import java.io.InputStream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import mx.edu.uacm.is.slt.ds.multitask_uacm.modelo.GestorOperaciones;
import mx.edu.uacm.is.slt.ds.multitask_uacm.modelo.Operacion;
import mx.edu.uacm.is.slt.ds.multitask_uacm.vista.AcercaDe;
import mx.edu.uacm.is.slt.ds.multitask_uacm.vista.NuevaOperacion;
import mx.edu.uacm.is.slt.ds.multitask_uacm.vista.VisorEditorOperacion;
import mx.edu.uacm.is.slt.ds.multitask_uacm.vista.VisorEditorTareas;

public class PantallaPrincipalController {

    private GestorOperaciones gestor = GestorOperaciones.obtenerInstancia();
    private Operacion operacionSeleccionada;

    @FXML
    public TableColumn<Operacion, String> tlb_tareas;
    @FXML
    public TableColumn<Operacion, String> tlb_estado;
    @FXML
    public TableColumn<Operacion, String> tlb_accion;
    @FXML
    public TableView<Operacion> tlbV_tablaViewPrincipal;
    @FXML
    private TableColumn<Operacion, String> tlb_operaciones;
    @FXML
    private ImageView logoImageView;
    @FXML
    private Button btn_nuevaOperacion;

    // Inicializa el controlador
    @FXML
    public void initialize() {
        cargarLogo();
        configurarColumnas();
        configurarBotonAccion();
        cargarDatos();
    }

    // Carga el logo desde la carpeta de recursos
    private void cargarLogo() {
        String ruta = "/mx/edu/uacm/is/slt/ds/multitask_uacm/logo/logo.png";
        InputStream inputStream = getClass().getResourceAsStream(ruta);
        if (inputStream != null) {
            logoImageView.setImage(new Image(inputStream));
        } else {
            System.err.println("Error: No se encontro el logo en " + ruta);
        }
    }

    // Configura las columnas de la tabla
    private void configurarColumnas() {
        tlb_operaciones.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tlb_tareas.setCellValueFactory(cellData -> {
            Operacion op = cellData.getValue();
            int numTareas = op.getTareas().size();
            return new javafx.beans.property.SimpleStringProperty(numTareas + " tareas");
        });
        tlb_estado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        tlb_operaciones.setStyle("-fx-alignment: CENTER;");
        tlb_tareas.setStyle("-fx-alignment: CENTER;");
        tlb_estado.setStyle("-fx-alignment: CENTER;");
    }

    // Configura los botones de accion (Abrir, Editar, Eliminar, Info)
    private void configurarBotonAccion() {
        tlb_accion.setCellFactory(param -> new TableCell<Operacion, String>() {
            private final Button btnAbrir = new Button("Abrir");
            private final Button btnEditar = new Button("Editar");
            private final Button btnEliminar = new Button("Eliminar");
            private final Button btnInfo = new Button("Info");
            private final HBox contenedor = new HBox(6, btnAbrir, btnEditar, btnEliminar, btnInfo);

            {
                // Aplica la clase CSS a todos los botones (mismo estilo)
                btnAbrir.getStyleClass().add("button");
                btnEditar.getStyleClass().add("button");
                btnEliminar.getStyleClass().add("button");
                btnInfo.getStyleClass().add("button");

                // Estilo inline para tamanos mas pequenos
                String estilo = "-fx-font-size: 11px; -fx-padding: 4 6; -fx-background-radius: 6; -fx-pref-width: 65;";
                btnAbrir.setStyle(estilo);
                btnEditar.setStyle(estilo);
                btnEliminar.setStyle(estilo);
                btnInfo.setStyle(estilo);

                contenedor.setAlignment(Pos.CENTER);

                // Accion del boton Abrir
                btnAbrir.setOnAction(e -> {
                    Operacion op = getTableRow().getItem();
                    if (op != null) {
                        VisorDeTareasController.setOperacionActual(op);
                        VisorEditorTareas.obtenerInstancia(new Stage()).mostrar();
                    }
                });

                // Accion del boton Editar
                btnEditar.setOnAction(e -> {
                    Operacion op = getTableRow().getItem();
                    if (op != null) {
                        VisorEditorOperacion.obtenerInstancia(new Stage(), op).mostrar();
                    }
                });

                // Accion del boton Eliminar
                btnEliminar.setOnAction(e -> {
                    Operacion op = getTableRow().getItem();
                    if (op != null) {
                        // Ventana de confirmacion
                        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
                        confirmacion.setTitle("Confirmar");
                        confirmacion.setHeaderText("Eliminar Operacion");
                        confirmacion.setContentText("¿Desea eliminar la operacion: " + op.getNombre() + "?");

                        if (confirmacion.showAndWait().get() == javafx.scene.control.ButtonType.OK) {
                            gestor.getOperaciones().remove(op);
                            gestor.notifyChanges();
                            tlbV_tablaViewPrincipal.refresh();
                            System.out.println("Operacion eliminada: " + op.getNombre());
                        }
                    }
                });

                // Accion del boton Info
                btnInfo.setOnAction(e -> {
                    Operacion op = getTableRow().getItem();
                    if (op != null) {
                        Alert a = new Alert(Alert.AlertType.INFORMATION);
                        a.setTitle("Informacion de la operacion");
                        a.setHeaderText(op.getNombre());
                        a.setContentText("Descripcion: " + op.getDescripcion());
                        a.showAndWait();
                    }
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
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

    // Carga los datos de las operaciones en la tabla
    private void cargarDatos() {
        ObservableList<Operacion> operaciones = FXCollections.observableArrayList(gestor.getOperaciones());
        tlbV_tablaViewPrincipal.setItems(operaciones);

        // Listener para actualizar la tabla cuando haya cambios
        gestor.addListener(() -> {
            javafx.application.Platform.runLater(() -> {
                ObservableList<Operacion> nuevasOperaciones = FXCollections.observableArrayList(gestor.getOperaciones());
                tlbV_tablaViewPrincipal.setItems(nuevasOperaciones);
                tlbV_tablaViewPrincipal.refresh();
                System.out.println("Lista de operaciones actualizada");
            });
        });
    }

    // Abre la ventana para crear nueva operacion
    @FXML
    public void onButtonNuevaOperacion() {
        NuevaOperacion.obtenerInstancia(new Stage()).mostrar();
    }

    // Abre la ventana Acerca De
    @FXML
    public void abrirAcercaDe() {
        AcercaDe.obtenerInstancia(new Stage()).mostrar();
    }
}
