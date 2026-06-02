package mx.edu.uacm.is.slt.ds.multitask_uacm.vista;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mx.edu.uacm.is.slt.ds.multitask_uacm.controlador.VisorEditorOperacionController;
import mx.edu.uacm.is.slt.ds.multitask_uacm.modelo.Operacion;

public class VisorEditorOperacion {

    private final Stage VISTA_EDITOR_OPERACION;
    private static VisorEditorOperacion singleton;
    private VisorEditorOperacionController controlador; //usado para recuperar la Operacion seleccionada en la pantalla principal

    private VisorEditorOperacion(Stage vistaEditorOperacion) {VISTA_EDITOR_OPERACION = vistaEditorOperacion; }

    public void mostrar(){
        VISTA_EDITOR_OPERACION.show();
    }

    private void cargaVista(){
        try {

            String fxmlRuta = "/mx/edu/uacm/is/slt/ds/multitask_uacm/fxml/vistaEditorOperaciones.fxml";

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxmlRuta));

            if (loader.getLocation() == null) {
                System.err.println("No se encontro la ruta: " + fxmlRuta);
                return;
            }

            Parent root = loader.load();
            this.controlador = loader.getController();

            Scene escena = new Scene(root,900,600);
            VISTA_EDITOR_OPERACION.setScene(escena);
            VISTA_EDITOR_OPERACION.setTitle("Editor operacion");


            /**
             * Si se cierra la ventana, por el uso del patron singleton no se permite
             * crear una nueva ventana, en le caso de que se necesite abrir denuevo la ventana
             * detectamos si se cerro
             */
            VISTA_EDITOR_OPERACION.showingProperty().addListener((obs,oldValue,newValue)->{
                if(!newValue){ // si se cerro la ventana
                    singleton = null;
                }
            } );




        }catch (Exception e){
            System.err.println("Surgio un problema al abrir la ventana");

            e.printStackTrace();

        }
    }

    /**
     * Metodo para recupera la operacion seleccionada en la pantalla principal
     * @param op
     */
    public void setOperacion(Operacion op){
        if(this.controlador != null){
            //this.controlador.setOperacionActual(op);
        }
    }

    public static VisorEditorOperacion obtenerInstancia(Stage visorEditorOperacion){
        if(singleton == null){
            singleton = new VisorEditorOperacion(visorEditorOperacion);
            singleton.cargaVista();
        }
        return  singleton;
    }

}
