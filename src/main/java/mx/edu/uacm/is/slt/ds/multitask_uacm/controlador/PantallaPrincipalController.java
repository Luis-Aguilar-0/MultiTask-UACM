package mx.edu.uacm.is.slt.ds.multitask_uacm.controlador;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import mx.edu.uacm.is.slt.ds.multitask_uacm.modelo.GestorOperaciones;
import mx.edu.uacm.is.slt.ds.multitask_uacm.modelo.Operacion;
import mx.edu.uacm.is.slt.ds.multitask_uacm.vista.AcercaDe;
import mx.edu.uacm.is.slt.ds.multitask_uacm.vista.NuevaOperacion;
import mx.edu.uacm.is.slt.ds.multitask_uacm.vista.VisorEditorOperacion;
import mx.edu.uacm.is.slt.ds.multitask_uacm.vista.VisorEditorTareas;

/**
 * En esta clase se implementan todas las interacciones que realiza el usuario con
 * botones, campos de texto etc..
 */
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
    private Label texto;

    @FXML
    private Button botonVisorEditorOperacion;

    @FXML
    private Button buttonMostrarOperciones;

    @FXML
    private Button buttonMostrarInfoSistema;

    @FXML
    private Button btn_nuevaOperacion;

    @FXML
    public void initialize() {
        // ===== CONFIGURACIÓN DE COLUMNAS =====
        // Vinculación reactiva de propiedades del Modelo a las columnas de la Vista
        tlb_operaciones.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tlb_tareas.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        tlb_estado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        
        // ===== CONFIGURACIÓN ESPECIAL DE LA COLUMNA ACCIÓN CON BOTÓN =====
        tlb_accion.setCellFactory(param -> new TableCell<Operacion, String>() {
            private final Button btnAbrirVer = new Button("Abrir");
            
            {
                btnAbrirVer.setOnAction(event -> {
                    operacionSeleccionada = getTableView().getItems().get(getIndex());
                    VisorEditorTareas visorEditorTareas = VisorEditorTareas.obtenerInstancia(new Stage());
                    visorEditorTareas.mostrar();
                    System.out.println("Consola: Abriendo operación -> " + operacionSeleccionada.getNombre());
                });
            }
            
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btnAbrirVer);
                }
            }
        });
        
        // ===== LISTENER DE SELECCIÓN DE FILA =====
        tlbV_tablaViewPrincipal.getSelectionModel().selectedItemProperty().addListener((obs, anterior, seleccion) -> {
            if (seleccion != null) {
                operacionSeleccionada = seleccion;
                System.out.println("Consola: Operación seleccionada correctamente -> " + operacionSeleccionada.getNombre());
            }
        });
        
        // ===== CARGA DE DATOS Y ACTUALIZACIÓN =====
        ObservableList<Operacion> operaciones = (ObservableList<Operacion>) gestor.getOperaciones();
        operaciones.addListener((ListChangeListener<Operacion>) c -> tlbV_tablaViewPrincipal.refresh());
        tlbV_tablaViewPrincipal.setItems(operaciones);
    }

    @FXML
    public void onButtonVistaEditorOperacion() {
        Operacion seleccionada = tlbV_tablaViewPrincipal.getSelectionModel().getSelectedItem();

        if (seleccionada == null) {
            System.out.println("Consola: Error, debes seleccionar una operación de la tabla.");
            return;
        }

        VisorEditorOperacionController.guardarReferenciaOperacion(seleccionada);
        VisorEditorOperacion visorEditorOperacion = VisorEditorOperacion.obtenerInstancia(new Stage());
        visorEditorOperacion.mostrar();
        tlbV_tablaViewPrincipal.refresh();
    }

    @FXML
    public void onButtonNuevaOperacion() {
        NuevaOperacion nuevaOperacion = NuevaOperacion.obtenerInstancia(new Stage());
        nuevaOperacion.mostrar();
    }

    @FXML
    public void onButtonMostrarInfoSistemaClick() {
        AcercaDe acerdaDe = AcercaDe.obtenerInstancia(new Stage());
        acerdaDe.mostrar();
    }

    @FXML
    public void onButtonMostrarOperacioes() {
        tlbV_tablaViewPrincipal.refresh();
    }

    @FXML
    protected void onHelloButtonClick() {
        texto.setText("Sistema MultiTask-UACM listo.");
    }

    @FXML
    private void abrirAcercaDe() {
        AcercaDe acerca = AcercaDe.obtenerInstancia(new Stage());
        acerca.mostrar();
    }
}