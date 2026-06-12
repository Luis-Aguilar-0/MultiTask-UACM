package mx.edu.uacm.is.slt.ds.multitask_uacm.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import mx.edu.uacm.is.slt.ds.multitask_uacm.modelo.Estado;
import mx.edu.uacm.is.slt.ds.multitask_uacm.modelo.GestorOperaciones;
import mx.edu.uacm.is.slt.ds.multitask_uacm.modelo.Operacion;
import mx.edu.uacm.is.slt.ds.multitask_uacm.modelo.Tarea;

public class VisorDeTareasController implements Initializable {

    private Stage nuevaTarea = null;
    @FXML
    private Button btnNuevaTarea;
    @FXML
    private Button btn_cancelar;
    @FXML
    private Button btn_PausarOperacion;
    @FXML
    private Button btn_detenerOperacion;
    @FXML
    private Button btn_reanudarOéracion;
    @FXML
    private Button btn_bolver;

    @FXML
    private TextField txt_nombreOperacion;
    @FXML
    private TextField txt_estado;

    @FXML
    private TableView<Tarea> tableView;
    @FXML
    private TableColumn<Tarea, String> tlc_nombre;
    @FXML
    private TableColumn<Tarea, String> tlb_tipo;
    @FXML
    private TableColumn<Tarea, String> tlb_descripcion;
    @FXML
    private TableColumn<Tarea, String> tlb_dependencias;
    @FXML
    public TableColumn<Tarea, String> tlb_estado_tarea;
    @FXML
    private TableColumn<Tarea, String> tlb_acciones;

    private GestorOperaciones gestor;
    private Operacion operacionActual;
    private volatile boolean escuchandoProgreso;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gestor = GestorOperaciones.obtenerInstancia();

        // Bloqueamos los campos de texto de arriba para que solo sirvan de vista
        txt_nombreOperacion.setEditable(false);
        txt_estado.setEditable(false);

