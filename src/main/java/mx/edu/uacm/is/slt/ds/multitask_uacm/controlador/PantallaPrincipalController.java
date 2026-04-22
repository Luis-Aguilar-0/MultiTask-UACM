package mx.edu.uacm.is.slt.ds.multitask_uacm.controlador;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mx.edu.uacm.is.slt.ds.multitask_uacm.modelo.GestorOperaciones;
import mx.edu.uacm.is.slt.ds.multitask_uacm.modelo.Operacion;
import mx.edu.uacm.is.slt.ds.multitask_uacm.vista.VisorEditorOperacion;

import java.io.IOException;


/**
 * En esta clase se implemtan todas las interacciones que raliza el usuario con botones, campos de texto etc..
 *
 * */

public class PantallaPrincipalController {

    private GestorOperaciones gestor = new GestorOperaciones(); //instancia para el manejo de la lógica
    private static Operacion operacionSeleccionada;

   @FXML
    private Label texto;

   @FXML
   private Button botonVisorEditorOperacion;

   @FXML
   private Button buttonMostrarOperciones;

   @FXML
   private Button buttonMostrarInfoSistema;

   @FXML
   protected void onHelloButtonClick(){
       texto.setText("En proceso de desarrollo........!");

   }

    /***
     * Accion encargada de mostrar las operaciones registradas
     *
     */
   @FXML
    public void onButtonMostrarOperacioes(){

        mostrarOperciones();

   }

    /**
     * Metodo encargado de mostrar las operaciones creadas
     * de momento solo muestra la informacion crada con el metodo cargaOperacionesPrueba() mediate terminal
     */
    private void mostrarOperciones(){
        gestor.cargarOperacionesDePrueba();
        System.out.println(gestor.toString());
        seleccionarOperacion(); //Una ves mostradas las Operaciones se permite seleccionar una sola Opearcion
    }


    private void seleccionarOperacion(){

        System.out.println("Operacion seleccionda " );
        operacionSeleccionada = gestor.obtenerOperacion(0);

    }




    /***
     * Metodo encargado de cargra la interfaz grafica de VisorEditorOperacionVista
     */
   @FXML
    public void onButtonVistaEditorOperacion(){
       System.out.println("El boton fue procionado");
       abrirVentanaEditorOperaciones();

   }

    /**
     * Metoo para abrir el editor de Operacioens sin cerrar Stage principal
     *
     */
   private void abrirVentanaEditorOperaciones(){
       Stage stage = new Stage();
       VisorEditorOperacion visorEditorOperacion = VisorEditorOperacion.obtenerInstancia(stage);

       //pasamos la operacion seleccionada
       if(operacionSeleccionada != null){
           visorEditorOperacion.setOperacion(operacionSeleccionada);
       }

   }


}
