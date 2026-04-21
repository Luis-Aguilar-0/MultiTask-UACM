package mx.edu.uacm.is.slt.ds.multitask_uacm.modelo;

import java.util.ArrayList;
import java.util.List;

public class GestorOperaciones {
    private List<Operacion> operaciones;

    public GestorOperaciones() {
        // Lista vacía al iniciar
        this.operaciones = new ArrayList<Operacion>();
    }

    public List<Operacion> getOperaciones() {
        return operaciones;
    }

    public void agregarOperacion(Operacion operacion) {
        // Solo agrega si existe
        if (operacion != null) {
            operaciones.add(operacion);
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
        operacion.setEstado("No ejecutada");

        operaciones.add(operacion);
    }
}