package org.una.municipalidad.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import lombok.SneakyThrows;
import org.una.municipalidad.data.CobroGenerado;
import org.una.municipalidad.data.DataCobros;
import org.una.municipalidad.dto.CobroGeneradoDTO;
import org.una.municipalidad.service.CobroGeneradoService;
import org.una.municipalidad.service.ContribuyenteServicioService;
import org.una.municipalidad.service.ServicioService;
import org.una.municipalidad.util.Mensaje;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class GenerarCobros extends Controller{

    @FXML
    public AnchorPane ancMensual;
    @FXML
    public AnchorPane ancBimestral;
    @FXML
    public AnchorPane ancAnuales;
    @FXML
    public AnchorPane ancMenu;
    @FXML
    public JFXButton btnGenerarCobros;
    @FXML
    public DatePicker dtpFechaCobros;

    @FXML
    public JFXButton btnGenerarCobrosMensuales;
    @FXML
    public DatePicker dtpFechaInicioBus;
    @FXML
    public DatePicker dtpFechaFinalBus;
    @FXML
    public JFXButton btnMensual;
    @FXML
    public JFXButton btnBimestral;
    @FXML
    public JFXButton btnAnual;
    @FXML
    public TableView<DataCobros> tbvCobrosGeneradosBimestral;
    @FXML
    public TableColumn clId1;
    @FXML
    public TableColumn clEstado1;
    @FXML
    public TableColumn clFechaCobro1;
    @FXML
    public TableColumn clMonto1;
    @FXML
    public TableColumn clContribuyente1;
    @FXML
    public JFXButton btnGenerarCobrosBimestrales;
    @FXML
    public DatePicker dtpFechaInicioLimpieza;
    @FXML
    public DatePicker dtpFechaFinalLimpieza;
    @FXML
    public TableView <DataCobros>tbvCobrosGeneradosAnuales;
    @FXML
    public TableColumn clId11;
    @FXML
    public TableColumn clEstado11;
    @FXML
    public TableColumn clFechaCobro11;
    @FXML
    public TableColumn clMonto11;
    @FXML
    public TableColumn clContribuyente11;
    @FXML
    public JFXButton btnGenerarCobrosAnuales;
    @FXML
    public DatePicker dtpFechaInicioCementerio;
    @FXML
    public DatePicker dtpFechaFinalCementerio;
    @FXML
    public ImageView imgSpinner;
    @FXML
    public Text txtCobro1;
    @FXML
    public Text txtCobro11;
    @FXML
    public Text txtCobro2;
    @FXML
    private TableColumn clId;
    @FXML
    private TableColumn clEstado;
    @FXML
    private TableColumn clFechaCobro;
    @FXML
    private TableColumn clMonto;
    @FXML
    private TableColumn clContribuyente;


    @FXML
    public TableView<DataCobros> tbvCobrosGeneradosMensuales;

    private ObservableList<DataCobros> cobro = FXCollections.observableArrayList();

    private static Mensaje msg = new Mensaje();



    @Override
    public void initialize() {


    }

    void inicializarTabla()
    {
        this.clId.setCellValueFactory(new PropertyValueFactory("id"));
        this.clEstado.setCellValueFactory(new PropertyValueFactory("Estado"));
        this.clFechaCobro.setCellValueFactory(new PropertyValueFactory("FechaCobro"));
        this.clMonto.setCellValueFactory(new PropertyValueFactory("Monto"));
        this.clContribuyente.setCellValueFactory(new PropertyValueFactory("cContribuyente"));

    }
    void inicializarTabla2(){
        this.clId1.setCellValueFactory(new PropertyValueFactory("id"));
        this.clEstado1.setCellValueFactory(new PropertyValueFactory("Estado"));
        this.clFechaCobro1.setCellValueFactory(new PropertyValueFactory("FechaCobro"));
        this.clMonto1.setCellValueFactory(new PropertyValueFactory("Monto"));
        this.clContribuyente1.setCellValueFactory(new PropertyValueFactory("cContribuyente"));
    }

    void inicializarTabla3(){
        this.clId11.setCellValueFactory(new PropertyValueFactory("id"));
        this.clEstado11.setCellValueFactory(new PropertyValueFactory("Estado"));
        this.clFechaCobro11.setCellValueFactory(new PropertyValueFactory("FechaCobro"));
        this.clMonto11.setCellValueFactory(new PropertyValueFactory("Monto"));
        this.clContribuyente11.setCellValueFactory(new PropertyValueFactory("cContribuyente"));
    }


    public static Date convertToDateUsingDate(LocalDate date) {
        return java.sql.Date.valueOf(date);
    }

    public void onActionBtnGenerarBimestrales(ActionEvent actionEvent) {
        LocalDate inicio = dtpFechaInicioLimpieza.getValue();
        LocalDate fin = dtpFechaFinalLimpieza.getValue();
        String num = "0";



        if(inicio != null && fin != null ) {

            imgSpinner.setVisible(true);

            TranslateTransition translate = new TranslateTransition();
            translate.setDuration(Duration.millis(4000));
            translate.setAutoReverse(true);
            translate.setNode(imgSpinner);
            translate.play();

            Thread t = new Thread(){
                @SneakyThrows
                public void run(){
                    try {
                        CobroGeneradoDTO FechaCobrar = CobroGeneradoService.getFechaCobroLimpieza(inicio.toString(),fin.toString());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };

            t.start();

            translate.setOnFinished(event -> {
                imgSpinner.setVisible(false);
                txtCobro11.setText("Se generaron un total de 7000 registros");
                txtCobro11.setVisible(true);
            });


        }
        else{
            msg.show(Alert.AlertType.ERROR, "Error", "La fecha ingresada es incorrecta");
        }
    }


    public void onActionBtnGenerarAnuales(ActionEvent actionEvent) {
        LocalDate inicio = dtpFechaInicioCementerio.getValue();
        LocalDate fin = dtpFechaFinalCementerio.getValue();
        String num = "0";

        if(inicio != null && fin != null ) {

            imgSpinner.setVisible(true);

            TranslateTransition translate = new TranslateTransition();
            translate.setDuration(Duration.millis(4000));
            translate.setAutoReverse(true);
            translate.setNode(imgSpinner);
            translate.play();

            Thread t = new Thread(){
                @SneakyThrows
                public void run(){
                    try {
                        CobroGeneradoDTO FechaCobrar = CobroGeneradoService.getFechaCobroCementerio(inicio.toString(),fin.toString());
                        List<CobroGeneradoDTO> cobrosGenerados = CobroGeneradoService.getDatosGeneracionCobros(num);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };

            t.start();

            translate.setOnFinished(event -> {
                imgSpinner.setVisible(false);
                txtCobro2.setVisible(true);
                txtCobro2.setText("Se generaron un total de 1000 registros");
            });
        }
        else{
            msg.show(Alert.AlertType.ERROR, "Error", "La fecha ingresada es incorrecta");
        }
    }

    public void onActionBtnMensual(ActionEvent actionEvent) {
        ancMenu.setVisible(false);
        ancMensual.setVisible(true);
        inicializarTabla();



    }

    public void onActionBtnBimestral(ActionEvent actionEvent) {
        ancMenu.setVisible(false);
        ancBimestral.setVisible(true);
        inicializarTabla2();

    }

    public void onActionBtnAnual(ActionEvent actionEvent) {
        ancMenu.setVisible(false);
        ancAnuales.setVisible(true);
        inicializarTabla3();

    }

    public void onActionBtnGenerarCobrosMensuales(ActionEvent actionEvent) {
        LocalDate inicio = dtpFechaInicioBus.getValue();
        LocalDate fin = dtpFechaFinalBus.getValue();
        String num = "0";

        if(inicio != null && fin != null ) {

            imgSpinner.setVisible(true);

            TranslateTransition translate = new TranslateTransition();
            translate.setDuration(Duration.millis(4000));
            translate.setAutoReverse(true);
            translate.setNode(imgSpinner);
            translate.play();

            Thread t = new Thread(){
                @SneakyThrows
                public void run(){
                    try {
                        CobroGeneradoDTO FechaCobrar = CobroGeneradoService.getFechaCobroRutaBus(inicio.toString(),fin.toString());
                        List<CobroGeneradoDTO> cobrosGenerados = CobroGeneradoService.getDatosGeneracionCobros(num);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };

            t.start();

            translate.setOnFinished(event -> {
                imgSpinner.setVisible(false);
                txtCobro1.setText("Se generaron un total de 1000 registros");
                txtCobro1.setVisible(true);
            });

        }
        else{
            msg.show(Alert.AlertType.ERROR, "Error", "La fecha ingresada es incorrecta");
        }
    }
}
