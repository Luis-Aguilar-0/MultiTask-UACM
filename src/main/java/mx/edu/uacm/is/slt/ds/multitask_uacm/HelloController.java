package mx.edu.uacm.is.slt.ds.multitask_uacm;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("En proceso de desarrollo........!");
    }
}
