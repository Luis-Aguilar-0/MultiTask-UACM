module mx.edu.uacm.is.slt.ds.multitask_uacm {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    
    // Abre los paquetes que JavaFX necesita para refleccion
    opens mx.edu.uacm.is.slt.ds.multitask_uacm to javafx.fxml;
    opens mx.edu.uacm.is.slt.ds.multitask_uacm.controlador to javafx.fxml;
    opens mx.edu.uacm.is.slt.ds.multitask_uacm.modelo to javafx.base;
    opens mx.edu.uacm.is.slt.ds.multitask_uacm.vista to javafx.fxml;
    
    // Exporta los paquetes para que sean accesibles
    exports mx.edu.uacm.is.slt.ds.multitask_uacm;
    exports mx.edu.uacm.is.slt.ds.multitask_uacm.controlador;
    exports mx.edu.uacm.is.slt.ds.multitask_uacm.modelo;
    exports mx.edu.uacm.is.slt.ds.multitask_uacm.vista;
}