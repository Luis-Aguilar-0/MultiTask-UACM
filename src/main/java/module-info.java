module multitask_uacm {
    requires javafx.controls;
    requires javafx.fxml;

    exports mx.edu.uacm.is.slt.ds.multitask_uacm.controlador;
    opens mx.edu.uacm.is.slt.ds.multitask_uacm.controlador to javafx.fxml;
}