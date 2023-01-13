package org.una.municipalidad.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.una.municipalidad.data.ReporteRecibo;
import org.una.municipalidad.util.Mensaje;
import java.io.IOException;
import java.time.LocalDate;
import java.util.concurrent.ExecutionException;

public class GenerarReportesController extends Controller{
    @FXML
    public JFXButton btnRecibosContribuyente;
    @FXML
    public JFXButton btnValorImpositivo;
    @FXML
    public JFXButton btnGenerarReporte;
    @FXML
    public DatePicker dtpFechaInicial;
    @FXML
    public DatePicker dtpFechaFinal;
    @FXML
    public TableView<ReporteRecibo> tbvReporte;
    @FXML
    private TableColumn tcId;
    @FXML
    private TableColumn tcIdContribuyente;
    @FXML
    private TableColumn tcNombre;
    @FXML
    private TableColumn tcValorImpositivo;
    @FXML
    private TableColumn tcTotal;
    @FXML
    private TableColumn tcFechaEmision;

    private ObservableList<ReporteRecibo> reportes = FXCollections.observableArrayList();

    private static Mensaje msg = new Mensaje();


    @Override
    public void initialize() {
        inicializarTabla();

    }

    private void inicializarTabla(){
        this.tcId.setCellValueFactory(new PropertyValueFactory("numeroRecibo"));
        this.tcIdContribuyente.setCellValueFactory(new PropertyValueFactory("idContribuyente"));
        this.tcNombre.setCellValueFactory(new PropertyValueFactory("nombreContribuyente"));
        this.tcValorImpositivo.setCellValueFactory(new PropertyValueFactory("tipoValorImpositivo"));
        this.tcTotal.setCellValueFactory(new PropertyValueFactory("total"));
        this.tcFechaEmision.setCellValueFactory(new PropertyValueFactory("FechaEmision"));
        reportes.add(new ReporteRecibo("","","","","",""));
        this.tbvReporte.setItems(reportes);
    }

    public void onActionBtnReciboscontribuyente(ActionEvent actionEvent) throws IOException, ExecutionException, InterruptedException {
        LocalDate inicio = dtpFechaInicial.getValue();
        LocalDate fin = dtpFechaFinal.getValue();
        /*
        if(inicio != null && fin != null){
            this.tbvReporte.getColumns().get(4).setText("Recibo");
            reportes.clear();

            List<ReciboDTO> recibos = ReciboService.getBetweenFecha(inicio.toString(),fin.toString());

            if(activos != null){

                for(ActivoDTO activo:activos) {
                    reportes.add(new Reporte(activo.getId().toString(),activo.getNombre(),activo.getFechaCreacion().toString(),String.valueOf(activo.isEstado()),activo.getProveedor().getNombre()));
                }

                Collections.sort(reportes);
                AppContext.getInstance().delete("reporte");
                AppContext.getInstance().set("reporte",reportes);

                this.tb_datos.setItems(reportes);
            }

        }
        else{
            msg.show(Alert.AlertType.ERROR, "Error", "La fechas ingresadas son incorrectas, vuelva a intentarlo");
        }
        */
    }

    public void onActionBtnValorImpositivo(ActionEvent actionEvent) {

        LocalDate inicio = dtpFechaInicial.getValue();
        LocalDate fin = dtpFechaFinal.getValue();
        /*
        if(inicio != null && fin != null){

            this.tbvReporte.getColumns().get(4).setText("Valor Impositivo");
            reportes.clear();

            List<ReciboDTO> activos = ActivoService.getBetweenFecha(inicio.toString(),fin.toString());

            if(activos != null){
                for(ActivoDTO activo:activos) {
                    reportes.add(new Reporte(activo.getId().toString(),activo.getNombre(),activo.getFechaCreacion().toString(),String.valueOf(activo.isEstado()),activo.getMarca().getNombre()));
                }

                Collections.sort(reportes);
                AppContext.getInstance().delete("reporte");
                AppContext.getInstance().set("reporte",reportes);

                this.tbvReporte.setItems(reportes);
            }

        }else{
            msg.show(Alert.AlertType.ERROR, "Error", "La fechas ingresadas son incorrectas, vuelva a intentarlo");
        }*/
    }

    public void onActionBtnGenerarReporte(ActionEvent actionEvent) {
       /* try{

            JasperReport report = (JasperReport) JRLoader.loadObject(getClass().getResource("/org/una/inventario/reporteJasper/Datos.jasper"));
            JasperPrint jprint = JasperFillManager.fillReport(report, null, ReporteDataSource.getDataSource());

            JasperViewer view = new JasperViewer(jprint, false);
            view.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            view.setVisible(true);


        }catch(JRException ex){
            ex.getMessage();
        }*/
    }

}
