package mx.edu.uacm.is.slt.ds.multitask_uacm.modelo;


/**
 * Interface Ejecutable la cuel define el comprotamiento de pausar, reanudar y detener
 * este comprtamietno lo comparte las clase Tarea y Operacion.
 * el tipo y los parametros que reciben los métodos de momento son void y no recive argumentos
 * mas adelante se modificaran si es conveniente
 * */
public interface Ejecutable {

    public  void reanudar();
    public void pausar();
    public void detener();

}
