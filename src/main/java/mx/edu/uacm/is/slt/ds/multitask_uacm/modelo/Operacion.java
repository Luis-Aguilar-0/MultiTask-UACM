package mx.edu.uacm.is.slt.ds.multitask_uacm.modelo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class Operacion implements Ejecutable {
    private String nombre;
    private String descripcion;
    private ObservableList<Tarea> tareas;
    private Estado estado;

    public Operacion() {
        this.nombre = "Nueva operación";
        this.descripcion = "";
        this.tareas = FXCollections.observableArrayList();
        this.estado = Estado.NO_EJECUTADA;
    }

    public Operacion(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tareas = FXCollections.observableArrayList();
        this.estado = Estado.NO_EJECUTADA;
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

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
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
        estado = Estado.PAUSADO;
        for (Tarea tarea : tareas) {
            tarea.pausar();
        }
    }

    @Override
    public void reanudar() {
        estado = Estado.EN_EJECUCION;
        for (Tarea tarea : tareas) {
            tarea.reanudar();
        }
    }

    @Override
    public void detener() {
        estado = Estado.DETENIDA;
        for (Tarea tarea : tareas) {
            tarea.detener();
        }
    }

    @Override
    public String toString() {
        return nombre;
    }
}