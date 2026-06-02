package mx.edu.uacm.is.slt.ds.multitask_uacm.controlador;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import mx.edu.uacm.is.slt.ds.multitask_uacm.modelo.Operacion;
import mx.edu.uacm.is.slt.ds.multitask_uacm.modelo.Tarea;

public class VisorEditorOperacionController {

    private Operacion operacionActual;
    private static Operacion operacionPuente;


    @FXML private TableView<Tarea> tablaTareasInternas;
    @FXML private TableColumn<Tarea, String> colNombre;
    @FXML private TableColumn<Tarea, String> colTipoDesc;
    @FXML private TableColumn<Tarea, String> colEstado;
    @FXML private TableColumn<Tarea, Double> colProgreso;


    @FXML private TextField txtNombreOperacionEdicion;
    @FXML private ComboBox<String> comboEstadoOperacion;
    @FXML private ListView<Tarea> listaTareasEdicion;

    /**
     * Guarda la referencia de la operación seleccionada en la Pantalla Principal
     * antes de levantar la escena (Patrón de paso de parámetros).
     */
    public static void guardarReferenciaOperacion(Operacion operacion) {
        operacionPuente = operacion;
    }

    @FXML
    public void initialize() {

        if (colNombre != null) {
            colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            colTipoDesc.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
            colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
            colProgreso.setCellValueFactory(new PropertyValueFactory<>("progreso"));
        }


        if (comboEstadoOperacion != null) {
            comboEstadoOperacion.getItems().clear();
            comboEstadoOperacion.getItems().addAll("NO_EJECUTADA", "EN_EJECUCION", "PAUSADA", "DETENIDA", "FINALIZADA");
        }


        if (operacionPuente != null) {
            this.operacionActual = operacionPuente;
            mostrarDatos();
        }
        

        Thread refrescadorGrafico = new Thread(() -> {
            try {
                while (operacionActual != null) {
                    Thread.sleep(300);
                    javafx.application.Platform.runLater(() -> {
                        if (tablaTareasInternas != null) {
                            tablaTareasInternas.refresh();
                        }
                    });
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        refrescadorGrafico.setDaemon(true);
        refrescadorGrafico.start();
    }

    /**
     * Valida la existencia segura del modelo en memoria.
     */
    private boolean isNulOperacion() {
        return operacionActual != null;
    }

    /**
     * Recupera externamente la operación si la arquitectura lo solicita.
     */
    public void setOperacionActual(Operacion operacion) {
        this.operacionActual = operacion;
        mostrarDatos();
    }

    /**
     * Carga y distribuye los datos en los componentes visuales dependiendo de la pantalla abierta.
     */
    @FXML
    public void mostrarDatos() {
        if (!isNulOperacion()) return;
        
        // Carga para la ventana de ejecución de procesos
        if (tablaTareasInternas != null) {
            ObservableList<Tarea> listaTareasObservables = FXCollections.observableArrayList(operacionActual.getTareas());
            tablaTareasInternas.setItems(listaTareasObservables);
        }
        
        // Carga para la ventana de edición y diseño
        if (txtNombreOperacionEdicion != null) {
            txtNombreOperacionEdicion.setText(operacionActual.getNombre());
            
            if (operacionActual.getEstado() != null) {
                comboEstadoOperacion.setValue(operacionActual.getEstado().toString());
            } else {
                comboEstadoOperacion.getSelectionModel().selectFirst();
            }
            
            if (listaTareasEdicion != null && operacionActual.getTareas() != null) {
                ObservableList<Tarea> tareasEdicion = FXCollections.observableArrayList(operacionActual.getTareas());
                listaTareasEdicion.setItems(tareasEdicion);
            }
        }
    }

    /**
     * Guarda los cambios del Editor en el objeto del Modelo (CU-04).
     */
    @FXML
    public void guardar() {
        if (!isNulOperacion()) return;
        
        try {
            if (txtNombreOperacionEdicion != null) {
                String nuevoNombre = txtNombreOperacionEdicion.getText().trim();
                if (nuevoNombre.isEmpty()) {
                    System.out.println("El nombre de la operación no puede estar vacío.");
                    return;
                }
                
                // Traspaso de datos bajo patrón MVC
                operacionActual.setNombre(nuevoNombre);
                
                if (comboEstadoOperacion.getValue() != null) {
                    String estadoStr = comboEstadoOperacion.getValue().toUpperCase().trim();
                    operacionActual.setEstado(mx.edu.uacm.is.slt.ds.multitask_uacm.modelo.Estado.valueOf(estadoStr));
                }
                
                System.out.println("Operación guardada en el Gestor de forma reactiva: " + operacionActual.getNombre());
                descartar(); // Cierre automático de ventana
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Error de conversión en el Enum de Estado: " + e.getMessage());
        }
    }

    /**
     * Cierra la ventana activa regresando fluidamente a la pantalla principal.
     */
    @FXML
    public void descartar() {
        Stage stage = null;
        if (txtNombreOperacionEdicion != null && txtNombreOperacionEdicion.getScene() != null) {
            stage = (Stage) txtNombreOperacionEdicion.getScene().getWindow();
        } else if (tablaTareasInternas != null && tablaTareasInternas.getScene() != null) {
            stage = (Stage) tablaTareasInternas.getScene().getWindow();
        }
        
        if (stage != null) {
            stage.close();
        }
    }

    // ─── COMPORTAMIENTOS CONCURRENTES (Fieles a Interfaz Ejecutable) ─────────
    @FXML
    public void pausar() {
        if (isNulOperacion()) {
            operacionActual.pausar();
            if (tablaTareasInternas != null) tablaTareasInternas.refresh();
        }
    }

    @FXML
    public void reanudar() {
        if (isNulOperacion()) {
            operacionActual.reanudar();
            if (tablaTareasInternas != null) tablaTareasInternas.refresh();
        }
    }

    @FXML
    public void detener() {
        if (isNulOperacion()) {
            operacionActual.detener();
            if (tablaTareasInternas != null) tablaTareasInternas.refresh();
        }
    }

    // ─── CONTROL DE ASOCIACIÓN Y ORDEN DE TAREAS (Casos de Uso ERS) ──────────
    @FXML
    public void asociarNuevaTareaAOperacion() {
        if (!isNulOperacion()) return;
        
        // Inyecta una tarea en el flujo de la operación (CU-04)
        Tarea nuevaTarea = new Tarea("Nueva Tarea Pasos", "Descripción por definir");
        operacionActual.agregarTarea(nuevaTarea);
        
        mostrarDatos(); // Refresco reactivo instantáneo
        System.out.println("Nueva tarea asociada al proceso actual.");
    }

    @FXML
    public void moverTareaArribaEdicion() {
        if (listaTareasEdicion == null) return;
        int indice = listaTareasEdicion.getSelectionModel().getSelectedIndex();
        if (indice > 0 && isNulOperacion()) {
            operacionActual.moverTareaArriba(indice);
            mostrarDatos();
            listaTareasEdicion.getSelectionModel().select(indice - 1);
        }
    }

    @FXML
    public void moverTareaAbajoEdicion() {
        if (listaTareasEdicion == null) return;
        int indice = listaTareasEdicion.getSelectionModel().getSelectedIndex();
        if (isNulOperacion() && indice >= 0 && indice < operacionActual.getTareas().size() - 1) {
            operacionActual.moverTareaAbajo(indice);
            mostrarDatos();
            listaTareasEdicion.getSelectionModel().select(indice + 1);
        }
    }

    @FXML
    public void agregarNuevaTarea() {
        if (!isNulOperacion()) return;
        
        // Flujo alterno (CU-01): Levantar formulario NuevaTarea.fxml
        try {
            javafx.fxml.FXMLLoader cargador = new javafx.fxml.FXMLLoader(
                getClass().getResource("/mx/edu/uacm/is/slt/ds/multitask_uacm/fxml/NuevaTarea.fxml")
            );
            javafx.scene.Parent raiz = cargador.load();
            
            Stage escenarioFlotante = new Stage();
            escenarioFlotante.setTitle("Crear Nueva Tarea - MultiTask-UACM");
            escenarioFlotante.initModality(javafx.stage.Modality.WINDOW_MODAL);
            escenarioFlotante.initOwner(tablaTareasInternas.getScene().getWindow());
            escenarioFlotante.setScene(new javafx.scene.Scene(raiz));
            escenarioFlotante.showAndWait();
            
            mostrarDatos();
            
        } catch (java.io.IOException e) {
            System.err.println("Error al abrir ventana interactiva de tareas: " + e.getMessage());
        }
    }
    
    // Métodos estructurales vacíos solicitados por el modelo de diseño viejo
    public void abrirVisor() {}
    public void modificarNombre(String nombre) {}
    public void modificarTareas() {}
    public void modificarOrden() {}
}