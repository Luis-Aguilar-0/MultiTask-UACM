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

    private VisorEditorOperacion(Stage vistaEditorOperacion) {
        VISTA_EDITOR_OPERACION = vistaEditorOperacion;
    }

    public void mostrar() {
        VISTA_EDITOR_OPERACION.show();
    }

    private void cargaVista() {
        try {
            String fxmlRuta = "/mx/edu/uacm/is/slt/ds/multitask_uacm/fxml/vistaEditorOperaciones.fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlRuta));
            
            if (loader.getLocation() == null) {
                System.err.println("No se encontro la ruta: " + fxmlRuta);
                return;
            }
            
            Parent root = loader.load();
            this.controlador = loader.getController();
            
            Scene escena = new Scene(root, 800, 550);
            VISTA_EDITOR_OPERACION.setScene(escena);
            VISTA_EDITOR_OPERACION.setTitle("Editor de Operaciones");
            VISTA_EDITOR_OPERACION.setResizable(false);
            
            VISTA_EDITOR_OPERACION.showingProperty().addListener((obs, oldValue, newValue) -> {
                if (!newValue) {
                    singleton = null;
                }
            });
            
        } catch (Exception e) {
            System.err.println("Surgio un problema al abrir la ventana");
            e.printStackTrace();
        }
    }

    public static VisorEditorOperacion obtenerInstancia(Stage visorEditorOperacion, Operacion operacion) {
        if (singleton == null) {
            singleton = new VisorEditorOperacion(visorEditorOperacion);
            singleton.cargaVista();
        }
        // Pasa la operacion al controlador
        if (singleton.controlador != null) {
            VisorEditorOperacionController.guardarReferenciaOperacion(operacion);
            singleton.controlador.initialize(); // Forzar recarga de datos
        }
        return singleton;
    }
}