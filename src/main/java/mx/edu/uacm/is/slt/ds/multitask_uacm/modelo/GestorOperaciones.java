package mx.edu.uacm.is.slt.ds.multitask_uacm.modelo;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GestorOperaciones {
    
    private static GestorOperaciones instancia;
    private ObservableList<Operacion> operaciones;
    private List<Runnable> listeners = new ArrayList<>();
    
    private GestorOperaciones() {
        operaciones = FXCollections.observableArrayList();
    }
    
    public static GestorOperaciones obtenerInstancia() {
        if (instancia == null) {
            instancia = new GestorOperaciones();
        }
        return instancia;
    }
    
    public ObservableList<Operacion> getOperaciones() {
        return operaciones;
    }
    
    public void agregarOperacion(Operacion operacion) {
        operaciones.add(operacion);
        notifyChanges();
    }
    
    public void addListener(Runnable listener) {
        listeners.add(listener);
    }
    
    public void notifyChanges() {
        for (Runnable listener : listeners) {
            listener.run();
        }
    }
}