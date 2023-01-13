package org.una.municipalidad.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.una.municipalidad.dto.AuthenticationResponse;
import org.una.municipalidad.util.AppContext;
import org.una.municipalidad.util.FlowController;
import org.una.municipalidad.util.RolesTypes;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GerentePrincipalController extends Controller{

    @FXML
    public JFXButton btnUsuarioLoguado;
    @FXML
    public Text txtLogeado;
    @FXML
    public Text txtRol;
    @FXML
    public Label lbl_pending;
    @FXML
    public Label lbl_completed;
    @FXML
    public BorderPane apRoot;
    @FXML
    public JFXButton btnValoresImpositivos;
    @FXML
    public JFXButton btnRegistro;
    @FXML
    public JFXButton btnCotribuyenteService;
    @FXML
    public JFXButton btnGenerarCobros;
    @FXML
    public JFXButton btnExpCancl;
    @FXML
    public JFXButton btnCobrosGenerados;
    @FXML
    public Button btnCerrarSesion;
    @FXML
    public VBox vbAcomodo;
    @FXML
    public AnchorPane apPrincipal;
    @FXML
    public AnchorPane apSecundario;
    @FXML
    public AnchorPane apBack;
    @FXML
    public AnchorPane apFront;

    private static FadeTransition transition;
    private final ObservableList<String> backImages= FXCollections.observableArrayList();
    private final ObservableList<String> frontImages=FXCollections.observableArrayList();
    private int backIndex=0;
    private int frontIndex=0;

    @Override
    public void initialize() throws IOException, ExecutionException, InterruptedException {

        AuthenticationResponse authenticationResponse = (AuthenticationResponse) AppContext.getInstance().get("Rol");

        txtLogeado.setText("Logeado");
        txtRol.setText(getRol(authenticationResponse.getRolDTO().getNombre()));
        btnUsuarioLoguado.setText(authenticationResponse.getUsuarioDTO().getNombreCompleto());

        frontImages.add("im1");
        frontImages.add("im3");
        frontImages.add("im5");
        frontImages.add("im7");

        backImages.add("im2");
        backImages.add("im4");
        backImages.add("im6");
        backImages.add("im8");

        setBackgroundImage(apBack,backImages.get(0),0);
        setBackgroundImage(apFront,frontImages.get(0),1);
        initSliderShow(4,4);

    }

    private String getRol(String user){

        if(user.equals("ROLE_GESTOR")){
           return "Gestor";
        }else if(user.equals("ROLE_GERENTE")){
            return "Gerente";
        }else if(user.equals("ROLE_AUDITOR")){
            return "Auditor";
        }else if(user.equals("ROLE_ADMINISTRADOR")){
            return "Administrador";
        }
        return "";
    }

    private void loadUI(String ui){
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/org/una/municipalidad/view/"+ ui +".fxml"));
        } catch (IOException ex) {
            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //((VBox) ((BorderPane) stage.getScene().getRoot()).getCenter()).getChildren().clear();
        //((VBox) ((BorderPane) stage.getScene().getRoot()).getCenter()).getChildren().add(root);
        ((VBox) apRoot.getCenter()).getChildren().clear();
        ((VBox) apRoot.getCenter()).getChildren().add(root);

    }

    public void handleButtonAction(MouseEvent mouseEvent) {
    }

    public void onActionValoresImp(ActionEvent actionEvent) {
        loadUI("ValoresImpositivos");
    }

    public void onActionRegistro(ActionEvent actionEvent) {
        loadUI("Registro");
    }

    public void onActionContribuyenteServicio(ActionEvent actionEvent) {
        loadUI("RegistroContribuyenteServicio");
    }

    public void onActionGenerarCobros(ActionEvent actionEvent) {
        loadUI("GenerarCobros");
    }

    public void oActionExpCobrosCanc(ActionEvent actionEvent) {
        loadUI("ExportarCobrosCancelados");
    }

    public void onActionCobrosGen(ActionEvent actionEvent) {
        loadUI("ExportarCobrosGenerados");
    }

    public void onActionCerrarSesion(ActionEvent actionEvent) {
        stop();
        FlowController.getInstance().goViewInNewStage("Login",stage);
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

    /**
     * Stop the Animation
     */
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
