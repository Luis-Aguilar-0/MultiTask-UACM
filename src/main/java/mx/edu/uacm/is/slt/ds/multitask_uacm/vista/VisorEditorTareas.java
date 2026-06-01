package mx.edu.uacm.is.slt.ds.multitask_uacm.vista;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class VisorEditorTareas {

    private final Stage PANTALLA_VISOR_EDITOR_TAREAS;
    private static VisorEditorTareas singleton;

    private VisorEditorTareas(Stage pantalla){
        PANTALLA_VISOR_EDITOR_TAREAS = pantalla;
    }

    public void mostrar(){ PANTALLA_VISOR_EDITOR_TAREAS.show();}

    private void cargarVista(){
        try {
            String rutaFXML ="/mx/edu/uacm/is/slt/ds/multitask_uacm/fxml/VisorDeTareas.fxml";
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource(rutaFXML));

            if(fxmlLoader.getLocation() == null){
                System.err.println("NO se encontro la ruta: " + rutaFXML);
                return;
            }

            Parent vistaRaiz = fxmlLoader.load();
            Scene ecena = new Scene(vistaRaiz);
            PANTALLA_VISOR_EDITOR_TAREAS.setScene(ecena);
            PANTALLA_VISOR_EDITOR_TAREAS.setTitle("Visor de Tareas");


            PANTALLA_VISOR_EDITOR_TAREAS.showingProperty().addListener((observable, oldValue, newValue) -> {
                if(!newValue){
                    singleton = null;
                }
            });

        } catch (Exception e) {
            System.err.println("Surgio un problema al cargar la ventana");
            e.printStackTrace();
        }
    }

    public static VisorEditorTareas obtenerInstancia(Stage pantalla){
        if(singleton == null){
            singleton = new VisorEditorTareas(pantalla);
            singleton.cargarVista();
        }
        return singleton;
    }
}
