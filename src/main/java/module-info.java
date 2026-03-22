module mx.edu.uacm.is.slt.ds.multitask_uacm {
    requires javafx.controls;
    requires javafx.fxml;

    opens mx.edu.uacm.is.slt.ds.multitask_uacm to javafx.fxml;
    exports mx.edu.uacm.is.slt.ds.multitask_uacm;
}