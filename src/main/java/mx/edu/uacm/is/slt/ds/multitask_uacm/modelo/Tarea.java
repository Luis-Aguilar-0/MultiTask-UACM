package mx.edu.uacm.is.slt.ds.multitask_uacm.modelo;

import java.util.ArrayList;
import java.util.List;

public class Tarea {

    // Atributos
    private String nombre;
    private String descripcion;
    private List<String> precondiciones;
    private List<String> postcondiciones;
    private String comportamiento;

    public Tarea() {
        // Valores por defecto
        this.nombre = "Nueva tarea";
        this.descripcion = "";
        this.precondiciones = new ArrayList<>();
        this.postcondiciones = new ArrayList<>();
        this.comportamiento = "";
    }

    public Tarea(String nombre, String descripcion) {
        // Datos iniciales
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precondiciones = new ArrayList<>();
        this.postcondiciones = new ArrayList<>();
        this.comportamiento = "";
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

    public List<String> getPrecondiciones() {
        return precondiciones;
    }

    public List<String> getPostcondiciones() {
        return postcondiciones;
    }

    public String getComportamiento() {
        return comportamiento;
    }

    public void setComportamiento(String comportamiento) {
        this.comportamiento = comportamiento;
    }

    public void agregarPrecondicion(String precondicion) {
        // Solo agrega si trae texto
        if (precondicion != null && !precondicion.isBlank()) {
            precondiciones.add(precondicion);
        }
    }

    public void agregarPostcondicion(String postcondicion) {
        // Solo agrega si trae texto
        if (postcondicion != null && !postcondicion.isBlank()) {
            postcondiciones.add(postcondicion);
        }
    }

    public void eliminarPrecondicion(int indice) {
        // Borra si el índice existe
        if (indice >= 0 && indice < precondiciones.size()) {
            precondiciones.remove(indice);
        }
    }

    public void eliminarPostcondicion(int indice) {
        // Borra si el índice existe
        if (indice >= 0 && indice < postcondiciones.size()) {
            postcondiciones.remove(indice);
        }
    }

    @Override
    public String toString() {
        return nombre;
    }
}