        // Mapeamos los datos basicos de las columnas
        tlc_nombre.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("nombre"));

        // Formateamos el tipo de tarea en dos lineas si es muy largo
        tlb_tipo.setCellFactory(param -> new TableCell<Tarea, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getIndex() >= getTableView().getItems().size()) {
                    setText(null);
                } else {
                    Tarea t = getTableView().getItems().get(getIndex());
                    String rawTipo = t.getTipoTarea() != null ? t.getTipoTarea() : "";
                    if (rawTipo.contains(" (")) {
                        String principal = rawTipo.substring(0, rawTipo.indexOf(" ("));
                        String detalle = rawTipo.substring(rawTipo.indexOf(" ("));
                        setText(principal + "\n" + detalle);
                    } else {
                        setText(rawTipo);
                    }
                }
            }
        });

        // Mostramos la descripcion de la tarea de forma simple
        tlb_descripcion.setCellFactory(param -> new TableCell<Tarea, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getIndex() >= getTableView().getItems().size()) {
                    setText(null);
                } else {
                    Tarea t = getTableView().getItems().get(getIndex());
                    setText(t.getDescripcion().isEmpty() ? "Sin descripcion" : t.getDescripcion());
                }
            }
        });

        // Separamos dependencias, pre y postcondiciones con saltos de linea
        tlb_dependencias.setCellFactory(param -> new TableCell<Tarea, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getIndex() >= getTableView().getItems().size()) {
                    setText(null);
                } else {
                    Tarea t = getTableView().getItems().get(getIndex());
                    // Corregido: cambiamos t.getDependencies() por t.getDependencias()
                    String dep = t.getDependencias() == null || t.getDependencias().isEmpty() ? "Sin dependencias" : String.join(", ", t.getDependencias());
                    String pre = t.getPrecondiciones().isEmpty() ? "Sin precondiciones" : String.join(", ", t.getPrecondiciones());
                    String post = t.getPostcondiciones().isEmpty() ? "Sin postcondiciones" : String.join(", ", t.getPostcondiciones());
                    setText("Dep: " + dep + "\nPre: " + pre + "\nPost: " + post);
                }
            }
        });

        // Dibujamos el boton estatico de estado usando el archivo css
        tlb_estado_tarea.setCellFactory(column -> new TableCell<Tarea, String>() {
            private final Button btnTareaEstado = new Button();

            {
                btnTareaEstado.setDisable(true);
                btnTareaEstado.getStyleClass().add("boton-estado-celda");
                setAlignment(Pos.CENTER);
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getIndex() >= getTableView().getItems().size()) {
                    setGraphic(null);
                } else {
                    Tarea tarea = getTableView().getItems().get(getIndex());
                    btnTareaEstado.setText((tarea.getEstado() != null ? tarea.getEstado() : "No ejecutada") + " (" + (int) tarea.getProgreso() + "%)");
                    setGraphic(btnTareaEstado);
                }
            }
        });

        // Creamos los botones de acciones de la tabla usando estilos css limpios
        tlb_acciones.setCellFactory(param -> new TableCell<Tarea, String>() {
            private final Button btnEditar = new Button("Editar");
            private final Button btnEliminar = new Button("Eliminar");
            private final HBox contenedor = new HBox();

            {
                contenedor.getStyleClass().add("contenedor-acciones-celda");
                btnEditar.getStyleClass().add("button");
                btnEliminar.getStyleClass().add("button");

                // Evento para el boton de editar la tarea seleccionada
                btnEditar.setOnAction(event -> {
                    if (operacionActual.getEstado() == Estado.EN_EJECUCION || operacionActual.getEstado() == Estado.PAUSADO) {
                        mostrarAlerta("No se puede editar", "La operacion esta corriendo o pausada. Detenla para modificar tareas.");
                        return;
                    }
                    if (operacionActual.getEstado() == Estado.FINALIZADA) {
                        mostrarAlerta("Operacion terminada", "Esta operacion ya finalizo exitosamente y no se puede modificar.");
                        return;
                    }

                    Tarea t = getTableView().getItems().get(getIndex());
                    System.out.println("Consola: Editando tarea -> " + t.getNombre());

                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/edu/uacm/is/slt/ds/multitask_uacm/fxml/NuevaTarea.fxml"));
                        Parent root = loader.load();
                        NuevaTareaController edicionControlador = loader.getController();
                        edicionControlador.setOperacion(operacionActual);

                        Stage stageEdicionFlotante = new Stage();
                        stageEdicionFlotante.setScene(new Scene(root));
                        stageEdicionFlotante.setTitle("Editar Tarea - " + t.getNombre());
                        stageEdicionFlotante.setOnHidden(e -> tableView.refresh());
                        stageEdicionFlotante.show();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });

                // Evento para eliminar la tarea de la lista
                btnEliminar.setOnAction(event -> {
                    if (operacionActual.getEstado() == Estado.EN_EJECUCION || operacionActual.getEstado() == Estado.PAUSADO) {
                        mostrarAlerta("No se puede eliminar", "La operacion esta activa. No puedes borrar tareas en este momento.");
                        return;
                    }
                    if (operacionActual.getEstado() == Estado.FINALIZADA) {
                        mostrarAlerta("Operacion terminada", "No se pueden quitar tareas de una operacion ya finalizada.");
                        return;
                    }
                    Tarea t = getTableView().getItems().get(getIndex());
                    operacionActual.getTareas().remove(t);
                    tableView.refresh();
                });
                contenedor.getChildren().addAll(btnEditar, btnEliminar);
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(contenedor);
                }
            }
        });

        // Cargamos la primera operacion disponible si existe
        if (!gestor.getOperaciones().isEmpty()) {
            operacionActual = gestor.obtenerOperacion(0);
            cargarDatosOperacion();
        }

        iniciarObservadorProgreso();
    }

    // Hilo de fondo encargado de refrescar la pantalla en tiempo real
    private void iniciarObservadorProgreso() {
        escuchandoProgreso = true;
        Thread hiloUI = new Thread(() -> {
            try {
                while (escuchandoProgreso) {
                    Thread.sleep(250);
                    Platform.runLater(() -> {
                        if (operacionActual != null) {
                            txt_estado.setText(estadoTexto(operacionActual.getEstado()));
                        }
                        tableView.refresh();
                    });
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        hiloUI.setDaemon(true);
        hiloUI.start();
    }

    public void setOperacion(Operacion op) {
        this.operacionActual = op;
        cargarDatosOperacion();
    }

    private void cargarDatosOperacion() {
        if (operacionActual == null) {
            return;
        }
        txt_nombreOperacion.setText(operacionActual.getNombre());
        txt_estado.setText(estadoTexto(operacionActual.getEstado()));
        ObservableList<Tarea> tareas = (ObservableList<Tarea>) operacionActual.getTareas();
        tableView.setItems(tareas);

        if (operacionActual.getEstado() == Estado.NO_EJECUTADA || operacionActual.getEstado() == Estado.DETENIDA) {
            btn_PausarOperacion.setText("Iniciar operacion");
        } else {
            btn_PausarOperacion.setText("Pausar operacion");
        }
    }

    private String estadoTexto(Estado estado) {
        if (estado == null) {
            return "";
        }
        switch (estado) {
            case EN_EJECUCION:
                return "En ejecución";
            case PAUSADO:
                return "Pausada";
            case DETENIDA:
                return "Detenida";
            case NO_EJECUTADA:
                return "No ejecutada";
            case FINALIZADA:
                return "Finalizada";
            default:
                return estado.toString();
        }
    }

    // Accion del boton de Iniciar o Pausar la operacion
    @FXML
    private void pausarOperacion(ActionEvent event) {
        if (operacionActual == null) {
            mostrarAlerta("Error", "No hay operacion para trabajar.");
            return;
        }
        if (operacionActual.getTareas().isEmpty()) {
            mostrarAlerta("Sin tareas", "No puedes iniciar la operacion porque no tiene tareas agregadas.");
            return;
        }

        Estado estadoActual = operacionActual.getEstado();

        if (estadoActual == Estado.FINALIZADA) {
            mostrarAlerta("Proceso terminado", "Esta operacion ya concluyo correctamente. No se puede pausar o iniciar.");
            return;
        }
        if (estadoActual == Estado.PAUSADO) {
            mostrarAlerta("Operacion pausada", "Usa el boton Reanudar para continuar con el avance.");
            return;
        }

        if (estadoActual == Estado.NO_EJECUTADA || estadoActual == Estado.DETENIDA) {
            operacionActual.ejecutar();
            btn_PausarOperacion.setText("Pausar Operacion");
            mostrarInfo("Proceso Iniciado", "Las tareas han comenzado a ejecutarse de forma secuencial.");
        } else if (estadoActual == Estado.EN_EJECUCION) {
            operacionActual.pausar();
            btn_PausarOperacion.setText("Iniciar operacion");
            mostrarInfo("Proceso Pausado", "Se detuvo el avance temporalmente.");
        }
    }

    // Accion del boton de Detener por completo la ejecucion
    @FXML
    private void detenerOperacion(ActionEvent event) {
        if (operacionActual == null) {
            mostrarAlerta("Error", "No hay operacion seleccionada.");
            return;
        }
        if (operacionActual.getTareas().isEmpty()) {
            mostrarAlerta("Sin tareas", "No se puede detener una operacion vacia.");
            return;
        }

        Estado estadoActual = operacionActual.getEstado();
        if (estadoActual == Estado.NO_EJECUTADA || estadoActual == Estado.DETENIDA) {
            mostrarAlerta("Proceso inactivo", "La operacion no esta corriendo. No se puede detener.");
            return;
        }
        if (estadoActual == Estado.FINALIZADA) {
            mostrarAlerta("Proceso terminado", "No puedes detener una operacion que ya finalizo.");
            return;
        }

        operacionActual.detener();
        btn_PausarOperacion.setText("Iniciar operacion");
        mostrarInfo("Proceso Detenido", "La operacion se detuvo y los progresos regresaron a cero.");
    }

    // Accion del boton de Reanudar la pausa
    @FXML
    private void reanudarOperacion(ActionEvent event) {
        if (operacionActual == null) {
            mostrarAlerta("Error", "No hay operacion cargada.");
            return;
        }
        if (operacionActual.getTareas().isEmpty()) {
            mostrarAlerta("Sin tareas", "No hay tareas para reanudar.");
            return;
        }

        Estado estadoActual = operacionActual.getEstado();
        if (estadoActual == Estado.FINALIZADA) {
            mostrarAlerta("Proceso terminado", "La operacion ya termino. No hay nada que reanudar.");
            return;
        }
        if (estadoActual != Estado.PAUSADO) {
            mostrarAlerta("No esta pausada", "Solo se puede reanudar si el estado actual es Pausada.");
            return;
        }

        operacionActual.reanudar();
        btn_PausarOperacion.setText("Pausar Operacion");
        mostrarInfo("Proceso Reanudado", "Las tareas continuan su ejecucion desde donde se quedaron.");
    }

    @FXML
    private void volver(ActionEvent event) {
        escuchandoProgreso = false;
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void cancelar(ActionEvent event) {
        escuchandoProgreso = false;
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    // Abre la ventana para agregar una nueva tarea
    @FXML
    private void abrirNuevaTarea(ActionEvent event) {
        if (operacionActual.getEstado() == Estado.EN_EJECUCION || operacionActual.getEstado() == Estado.PAUSADO) {
            mostrarAlerta("Proceso activo", "No puedes agregar tareas mientras la operacion se esta ejecutando.");
            return;
        }
        if (operacionActual.getEstado() == Estado.FINALIZADA) {
            mostrarAlerta("Operacion terminada", "No se pueden añadir mas tareas a una operacion completada.");
            return;
        }

        if (nuevaTarea != null && nuevaTarea.isShowing()) {
            nuevaTarea.toFront();
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/edu/uacm/is/slt/ds/multitask_uacm/fxml/NuevaTarea.fxml"));
            Parent root = loader.load();

            NuevaTareaController controlador = loader.getController();
            controlador.setOperacion(operacionActual);

            nuevaTarea = new Stage();
            nuevaTarea.setScene(new Scene(root));
            nuevaTarea.setTitle("Nueva Tarea");
            nuevaTarea.setOnHidden(e -> tableView.refresh());
            nuevaTarea.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
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
