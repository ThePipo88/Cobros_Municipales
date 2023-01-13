package org.una.municipalidad.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.una.municipalidad.data.Transacciones;
import org.una.municipalidad.dto.CobroGeneradoDTO;
import org.una.municipalidad.dto.TransaccionDTO;
import org.una.municipalidad.service.TransaccionService;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TransaccionesController extends Controller{

    @FXML
    public TableView<Transacciones> tvTransacciones;
    @FXML
    public TableColumn tcId;
    @FXML
    public TableColumn tcAccion;
    @FXML
    public TableColumn tcFechaCreacion;
    @FXML
    public TableColumn tcObjecto;
    @FXML
    public TableColumn tcUsuario;
    @FXML
    public JFXButton btnTransacciones;
    @FXML
    public ImageView imgSpinner;

    private List<TransaccionDTO> transaccionesList = null;

    private final ObservableList<Transacciones> transaccionDTOS = FXCollections.observableArrayList();

    @Override
    public void initialize() throws IOException, ExecutionException, InterruptedException {

        this.tcId.setCellValueFactory(new PropertyValueFactory("id"));
        this.tcAccion.setCellValueFactory(new PropertyValueFactory("accion"));
        this.tcFechaCreacion.setCellValueFactory(new PropertyValueFactory("fechaCreacion"));
        this.tcObjecto.setCellValueFactory(new PropertyValueFactory("objeto"));
        this.tcUsuario.setCellValueFactory(new PropertyValueFactory("usuario"));

    }

    public void onActionTransacciones(ActionEvent actionEvent) throws IOException, ExecutionException, InterruptedException {
        imgSpinner.setVisible(true);

        TranslateTransition translate = new TranslateTransition();
        translate.setDuration(Duration.millis(1000));
        translate.setAutoReverse(true);
        translate.setNode(imgSpinner);
        translate.play();

        Runnable rn=()->{
            Platform.setImplicitExit(false);
            Platform.runLater(()-> {
                try {
                    transaccionesList = TransaccionService.getTransacciones();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        };


        Thread t=new Thread(rn);
        t.start();

        translate.setOnFinished(event -> {
            imgSpinner.setVisible(false);
            for(TransaccionDTO transaccionDTO:transaccionesList) {
                transaccionDTOS.add(new Transacciones(transaccionDTO.getId().toString(),transaccionDTO.getAccion(),transaccionDTO.getFechaCreacion().toString(),transaccionDTO.getObjeto(),
                        transaccionDTO.getUsuario().getNombreCompleto()));
            }
            tvTransacciones.setItems(transaccionDTOS);
        });
    }
}
