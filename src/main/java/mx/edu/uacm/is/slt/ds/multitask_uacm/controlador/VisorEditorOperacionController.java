package mx.edu.uacm.is.slt.ds.multitask_uacm.controlador;


import mx.edu.uacm.is.slt.ds.multitask_uacm.modelo.Operacion;
import mx.edu.uacm.is.slt.ds.multitask_uacm.modelo.Tarea;


import java.util.ArrayList;
import java.util.List;



public class VisorEditorOperacionController {

    private Operacion operacionActual;

    /**
     * Cehecamos si la operacionAcutual no es nula
     * @return
     */
    private boolean isNulOperacion(){
        boolean retornEstado = false;
        if(operacionActual != null){
            retornEstado = true;
        }
        return retornEstado;
    }


    //recupero la operación seleccionda en el munu princial
    public void setOperacionActual(Operacion operacion){
        if(isNulOperacion()){
            this.operacionActual = operacion;
        }
    }


    public void modificarNombre(String nombre){
        if(isNulOperacion()){
            operacionActual.setNombre(nombre);
        }
    }

    //metodos aun en desarrollo
    public void abrirVisor(){}

    public void mostrarDatos(){}


    public void modificarTareas(){}

    public void modificarOrden(){}

    public void guardar(){}

    public void descartar(){}



}
