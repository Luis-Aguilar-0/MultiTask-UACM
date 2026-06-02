package mx.edu.uacm.is.slt.ds.multitask_uacm.modelo;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;

public class Tarea implements Ejecutable, Runnable {

    private String nombre;
    private String descripcion;
    private List<String> precondiciones;
    private List<String> postcondiciones;
    private String comportamiento;
    private String estado;
    private String tipoTarea;
    private List<String> dependencias;
    private boolean pausable;

    // --- NUEVAS PROPIEDADES PARA ROBUSTECER EL CONTROL GRÁFICO Y HILOS ---
    private double progreso;
    private Thread hilo;
    private volatile boolean corriendo;
    private volatile boolean pausada;
    private final Object bloqueo = new Object();

    public Tarea() {
        this.nombre = "Nueva tarea";
        this.descripcion = "";
        this.precondiciones = new ArrayList<>();
        this.postcondiciones = new ArrayList<>();
        this.comportamiento = "";
        this.estado = "No ejecutada";
        this.tipoTarea = "Inicial (puede iniciar sola)";
        this.dependencias = new ArrayList<>();
        this.pausable = true;
        this.corriendo = false;
        this.pausada = false;
        this.progreso = 0.0;
    }

    public Tarea(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precondiciones = new ArrayList<>();
        this.postcondiciones = new ArrayList<>();
        this.comportamiento = "";
        this.estado = "No ejecutada";
        this.tipoTarea = "Inicial (puede iniciar sola)";
        this.dependencias = new ArrayList<>();
        this.pausable = true;
        this.corriendo = false;
        this.pausada = false;
        this.progreso = 0.0;
    }


    public double getProgreso() {
        return progreso;
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
        Platform.runLater(() -> this.estado = estado);
    }

    public String getTipoTarea() {
        return tipoTarea;
    }

    public void setTipoTarea(String tipoTarea) {
        this.tipoTarea = tipoTarea;
    }

    public List<String> getDependencias() {
        return dependencias;
    }

    public void agregarDependencia(String dep) {
        if (dep != null && !dep.isBlank()) {
            dependencias.add(dep);
        }
    }

    public boolean isPausable() {
        return pausable;
    }

    public void setPausable(boolean pausable) {
        this.pausable = pausable;
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


    public void ejecutar() {
        if (hilo != null && hilo.isAlive()) {
            return;
        }
        corriendo = true;
        pausada = false;
        setEstado("En ejecución");
        hilo = new Thread(this, "Hilo-" + nombre);
        hilo.setDaemon(true);
        hilo.start();
    }

    @Override
    public void run() {
        try {

            while (progreso < 100.0 && corriendo) {
                synchronized (bloqueo) {
                    while (pausada && corriendo) {
                        setEstado("Pausada");
                        bloqueo.wait(); // Suspensión real del hilo sin consumo de CPU
                    }
                }

                if (!corriendo) {
                    break;
                }


                Thread.sleep(200);


                Platform.runLater(() -> progreso += 10.0);
                System.out.println("[" + nombre + "] Progreso: " + progreso + "%");
            }

            if (corriendo && progreso >= 100.0) {
                setEstado("Finalizada");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            corriendo = false;
        }
    }

    @Override
    public void pausar() {
        if (!pausable || !corriendo) {
            return;
        }
        pausada = true;
        System.out.println("[" + nombre + "] Pausa solicitada.");
    }

    @Override
    public void reanudar() {
        if (!pausada) {
            return;
        }
        synchronized (bloqueo) {
            pausada = false;
            setEstado("En ejecución");
            bloqueo.notifyAll(); // Despierta el hilo secundario
        }
        System.out.println("[" + nombre + "] Reanudada.");
    }

    @Override
    public void detener() {
        corriendo = false;
        pausada = false;
        synchronized (bloqueo) {
            bloqueo.notifyAll();
        }
        if (hilo != null) {
            hilo.interrupt(); // Interrumpe inmediatamente si el hilo estaba en sleep()
        }
        Platform.runLater(() -> progreso = 0.0); // Reseteo completo conforme a la ERS
        setEstado("Detenida");
        hilo = null;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
