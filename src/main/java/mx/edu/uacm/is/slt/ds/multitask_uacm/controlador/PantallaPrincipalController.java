package mx.edu.uacm.is.slt.ds.multitask_uacm.controlador;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import mx.edu.uacm.is.slt.ds.multitask_uacm.modelo.Estado;
import mx.edu.uacm.is.slt.ds.multitask_uacm.modelo.GestorOperaciones;
import mx.edu.uacm.is.slt.ds.multitask_uacm.modelo.Operacion;
import mx.edu.uacm.is.slt.ds.multitask_uacm.vista.AcercaDe;
import mx.edu.uacm.is.slt.ds.multitask_uacm.vista.NuevaOperacion;
import mx.edu.uacm.is.slt.ds.multitask_uacm.vista.VisorEditorOperacion;
import mx.edu.uacm.is.slt.ds.multitask_uacm.vista.VisorEditorTareas;

public class PantallaPrincipalController {

    // Instancia del gestor global de operaciones de la aplicacion
    private GestorOperaciones gestor = GestorOperaciones.obtenerInstancia();
    private Operacion operacionSeleccionada;

    // Componentes de la vista inyectados desde el FXML
    @FXML
    public TableColumn<Operacion, String> tlb_tareas;
    @FXML
    public TableColumn<Operacion, Estado> tlb_estado;
    @FXML
    public TableColumn<Operacion, String> tlb_accion;
    @FXML
    public TableView<Operacion> tlbV_tablaViewPrincipal;
    @FXML
    private TableColumn<Operacion, String> tlb_operaciones;
    @FXML
    private TableColumn<Operacion, String> tlb_descripcion;
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

    // Metodo de arranque automatico para configurar la tabla principal
    @FXML
    public void initialize() {
        // Enlazamos las columnas basicas con los atributos del modelo Operacion
        tlb_operaciones.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tlb_descripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        // Calculamos en tiempo real la cantidad de tareas por cada operacion
        tlb_tareas.setCellValueFactory(cellData -> {
            Operacion operacion = cellData.getValue();
            int numTareas = operacion.getTareas().size();
            return new SimpleStringProperty(numTareas + " tareas");
        });

        // Configuramose el diseño del boton de estado heredando del CSS
        tlb_estado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        tlb_estado.setCellFactory(column -> new TableCell<Operacion, Estado>() {
            @Override
            protected void updateItem(Estado item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    Button btnEstadoEstilo = new Button();
                    btnEstadoEstilo.setDisable(true);
                    btnEstadoEstilo.getStyleClass().add("boton-estado-celda");
                    setAlignment(Pos.CENTER);

                    // Evaluamos el estado para asignar el texto plano correspondiente
                    switch (item) {
                        case EN_EJECUCION:
                            btnEstadoEstilo.setText("En ejecucion");
                            break;
                        case PAUSADO:
                            btnEstadoEstilo.setText("Pausada");
                            break;
                        case DETENIDA:
                            btnEstadoEstilo.setText("Detenida");
                            break;
                        case NO_EJECUTADA:
                            btnEstadoEstilo.setText("No ejecutada");
                            break;
                        case FINALIZADA:
                            btnEstadoEstilo.setText("Finalizada");
                            break;
                    }
                    setGraphic(btnEstadoEstilo);
                }
            }
        });

        // Configuramos la botonera de acciones dentro de cada fila de la tabla
        tlb_accion.setCellFactory(param -> new TableCell<Operacion, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Button btnAbrirVer = new Button("Abrir");
                    Button btnEliminarOp = new Button("Eliminar");
                    HBox contenedorBotones = new HBox();

                    // Agregamos las clases cargadas desde estilos.css
                    contenedorBotones.getStyleClass().add("contenedor-acciones-celda");
                    btnAbrirVer.getStyleClass().add("button");
                    btnEliminarOp.getStyleClass().add("button");

                    // Evento para el boton abrir que levanta el visor de hilos
                    btnAbrirVer.setOnAction(event -> {
                        operacionSeleccionada = getTableView().getItems().get(getIndex());
                        Stage visorStage = new Stage();
                        visorStage.setOnHidden(e -> tlbV_tablaViewPrincipal.refresh());

                        VisorEditorTareas visorEditorTareas = VisorEditorTareas.obtenerInstancia(visorStage, operacionSeleccionada);
                        visorEditorTareas.mostrar();
                        System.out.println("Consola: Abriendo operacion -> " + operacionSeleccionada.getNombre());
                    });

                    // Evento para el boton eliminar de la rejilla principal
                    btnEliminarOp.setOnAction(event -> {
                        Operacion op = getTableView().getItems().get(getIndex());
                        if (op.getEstado() == Estado.EN_EJECUCION) {
                            System.out.println("Consola: No se puede borrar una operacion activa.");
                            return;
                        }
                        gestor.getOperaciones().remove(op);
                        System.out.println("Consola: Operacion eliminada -> " + op.getNombre());
                    });

                    contenedorBotones.getChildren().addAll(btnAbrirVer, btnEliminarOp);
                    setGraphic(contenedorBotones);
                }
            }
        });

        // Escuchamos cual fila selecciona el usuario en la interfaz
        tlbV_tablaViewPrincipal.getSelectionModel().selectedItemProperty().addListener((obs, anterior, seleccion) -> {
            if (seleccion != null) {
                operacionSeleccionada = seleccion;
                System.out.println("Consola: Operacion seleccionada -> " + operacionSeleccionada.getNombre());
            }
        });

        // Conectamos la lista observable del gestor para refrescar la tabla al hacer cambios
        ObservableList<Operacion> operaciones = (ObservableList<Operacion>) gestor.getOperaciones();
        operaciones.addListener((ListChangeListener<Operacion>) c -> tlbV_tablaViewPrincipal.refresh());
        tlbV_tablaViewPrincipal.setItems(operaciones);
    }

    // Abre la ventana de edicion para la operacion seleccionada
    @FXML
    public void onButtonVistaEditorOperacion() {
        Operacion seleccionada = tlbV_tablaViewPrincipal.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            System.out.println("Consola: Error, debes seleccionar una operacion.");
            return;
        }
        VisorEditorOperacionController.guardarReferenciaOperacion(seleccionada);
        VisorEditorOperacion visorEditorOperacion = VisorEditorOperacion.obtenerInstancia(new Stage());
        visorEditorOperacion.mostrar();
        tlbV_tablaViewPrincipal.refresh();
    }

    // Levanta la ventana flotante para crear una nueva operacion vacia
    @FXML
    public void onButtonNuevaOperacion() {
        NuevaOperacion nuevaOperacion = NuevaOperacion.obtenerInstancia(new Stage());
        nuevaOperacion.mostrar();
    }

    // Muestra los datos informativos del sistema
    @FXML
    public void onButtonMostrarInfoSistemaClick() {
        AcercaDe acerdaDe = AcercaDe.obtenerInstancia(new Stage());
        acerdaDe.mostrar();
    }

    // Forzamos el refresco manual de la rejilla
    @FXML
    public void onButtonMostrarOperacioes() {
        tlbV_tablaViewPrincipal.refresh();
    }

    // Metodo complementario de verificacion de estado inicial
    @FXML
    protected void onHelloButtonClick() {
        texto.setText("Sistema MultiTask-UACM listo.");
    }

    // Abre la ventana de acerca de
    
    @FXML
    private void abrirAcercaDe() {
        AcercaDe acerca = AcercaDe.obtenerInstancia(new Stage());
        acerca.mostrar();
    }
    
    
}
