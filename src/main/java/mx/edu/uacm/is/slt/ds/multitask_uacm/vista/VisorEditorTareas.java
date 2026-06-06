package mx.edu.uacm.is.slt.ds.multitask_uacm.vista;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mx.edu.uacm.is.slt.ds.multitask_uacm.controlador.VisorDeTareasController;
import mx.edu.uacm.is.slt.ds.multitask_uacm.modelo.Operacion;

public class VisorEditorTareas {

    private Stage stage;
    private static VisorEditorTareas singleton;

    private VisorEditorTareas() {}

    public static VisorEditorTareas obtenerInstancia(Stage pantalla, Operacion operacion) {
        if (singleton == null) {
            singleton = new VisorEditorTareas();
            singleton.stage = pantalla;
            singleton.cargarVista(operacion);

            // Agregamos el listener para destruir el singleton cuando el usuario cierre la ventana
            singleton.stage.showingProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue) {
                    singleton = null;
                }
            });
        } else {
            // Si la ventana ya está abierta, evitamos crear una nueva y la traemos al frente
            singleton.stage.toFront();
        }
        return singleton;
    }

    public void mostrar() {
        if (stage != null) {
            stage.show();
        }
    }

    private void cargarVista(Operacion operacion) {
        try {
            String rutaFXML = "/mx/edu/uacm/is/slt/ds/multitask_uacm/fxml/VisorDeTareas.fxml";
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource(rutaFXML));

            if (fxmlLoader.getLocation() == null) {
                System.err.println("NO se encontro la ruta: " + rutaFXML);
                return;
            }

            Parent vistaRaiz = fxmlLoader.load();


            VisorDeTareasController controlador = fxmlLoader.getController();
            if (operacion != null) {
                controlador.setOperacion(operacion);
            }

            Scene escena = new Scene(vistaRaiz);
            stage.setScene(escena);
            stage.setTitle("Visor de Tareas");

        } catch (Exception e) {
            System.err.println("Surgio un problema al cargar la ventana");
            e.printStackTrace();
        }
    }
}