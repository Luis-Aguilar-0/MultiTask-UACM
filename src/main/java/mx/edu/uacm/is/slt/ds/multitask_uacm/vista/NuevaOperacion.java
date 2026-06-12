package mx.edu.uacm.is.slt.ds.multitask_uacm.vista;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class NuevaOperacion {

    private final Stage PANTALLA_NUEVA_OPERACION;
    private static NuevaOperacion singleton;

    // Constructor para fijar la escena de creacion
    private NuevaOperacion(Stage pantalla) {
        PANTALLA_NUEVA_OPERACION = pantalla;
    }

    // Despliega la ventana flotante de altas
    public void mostrar() {
        PANTALLA_NUEVA_OPERACION.show();
    }

    // Lee el archivo de diseño fxml de altas de operacion
    private void cargarVista() {
        try {
            String rutaFXML = "/mx/edu/uacm/is/slt/ds/multitask_uacm/fxml/nuevaOperacion.fxml";
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource(rutaFXML));

            if (fxmlLoader.getLocation() == null) {
                System.err.println("No se encontro la ruta : " + rutaFXML);
                return;
            }

            Parent vistaRaiz = fxmlLoader.load();
            Scene escena = new Scene(vistaRaiz, 900, 600);
            PANTALLA_NUEVA_OPERACION.setScene(escena);
            PANTALLA_NUEVA_OPERACION.setTitle("Nueva Operacion");

            // Escuchamos el cierre para restaurar la referencia estatica
            PANTALLA_NUEVA_OPERACION.showingProperty().addListener((obs, odlValue, newValue) -> {
                if (!newValue) {
                    singleton = null;
                }
            });

            // Si el controlador coincide, le inyectamos la ventana para poder cerrarla despues
            Object ctrl = fxmlLoader.getController();
            if (ctrl instanceof mx.edu.uacm.is.slt.ds.multitask_uacm.controlador.NuevaOperacion) {
                ((mx.edu.uacm.is.slt.ds.multitask_uacm.controlador.NuevaOperacion) ctrl).setStage(PANTALLA_NUEVA_OPERACION);
            }

        } catch (Exception e) {
            System.err.println("Surgio un problema al abrir la ventana");
            e.printStackTrace();
        }
    }

    // Singleton encargado del control de la ventana de altas
    public static NuevaOperacion obtenerInstancia(Stage pantallaNuevaOperacion) {
        if (singleton == null) {
            singleton = new NuevaOperacion(pantallaNuevaOperacion);
            singleton.cargarVista();
        }
        return singleton;
    }
}
