package mx.edu.uacm.is.slt.ds.multitask_uacm.controlador;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import mx.edu.uacm.is.slt.ds.multitask_uacm.modelo.GestorOperaciones;
import mx.edu.uacm.is.slt.ds.multitask_uacm.modelo.Operacion;
import mx.edu.uacm.is.slt.ds.multitask_uacm.vista.AcercaDe;
import mx.edu.uacm.is.slt.ds.multitask_uacm.vista.NuevaOperacion;
import mx.edu.uacm.is.slt.ds.multitask_uacm.vista.VisorEditorOperacion;

public class PantallaPrincipalController {

    private GestorOperaciones gestor = GestorOperaciones.obtenerInstancia(); 
    private Operacion operacionSeleccionada;

    @FXML public TableView<Operacion> tlbV_tablaViewPrincipal;
    @FXML private TableColumn<Operacion, String> tlb_operaciones;
    @FXML public TableColumn<Operacion, String> tlb_tareas; // Muestra descripción/conteo
    @FXML public TableColumn<Operacion, String> tlb_estado;
    @FXML public TableColumn<Operacion, String> tlb_accion;

    @FXML private Label texto;
    @FXML private Button botonVisorEditorOperacion;
    @FXML private Button buttonMostrarOperciones;
    @FXML private Button buttonMostrarInfoSistema;
    @FXML private Button btn_nuevaOperacion;

    @FXML
    public void initialize() {
        // Vinculación reactiva de propiedades del Modelo a las columnas de la Vista
        tlb_operaciones.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tlb_tareas.setCellValueFactory(new PropertyValueFactory<>("descripcion")); // ERS: Ver descripción completa
        tlb_estado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        tlb_accion.setCellValueFactory(new PropertyValueFactory<>("estado")); // Reutiliza estado o propiedad de control

        // Forzamos la carga inicial de datos de prueba al arrancar el MVP
        if (gestor.getOperaciones().isEmpty()) {
            gestor.cargarOperacionesDePrueba();
        }

        // SOLUCIÓN AL DESFASE: Captura la fila seleccionada físicamente por el usuario
        tlbV_tablaViewPrincipal.getSelectionModel().selectedItemProperty().addListener((obs, anterior, seleccion) -> {
            if (seleccion != null) {
                operacionSeleccionada = seleccion;
                System.out.println("Consola: Operación seleccionada correctamente -> " + operacionSeleccionada.getNombre());
            }
        });

        ObservableList<Operacion> operaciones = (ObservableList<Operacion>) gestor.getOperaciones();
        operaciones.addListener((ListChangeListener<Operacion>) c -> tlbV_tablaViewPrincipal.refresh());

        tlbV_tablaViewPrincipal.setItems(operaciones);
    }

    @FXML
    public void onButtonVistaEditorOperacion() {
        // Verificación de selección de fila actual
        Operacion seleccionada = tlbV_tablaViewPrincipal.getSelectionModel().getSelectedItem();
        
        if (seleccionada == null) {
            System.out.println("Consola: Error, debes seleccionar una operación de la tabla.");
            return;
        }

        // Inyectamos la operación exacta elegida al puente estático
        VisorEditorOperacionController.guardarReferenciaOperacion(seleccionada);
        
        // Desplegamos el editor/visor correspondiente
        VisorEditorOperacion visorEditorOperacion = VisorEditorOperacion.obtenerInstancia(new Stage());
        visorEditorOperacion.mostrar();
        
        // Al cerrar la ventana flotante, forzamos el refresco para visualizar los cambios guardados
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