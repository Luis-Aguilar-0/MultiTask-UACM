package mx.edu.uacm.is.slt.ds.multitask_uacm.modelo;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class GestorOperaciones {

    private static GestorOperaciones singleton = null;
    private ObservableList<Operacion> operaciones; // se usa una ObservableList para hacer uso del patron Observador

    private GestorOperaciones() {
        // Lista vacía al iniciar
        this.operaciones = FXCollections.observableArrayList();

    }

    public static GestorOperaciones obtenerInstancia() {
        if(singleton == null){
            singleton = new GestorOperaciones();
        }
        return singleton;
    }

    public List<Operacion> getOperaciones() {
        return operaciones;
    }

    public void agregarOperacion(Operacion operacion) {
        // Solo agrega si existe
        /*
        Por defecto solo se pueden crear cuatro operaciones
        * */
        if(operaciones.size() <= 4){
            if (operacion != null) {
                operaciones.add(operacion);
            }
        }else{
            System.out.println("No es posible agregar mas operaciones");
        }

    }

    public void eliminarOperacion(int indice) {
        // Borra si el índice existe
        if (indice >= 0 && indice < operaciones.size()) {
            operaciones.remove(indice);
        }
    }

    public Operacion obtenerOperacion(int indice) {
        // Regresa la operación si está dentro del rango
        if (indice >= 0 && indice < operaciones.size()) {
            return operaciones.get(indice);
        }
        return null;
    }

    public void cargarOperacionesDePrueba() {
        // Operación sencilla para mostrar datos
        Operacion operacion = new Operacion("Operacion de prueba", "Operacion simple para mostrar la estructura inicial del sistema");

        Tarea tarea1 = new Tarea("Tarea inicial 1", "Primera tarea de ejemplo");
        Tarea tarea2 = new Tarea("Tarea inicial 2", "Segunda tarea de ejemplo");
        Tarea tarea3 = new Tarea("Tarea final", "Tarea que representa el cierre de la operación");

        tarea1.agregarPrecondicion("Dato de entrada 1");
        tarea1.agregarPostcondicion("Resultado parcial 1");

        tarea2.agregarPrecondicion("Dato de entrada 2");
        tarea2.agregarPostcondicion("Resultado parcial 2");

        tarea3.agregarPrecondicion("Resultado parcial 1");
        tarea3.agregarPrecondicion("Resultado parcial 2");
        tarea3.agregarPostcondicion("Resultado final");

        operacion.agregarTarea(tarea1);
        operacion.agregarTarea(tarea2);
        operacion.agregarTarea(tarea3);
        operacion.setEstado(Estado.NO_EJECUTADA);

        operaciones.add(operacion);
    }

    @Override
    public String toString() {
        return "Operaciones = " + operaciones ;
    }
}