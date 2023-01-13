package org.una.municipalidad.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.util.Duration;
import lombok.SneakyThrows;
import org.una.municipalidad.dto.AuthenticationResponse;
import org.una.municipalidad.service.LoginService;
import org.una.municipalidad.util.FlowController;
import org.una.municipalidad.util.Mensaje;


import java.awt.*;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class LoginController extends Controller{

    @FXML
    public JFXTextField txtUsuario;
    @FXML
    public JFXPasswordField txtContrasena;
    @FXML
    public JFXButton btnIniciarSesion;
    @FXML
    public AnchorPane apImagenes;
    @FXML
    public AnchorPane apBack;
    @FXML
    public AnchorPane apFront;
    @FXML
    public AnchorPane vbDatos;
    @FXML
    public ImageView imgSpinner;

    private AuthenticationResponse login = null;

    private static FadeTransition transition;
    private final ObservableList<String> backImages= FXCollections.observableArrayList();
    private final ObservableList<String> frontImages=FXCollections.observableArrayList();
    private int backIndex=0;
    private int frontIndex=0;
    private static Mensaje msg = new Mensaje();

    @Override
    public void initialize() {
        String img1 = "img1";
        String img2 = "img2";
        String img3 = "img3";
        String img4 = "img4";

        frontImages.add("im1");
        frontImages.add("im3");
        frontImages.add("im5");
        frontImages.add("im7");

        backImages.add("im2");
        backImages.add("im4");
        backImages.add("im6");
        backImages.add("im8");

        apBack.getStyleClass().add(backImages.get(0));
        apFront.getStyleClass().add(frontImages.get(0));
        initSliderShow(4,4);

    }

    public void iniciar(ActionEvent actionEvent) throws IOException, ExecutionException, InterruptedException {

        TranslateTransition translate = new TranslateTransition();
        translate.setDuration(Duration.millis(2000));
        translate.setAutoReverse(true);
        translate.setNode(imgSpinner);
        translate.play();

        imgSpinner.setVisible(true);

        Thread thread = new Thread() {
            @SneakyThrows
            public void run() {
                login = LoginService.login(txtUsuario.getText().toString(), txtContrasena.getText().toString());

            }
        };

        thread.start();


        translate.setOnFinished(event -> {

            imgSpinner.setVisible(false);

            if(login != null){
                stop();
                if(login.getRolDTO().getNombre().equals("ROLE_GESTOR")){
                    FlowController.getInstance().goViewInNewStage("GestorPrincipal",stage);
                }
                else if(login.getRolDTO().getNombre().equals("ROLE_GERENTE")){
                    FlowController.getInstance().goViewInNewStage("GerentePrincipal",stage);
                }
                else if(login.getRolDTO().getNombre().equals("ROLE_AUDITOR")){
                    FlowController.getInstance().goViewInNewStage("AuditorPrincipal",stage);
                }
                else if(login.getRolDTO().getNombre().equals("ROLE_ADMINISTRADOR")){
                    FlowController.getInstance().goViewInNewStage("Principal",stage);
                }
            }else{
                msg.show(Alert.AlertType.ERROR, "Error", "Usuario o contraeña incorrectos, vuelva a ingresarlo nuevamente");
            }
        });
    }

    public void initSliderShow(int animationDelay, int visibilityDelay){
        Runnable rn=()->{
            Platform.runLater(()->{
                apFront.opacityProperty().addListener((observable, oldValue, newValue) -> {
                    PauseTransition pt;
                    if (newValue.doubleValue()==0){
                        frontIndex++;
                        if (frontIndex==frontImages.size()){
                            frontIndex=0;
                        }
                        setBackgroundImage(apFront,frontImages.get(frontIndex),1);
                        pt=new PauseTransition(Duration.seconds(visibilityDelay));
                        pt.setOnFinished(event -> {
                            transition.play();
                        });
                        transition.pause();
                        pt.play();
                    }else if (newValue.doubleValue()==1){
                        backIndex++;
                        if (backIndex==backImages.size()){
                            backIndex=0;
                        }
                        setBackgroundImage(apBack,backImages.get(backIndex),0);
                        pt=new PauseTransition(Duration.seconds(visibilityDelay));
                        pt.setOnFinished(event -> {
                            transition.play();
                        });
                        transition.pause();
                        pt.play();
                    }
                });
            });

            transition=new FadeTransition(Duration.seconds(animationDelay),apFront);
            transition.setFromValue(0);
            transition.setToValue(1);
            transition.setAutoReverse(true);
            transition.setCycleCount(-1);
            transition.play();

        };
        Thread t=new Thread(rn);
        t.start();
    }

    public synchronized void stop(){
        if (transition!=null){
            transition.stop();
        }
    }

    private void setBackgroundImage(AnchorPane target, String image, int pantalla){

        if(target.getStyleClass().size() > 0){
            target.getStyleClass().remove(0);
        }
        target.getStyleClass().add(image);
    }
}
