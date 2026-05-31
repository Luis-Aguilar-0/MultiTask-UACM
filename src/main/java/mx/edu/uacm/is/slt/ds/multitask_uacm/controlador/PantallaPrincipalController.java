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
import mx.edu.uacm.is.slt.ds.multitask_uacm.vista.AcercaDe;
import mx.edu.uacm.is.slt.ds.multitask_uacm.vista.NuevaOperacion;
import mx.edu.uacm.is.slt.ds.multitask_uacm.vista.VisorEditorOperacion;

import java.io.IOException;
import mx.edu.uacm.is.slt.ds.multitask_uacm.vista.PantallaPrincipal;


/**
 * En esta clase se implemtan todas las interacciones que realiza el usuario con botones, campos de texto etc..
 * */

public class PantallaPrincipalController {

    private GestorOperaciones gestor = new GestorOperaciones(); //instancia para el manejo de la lógica
    private Operacion operacionSeleccionada; //hola

   @FXML
    private Label texto;

   @FXML
   private Button botonVisorEditorOperacion;

   @FXML
   private Button buttonMostrarOperciones;

   @FXML
   private Button buttonMostrarInfoSistema;

   @FXML
   private Button btn_nuevaOperacion;

   @FXML
   protected void onHelloButtonClick(){
       texto.setText("En proceso de desarrollo........!");

   }

    /**
     * Accion encargada de mostrar la informacion del sistema
     */
    @FXML
    public void onButtonMostrarInfoSistemaClick(){

        AcercaDe acerdaDe = AcercaDe.obtenerInstancia(new Stage());
        acerdaDe.mostrar();
        System.out.println("El boton fue procionado");
        //texto.setText("Función aún no disponible..... :(");

    }

    /**
     * Boton encargado de mostrar la pantalla "Nueva Opearcion"
     * */
     @FXML
     public void onButtonNuevaOperacion(){
         NuevaOperacion nuevaOperacion = NuevaOperacion.obtenerInstancia(new Stage());
         nuevaOperacion.mostrar();
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
       VisorEditorOperacion visorEditorOperacion = VisorEditorOperacion.obtenerInstancia(new Stage());
       visorEditorOperacion.mostrar();
   }

   @FXML
    private void abrirAcercaDe(){
        Stage stage = new Stage();
        AcercaDe acerca = AcercaDe.obtenerInstancia(stage);
        acerca.mostrar();
    }



}
