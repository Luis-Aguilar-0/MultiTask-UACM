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

    // Constructor privado para que nadie use el operador new fuera de aqui
    private VisorEditorTareas() {
    }

    // Reutiliza la ventana si ya existe o crea una nueva desde cero
    public static VisorEditorTareas obtenerInstancia(Stage pantalla, Operacion operacion) {
        if (singleton == null) {
            singleton = new VisorEditorTareas();
            singleton.stage = pantalla;
            singleton.cargarVista(operacion);

            // Si el usuario cierra la ventana, liberamos la instancia para el recolector de basura
            singleton.stage.showingProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue) {
                    singleton = null;
                }
            });
        } else {
            // Si ya estaba abierta, solo actualiza los datos y la manda al frente
            singleton.cargarVista(operacion);
            singleton.stage.toFront();
        }
        return singleton;
    }

    // Despliega la ventana en la interfaz grafica
    public void mostrar() {
        if (stage != null) {
            stage.show();
        }
    }

    // Carga el archivo de diseño FXML y le inyecta el controlador correspondiente
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

            // Pasamos los datos de la operacion al controlador de la pantalla
            VisorDeTareasController controlador = fxmlLoader.getController();
            if (operacion != null && controlador != null) {
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
