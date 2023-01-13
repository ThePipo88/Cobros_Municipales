package org.una.municipalidad;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.una.municipalidad.util.FlowController;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException, ExecutionException, InterruptedException {
        FlowController.getInstance().InitializeFlow(stage, null);
        stage.setTitle("Control de cobros municipales");
        FlowController.getInstance().goViewInWindow("Login");
    }

    public static void main(String[] args) {
        launch();
    }
}