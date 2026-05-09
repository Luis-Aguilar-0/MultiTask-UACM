package mx.edu.uacm.is.slt.ds.multitask_uacm.vista;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mx.edu.uacm.is.slt.ds.multitask_uacm.controlador.AcercaDeController;



/***
 * Clase encargada de mostrar la infromacion del sistema como:
 * version actual del sistema
 * información de los desarrolladores
 */
public class AcercaDe {


    private final Stage PANTALLA_ACERCA_DE;
    private static AcercaDe singleton;
    

    private AcercaDe(Stage pantalla){
        PANTALLA_ACERCA_DE = pantalla;
    }
    
    public void mostrar(){
        PANTALLA_ACERCA_DE.show();
    }

    private void cargarVista(){
        try {
            String rutaFXML = "/mx/edu/uacm/is/slt/ds/multitask_uacm/fxml/acerca_de.fxml";
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource(rutaFXML));

            if(fxmlLoader.getLocation() == null){
                System.err.println("No se encontro la ruta : " + rutaFXML);
                return;
            }

            Parent vistaRaiz = fxmlLoader.load();
            Scene escena = new Scene(vistaRaiz,900,600);
            PANTALLA_ACERCA_DE.setScene(escena);
            PANTALLA_ACERCA_DE.setTitle("Acerca de.");

            /*Logica para permitir abrir de nuevo la ventana*/
            PANTALLA_ACERCA_DE.showingProperty().addListener((obs,odlValue,newValue) -> {
                if(!newValue){
                    singleton = null;
                }
            });

        } catch (Exception e) {
            System.err.println("Surgio un problema al abrir la ventana");
            e.printStackTrace();
        }
    }

    public static AcercaDe obtenerInstancia(Stage pantallaAcerdaDe){
        if(singleton == null){
            singleton = new AcercaDe(pantallaAcerdaDe);
            singleton.cargarVista();
        }
        return singleton;
    }


}
