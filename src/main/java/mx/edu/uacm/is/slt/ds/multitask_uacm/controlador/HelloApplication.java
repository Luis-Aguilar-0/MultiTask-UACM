package mx.edu.uacm.is.slt.ds.multitask_uacm.controlador;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        URL fxmlhello_view = getClass().getResource("/mx/edu/uacm/is/slt/ds/multitask_uacm/fxml/hello-view.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlhello_view);
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("MultiTask");
        stage.setScene(scene);
        stage.show();
    }
}
