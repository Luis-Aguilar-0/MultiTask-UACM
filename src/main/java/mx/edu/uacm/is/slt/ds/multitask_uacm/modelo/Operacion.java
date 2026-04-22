package mx.edu.uacm.is.slt.ds.multitask_uacm.modelo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class Operacion implements Ejecutable {
    private String nombre;
    private String descripcion;
    private ObservableList<Tarea> tareas;
    private String estado;

    public Operacion() {
        this.nombre = "Nueva operación";
        this.descripcion = "";
        this.tareas = FXCollections.observableArrayList();
        this.estado = "No ejecutada";
    }

    public Operacion(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tareas = FXCollections.observableArrayList();
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
        if (tarea != null) {
            tareas.add(tarea);
        }
    }

    public void eliminarTarea(int indice) {
        if (indice >= 0 && indice < tareas.size()) {
            tareas.remove(indice);
        }
    }

    public void moverTareaArriba(int indice) {
        if (indice > 0 && indice < tareas.size()) {
            Tarea temporal = tareas.get(indice);
            tareas.set(indice, tareas.get(indice - 1));
            tareas.set(indice - 1, temporal);
        }
    }

    public void moverTareaAbajo(int indice) {
        if (indice >= 0 && indice < tareas.size() - 1) {
            Tarea temporal = tareas.get(indice);
            tareas.set(indice, tareas.get(indice + 1));
            tareas.set(indice + 1, temporal);
        }
    }

    @Override
    public void pausar() {
        estado = "Pausada";
        for (Tarea tarea : tareas) {
            tarea.pausar();
        }
    }

    @Override
    public void reanudar() {
        estado = "En ejecución";
        for (Tarea tarea : tareas) {
            tarea.reanudar();
        }
    }

    @Override
    public void detener() {
        estado = "Detenida";
        for (Tarea tarea : tareas) {
            tarea.detener();
        }
    }

    @Override
    public String toString() {
        return nombre;
    }
}