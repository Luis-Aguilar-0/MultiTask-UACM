package mx.edu.uacm.is.slt.ds.multitask_uacm.vista;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mx.edu.uacm.is.slt.ds.multitask_uacm.controlador.VisorEditorOperacionController;
import mx.edu.uacm.is.slt.ds.multitask_uacm.modelo.Operacion;

public class VisorEditorOperacion {

    private final Stage VISTA_EDITOR_OPERACION;
    private static VisorEditorOperacion singleton;
    private VisorEditorOperacionController controlador;
    private Operacion operacionPendiente;

    // Constructor basico para fijar la escena
    private VisorEditorOperacion(Stage vistaEditorOperacion) {
        VISTA_EDITOR_OPERACION = vistaEditorOperacion;
    }

    // Muestra la pantalla flotante del editor
    public void mostrar() {
        VISTA_EDITOR_OPERACION.show();
    }

    // Procesa el FXML del editor de operaciones
    private void cargaVista() {
        try {
            String fxmlRuta = "/mx/edu/uacm/is/slt/ds/multitask_uacm/fxml/EditordeOperaciones.fxml";
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxmlRuta));

            if (loader.getLocation() == null) {
                System.err.println("No se encontro la ruta: " + fxmlRuta);
                return;
            }

            Parent root = loader.load();
            this.controlador = loader.getController();

            // Enlazamos la operacion pendiente con el controlador recien cargado
            if (operacionPendiente != null && controlador != null) {
                controlador.setOperacionActual(operacionPendiente);
            }

            Scene escena = new Scene(root, 900, 600);
            VISTA_EDITOR_OPERACION.setScene(escena);
            VISTA_EDITOR_OPERACION.setTitle("Editor de Operacion");

            // Limpiamos la referencia estatica si la ventana es cerrada
            VISTA_EDITOR_OPERACION.showingProperty().addListener((obs, oldVal, newVal) -> {
                if (!newVal) {
                    singleton = null;
                }
            });

        } catch (Exception e) {
            System.err.println("Error al abrir la ventana del editor de operacion");
            e.printStackTrace();
        }
    }

    // Configura o actualiza la operacion sobre el controlador activo
    public void setOperacion(Operacion op) {
        this.operacionPendiente = op;
        if (this.controlador != null) {
            this.controlador.setOperacionActual(op);
        }
    }

    // Administra la instancia unica del editor de operaciones
    public static VisorEditorOperacion obtenerInstancia(Stage visorEditorOperacion) {
        if (singleton == null) {
            singleton = new VisorEditorOperacion(visorEditorOperacion);
            singleton.cargaVista();
        }
        return singleton;
    }
}
