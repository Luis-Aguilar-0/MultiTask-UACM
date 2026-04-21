package mx.edu.uacm.is.slt.ds.multitask_uacm.controlador;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) {
        // Pantalla simple de inicio
        Label texto = new Label("MultiTask-UACM");

        StackPane raiz = new StackPane();
        raiz.getChildren().add(texto);

        Scene scene = new Scene(raiz, 900, 600);

        stage.setTitle("MultiTask-UACM");
        stage.setScene(scene);
        stage.show();
    }
}