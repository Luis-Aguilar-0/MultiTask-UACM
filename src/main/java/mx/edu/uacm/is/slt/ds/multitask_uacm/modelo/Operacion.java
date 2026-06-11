package mx.edu.uacm.is.slt.ds.multitask_uacm.modelo;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;

public class Operacion implements Ejecutable, Runnable {
    private String nombre;
    private String descripcion;
    private ObservableList<Tarea> tareas;
    private Estado estado;

    //Atributos para el manejo de hilos
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
        // actualizamos el estado del hilo de javaFX para evitar que se conjele la intrefaz garfica
        Platform.runLater(() -> this.estado = estado);
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
        //chacamos si ya esta corriedo y no es null
        if(hiloPrincipal != null && hiloPrincipal.isAlive()) {
            return; // evita que se ejecute dos veces
        }
        corriendo = true;
        pausada = false;
        setEstado(Estado.EN_EJECUCION);

        //se crea el hilo
        hiloPrincipal = new Thread(this, "Orquestador-" + nombre);
        hiloPrincipal.setDaemon(true);
        hiloPrincipal.start();

    }


    @Override
    public void run() {

        try {
            for(Tarea tareaActual: tareas) {
                //chacamos si la operacion esta pausada
                synchronized(bloqueo) {
                    while (pausada && corriendo){
                        setEstado(Estado.PAUSADO);
                        bloqueo.wait();
                    }
                }

                //si se detuvo la operacion salimos del ciclo
                if(!corriendo){
                    break;
                }

                setEstado(Estado.EN_EJECUCION);

                //se ejecuta la tarea actual
                tareaActual.ejecutar();

                //esperamos a que termine para continual con la siguiente tarea
                while (tareaActual.getProgreso() < 100.0 && corriendo){
                    Thread.sleep(300);
                }
            }

            //si la opearcion no fue detenida
            if(corriendo){
                setEstado(Estado.FINALIZADA);
                System.out.println("Operacion " + nombre + " termino todas sus tareas");
            }
        }catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }finally {
            corriendo = false;
        }

    }

    @Override
    public void pausar() {
        if(!corriendo){
           return;
        }
        pausada = true;

        for (Tarea tarea : tareas) tarea.pausar();
    }

    @Override
    public void reanudar() {

        if(!pausada){
           return;
        }
        for(Tarea tareaActual: tareas){
            tareaActual.reanudar();
        }

        synchronized(bloqueo) {
            pausada = false;
            setEstado(Estado.EN_EJECUCION);
            bloqueo.notifyAll();
        }


    }

    @Override
    public void detener() {
        corriendo = false;
        pausada = false;

        for(Tarea tareaActual: tareas){
            tareaActual.detener();
        }

        // libera el hilo
        synchronized(bloqueo) {
            bloqueo.notifyAll();
        }
        if(hiloPrincipal != null ) {
            hiloPrincipal.interrupt();
        }
        setEstado(Estado.DETENIDA);
        hiloPrincipal = null;


    }

    @Override
    public String toString() { return nombre; }

}