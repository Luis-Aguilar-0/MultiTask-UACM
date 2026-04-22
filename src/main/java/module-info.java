module mx.edu.uacm.is.slt.ds.multitask_uacm {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    exports  mx.edu.uacm.is.slt.ds.multitask_uacm;
    exports mx.edu.uacm.is.slt.ds.multitask_uacm.controlador;
    exports mx.edu.uacm.is.slt.ds.multitask_uacm.modelo;
    opens mx.edu.uacm.is.slt.ds.multitask_uacm.controlador to javafx.fxml;
}