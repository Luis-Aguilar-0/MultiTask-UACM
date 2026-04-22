package mx.edu.uacm.is.slt.ds.multitask_uacm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mx.edu.uacm.is.slt.ds.multitask_uacm.vista.PantallaPrincipal;


public class Main extends Application {

    @Override
    public void start(Stage stagePrincipal){

    PantallaPrincipal pantallaPrincipal = PantallaPrincipal.obtenerInstancia(stagePrincipal);
    pantallaPrincipal.mostrar();

    }

    public static void main(String[] args){
        launch(args);
    }

}
