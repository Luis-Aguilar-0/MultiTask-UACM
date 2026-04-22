package mx.edu.uacm.is.slt.ds.multitask_uacm.vista;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Clase encargada unicamente para la creacion de la interfaz grafica
 */
public class PantallaPrincipal{

    private final Stage PANTALLA_PRINCIPAL;
    private static PantallaPrincipal singleton;

    private PantallaPrincipal(Stage pantallaPrincipal){
        PANTALLA_PRINCIPAL = pantallaPrincipal;
    }

    public void mostrar(){
        PANTALLA_PRINCIPAL.show();
    }

    private void cargaVista(){

        try {

        String rutaFXML = "/mx/edu/uacm/is/slt/ds/multitask_uacm/fxml/hello-view.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(rutaFXML));

        if(fxmlLoader.getLocation() == null){
            System.err.println("No se encontro la ruta:  " + rutaFXML);
            return;
        }
            Parent vistaRaiz = fxmlLoader.load();
            Scene escena = new Scene(vistaRaiz, 900, 600);
            PANTALLA_PRINCIPAL.setScene(escena);
        }catch (IOException e){
            System.err.println("No fue posible mostrar la ventana");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static PantallaPrincipal obtenerInstancia(Stage pantallaPrincipal){
        if(singleton == null){
            singleton = new PantallaPrincipal(pantallaPrincipal);
            singleton.cargaVista();
        }
        return singleton;
    }



}
