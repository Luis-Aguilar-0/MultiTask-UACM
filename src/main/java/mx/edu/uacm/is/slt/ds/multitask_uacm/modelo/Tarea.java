package mx.edu.uacm.is.slt.ds.multitask_uacm.modelo;

import mx.edu.uacm.is.slt.ds.multitask_uacm.modelo.Ejecutable;


public class Tarea implements Ejecutable{

    //Atriburos
    private String nombre;
    private String descripcion;
    private  String estado;

    //constructores
    public Tarea(){}


    //metodos
    @Override
    public void reanudar() {

    }

    @Override
    public void pausar() {

    }

    @Override
    public void detener() {

    }

}
