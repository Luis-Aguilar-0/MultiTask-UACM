package mx.edu.uacm.is.slt.ds.multitask_uacm.modelo;

import java.util.ArrayList;
import java.util.List;

public class Operacion {
    private String nombre;
    private String descripcion;
    private List<Tarea> tareas;
    private String estado;

    public Operacion() {
        // Valores iniciales
        this.nombre = "Nueva operación";
        this.descripcion = "";
        this.tareas = new ArrayList<>();
        this.estado = "No ejecutada";
    }

    public Operacion(String nombre, String descripcion) {
        // Datos iniciales
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tareas = new ArrayList<>();
        this.estado = "No ejecutada";
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Tarea> getTareas() {
        return tareas;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void agregarTarea(Tarea tarea) {
        // Solo agrega si no viene vacía
        if (tarea != null) {
            tareas.add(tarea);
        }
    }

    public void eliminarTarea(int indice) {
        // Borra si el índice existe
        if (indice >= 0 && indice < tareas.size()) {
            tareas.remove(indice);
        }
    }

    public void moverTareaArriba(int indice) {
        // Sube una tarea en la lista
        if (indice > 0 && indice < tareas.size()) {
            Tarea temporal = tareas.get(indice);
            tareas.set(indice, tareas.get(indice - 1));
            tareas.set(indice - 1, temporal);
        }
    }

    public void moverTareaAbajo(int indice) {
        // Baja una tarea en la lista
        if (indice >= 0 && indice < tareas.size() - 1) {
            Tarea temporal = tareas.get(indice);
            tareas.set(indice, tareas.get(indice + 1));
            tareas.set(indice + 1, temporal);
        }
    }

    @Override
    public String toString() {
        return nombre;
    }
}