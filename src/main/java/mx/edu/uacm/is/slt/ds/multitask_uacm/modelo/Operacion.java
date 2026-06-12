package mx.edu.uacm.is.slt.ds.multitask_uacm.modelo;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Operacion implements Ejecutable, Runnable {
    private String nombre;
    private String descripcion;
    private ObservableList<Tarea> tareas;
    private Estado estado;

    private Thread hiloPrincipal;
    private volatile boolean corriendo;
    private volatile boolean pausada;
    private final Object bloqueo = new Object();

    public Operacion() {
        this.nombre = "Nueva operación";
        this.descripcion = "";
        this.tareas = FXCollections.observableArrayList();
        this.estado = Estado.NO_EJECUTADA;
        this.corriendo = false;
        this.pausada = false;
    }

    public Operacion(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tareas = FXCollections.observableArrayList();
        this.estado = Estado.NO_EJECUTADA;
        this.corriendo = false;
        this.pausada = false;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public List<Tarea> getTareas() { return tareas; }

    public Estado getEstado() { return estado; }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public void agregarTarea(Tarea tarea) {
        if (tarea != null) tareas.add(tarea);
    }

    public void eliminarTarea(int indice) {
        if (indice >= 0 && indice < tareas.size()) tareas.remove(indice);
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

    public void ejecutar() {
        if (hiloPrincipal != null && hiloPrincipal.isAlive()) {
            return;
        }
        corriendo = true;
        pausada = false;
        setEstado(Estado.EN_EJECUCION);
        hiloPrincipal = new Thread(this, "Orquestador-" + nombre);
        hiloPrincipal.setDaemon(true);
        hiloPrincipal.start();
    }

    @Override
    public void run() {
        try {
            for (Tarea tareaActual : tareas) {
                synchronized (bloqueo) {
                    while (pausada && corriendo) {
                        setEstado(Estado.PAUSADO);
                        bloqueo.wait();
                    }
                }

                if (!corriendo) {
                    break;
                }

                setEstado(Estado.EN_EJECUCION);
                tareaActual.ejecutar();

                // Ciclo sychronized encargado de retener la operacion hasta que el progreso de la tarea alcance el cien por ciento
                while (tareaActual.getProgreso() < 100.0 && corriendo) {
                    synchronized (bloqueo) {
                        while (pausada && corriendo) {
                            setEstado(Estado.PAUSADO);
                            bloqueo.wait(); // Duerme el orquestador si el usuario presiona el boton de pausa en la interfaz
                        }
                    }
                    Thread.sleep(200); // Pequeño retardo de sondeo asincrono para no saturar el procesador
                }
            }

            if (corriendo) {
                setEstado(Estado.FINALIZADA);
                System.out.println("Operacion " + nombre + " termino todas sus tareas");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            corriendo = false;
        }
    }

    @Override
    public void pausar() {
        if (!corriendo) {
            return;
        }
        pausada = true;
        for (Tarea tarea : tareas) tarea.pausar();
        setEstado(Estado.PAUSADO);
    }

    @Override
    public void reanudar() {
        if (!pausada) {
            return;
        }
        for (Tarea tareaActual : tareas) {
            tareaActual.reanudar();
        }

        synchronized (bloqueo) {
            pausada = false;
            setEstado(Estado.EN_EJECUCION);
            bloqueo.notifyAll();
        }
    }

    @Override
    public void detener() {
        corriendo = false;
        pausada = false;

        for (Tarea tareaActual : tareas) {
            tareaActual.detener();
            tareaActual.setProgreso(0.0); 
        }

        synchronized (bloqueo) {
            bloqueo.notifyAll();
        }

        if (hiloPrincipal != null) {
            hiloPrincipal.interrupt();
        }

        setEstado(Estado.DETENIDA);
        hiloPrincipal = null;
    }

    @Override
    public String toString() {
        return nombre;
    }
}