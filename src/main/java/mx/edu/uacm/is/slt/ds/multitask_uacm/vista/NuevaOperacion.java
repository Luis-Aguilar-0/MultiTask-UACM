package mx.edu.uacm.is.slt.ds.multitask_uacm.vista;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class NuevaOperacion {

    private final Stage PANTALLA_NUEVA_OPERACION;
    private static NuevaOperacion singleton;

    private NuevaOperacion(Stage pantalla) {
        PANTALLA_NUEVA_OPERACION = pantalla;
    }

    public void mostrar(){
        PANTALLA_NUEVA_OPERACION.show();
    }

    private void cargarVista(){
        try {
            String rutaFXML = "/mx/edu/uacm/is/slt/ds/multitask_uacm/fxml/nuevaOperacion.fxml";
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource(rutaFXML));

            if(fxmlLoader.getLocation() == null){
                System.err.println("No se encontro la ruta : " + rutaFXML);
                return;
            }

            Parent vistaRaiz = fxmlLoader.load();
            Scene escena = new Scene(vistaRaiz,900,600);
            PANTALLA_NUEVA_OPERACION.setScene(escena);
            PANTALLA_NUEVA_OPERACION.setTitle("Nueva Operacion");

            /*Logica para permitir abrir de nuevo la ventana*/
            PANTALLA_NUEVA_OPERACION.showingProperty().addListener((obs,odlValue,newValue) -> {
                if(!newValue){
                    singleton = null;
                }
            });

        } catch (Exception e) {
            System.err.println("Surgio un problema al abrir la ventana");
            e.printStackTrace();
        }
    }

    public static NuevaOperacion obtenerInstancia(Stage pantallaNuevaOperacion) {
        if(singleton == null){
            singleton = new NuevaOperacion(pantallaNuevaOperacion);
            singleton.cargarVista();
        }
        return  singleton;
    }



}
