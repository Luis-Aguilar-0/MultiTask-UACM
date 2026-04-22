package mx.edu.uacm.is.slt.ds.multitask_uacm.modelo;

import java.util.ArrayList;
import java.util.List;

public class Tarea implements Ejecutable {
    private String nombre;
    private String descripcion;
    private List<String> precondiciones;
    private List<String> postcondiciones;
    private String comportamiento;
    private String estado;

    public Tarea() {
        this.nombre = "Nueva tarea";
        this.descripcion = "";
        this.precondiciones = new ArrayList<>();
        this.postcondiciones = new ArrayList<>();
        this.comportamiento = "";
        this.estado = "No ejecutada";
    }

    public Tarea(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precondiciones = new ArrayList<>();
        this.postcondiciones = new ArrayList<>();
        this.comportamiento = "";
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void agregarPrecondicion(String precondicion) {
        if (precondicion != null && !precondicion.isBlank()) {
            precondiciones.add(precondicion);
        }
    }

    public void agregarPostcondicion(String postcondicion) {
        if (postcondicion != null && !postcondicion.isBlank()) {
            postcondiciones.add(postcondicion);
        }
    }

    public void eliminarPrecondicion(int indice) {
        if (indice >= 0 && indice < precondiciones.size()) {
            precondiciones.remove(indice);
        }
    }

    public void eliminarPostcondicion(int indice) {
        if (indice >= 0 && indice < postcondiciones.size()) {
            postcondiciones.remove(indice);
        }
    }

    @Override
    public void pausar() {
        estado = "Pausada";
    }

    @Override
    public void reanudar() {
        estado = "En ejecución";
    }

    @Override
    public void detener() {
        estado = "Detenida";
    }

    @Override
    public String toString() {
        return nombre;
    }
}