package mx.edu.uacm.is.slt.ds.multitask_uacm.vista;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PantallaPrincipal {

    private final Stage PANTALLA_PRINCIPAL;
    private static PantallaPrincipal singleton;

    // Recibe el stage principal que levanta el metodo start de la app
    private PantallaPrincipal(Stage pantallaPrincipal) {
        PANTALLA_PRINCIPAL = pantallaPrincipal;
    }

    // Hace visible el menu principal del sistema
    public void mostrar() {
        PANTALLA_PRINCIPAL.show();
    }

    // Inicializa el archivo de vista raiz hello-view
    private void cargaVista() {
        try {
            String rutaFXML = "/mx/edu/uacm/is/slt/ds/multitask_uacm/fxml/hello-view.fxml";
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource(rutaFXML));

            if (fxmlLoader.getLocation() == null) {
                System.err.println("No se encontro la ruta:  " + rutaFXML);
                return;
            }

            Parent vistaRaiz = fxmlLoader.load();
            Scene escena = new Scene(vistaRaiz, 900, 600);
            PANTALLA_PRINCIPAL.setScene(escena);
            PANTALLA_PRINCIPAL.setTitle("Multitask UACM");

        } catch (IOException e) {
            System.err.println("No fue posible mostrar la ventana");
            e.printStackTrace();
        }
    }

    // Metodo de acceso global para controlar la escena principal de JavaFX
    public static PantallaPrincipal obtenerInstancia(Stage pantallaPrincipal) {
        if (singleton == null) {
            singleton = new PantallaPrincipal(pantallaPrincipal);
            singleton.cargaVista();
        }
        return singleton;
    }
}
