package org.una.municipalidad.controller;

//import com.gembox.spreadsheet.*;
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
import javafx.stage.FileChooser;
import javafx.util.Duration;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.una.municipalidad.data.CobroCancelados;
import org.una.municipalidad.data.CobroGenerado;
import org.una.municipalidad.dto.CobroCanceladoDTO;
import org.una.municipalidad.dto.CobroGeneradoDTO;
import org.una.municipalidad.service.CobroGeneradoService;
import org.una.municipalidad.util.Mensaje;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class ExportarCobrosGeneradosController extends Controller{
    @FXML
    public TableView<CobroGenerado> tbCobrosGenerados;
    @FXML
    public TableColumn cl_id;
    @FXML
    public TableColumn cl_Estado;
    @FXML
    public TableColumn cl_FechaCobro;
    @FXML
    public TableColumn cl_Monto;
    @FXML
    public TableColumn cl_Contribuyente;
    @FXML
    public TableColumn cl_Servicio;
    @FXML
    public JFXButton btnCargarCobros;
    @FXML
    public JFXButton btnGenerarExcel;
    @FXML
    public JFXButton btnLimpiar;
    @FXML
    public TableColumn cl_IdServicio;
    @FXML
    public ImageView imgSpinner;

    private ObservableList<CobroGenerado> cobrosGenerados = FXCollections.observableArrayList();

    private Mensaje msg =  new Mensaje();

    private List<CobroGeneradoDTO> cobros = null;

    @Override
    public void initialize() {
        this.cl_id.setCellValueFactory(new PropertyValueFactory("cl_id"));
        this.cl_Estado.setCellValueFactory(new PropertyValueFactory("cl_Estado"));
        this.cl_FechaCobro.setCellValueFactory(new PropertyValueFactory("cl_FechaCobro"));
        this.cl_Monto.setCellValueFactory(new PropertyValueFactory("cl_Monto"));
        this.cl_Contribuyente.setCellValueFactory(new PropertyValueFactory("cl_Contribuyente"));
        this.cl_Servicio.setCellValueFactory(new PropertyValueFactory("cl_Servicio"));
        this.cl_IdServicio.setCellValueFactory(new PropertyValueFactory("cl_servicioId"));
    }

    public void onActionCargarCobros(ActionEvent actionEvent) throws IOException, ExecutionException, InterruptedException {

        imgSpinner.setVisible(true);

        TranslateTransition translate = new TranslateTransition();
        translate.setDuration(Duration.millis(5000));
        translate.setAutoReverse(true);
        translate.setNode(imgSpinner);
        translate.play();

        Runnable rn=()-> {
            Platform.setImplicitExit(false);
            Platform.runLater(() -> {
                try {
                    cobros = CobroGeneradoService.getCobrosGenerados();
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
            if(cobros != null){
                for(CobroGeneradoDTO cobro:cobros) {
                    if(cobro.isEstado()){
                        SimpleDateFormat DateFor = new SimpleDateFormat("yyyy/MM/dd");
                        String stringDate = DateFor.format(cobro.getFechaCobro());
                        cobrosGenerados.add(new CobroGenerado(cobro.getId(),cobro.isEstado(),stringDate,cobro.getMonto(),
                                cobro.getContribuyenteServicio().getContribuyente().getNombre(),getServicio(cobro.getContribuyenteServicio().getServicio().getTipoServicio()),
                                cobro.getContribuyenteServicio().getServicio().getId()));
                    }
                    tbCobrosGenerados.setItems(cobrosGenerados);
                }
            }
        });
    }

    private String getServicio(String servicio){

        if(Objects.equals(servicio, "1")){
            return "Ruta de buses";

        } else if(Objects.equals(servicio, "2")){
            return "Parques y ornatos";

        }else if(Objects.equals(servicio, "3")){
            return "Limpieza de vias";

        }else if(Objects.equals(servicio, "4")){
            return "Derechos de cementerio";

        }
        else{
            return " ";
        }
    }

    public void onActionGenerarExcel(ActionEvent actionEvent) {


        //Blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook();

        //Create a blank sheet
        XSSFSheet sheet = workbook.createSheet("Employee Data");

        //This data needs to be written (Object[])
        Map<String, Object[]> data = new TreeMap<String, Object[]>();
        data.put("1", new Object[]{"Id", "Estado", "Fecha Cobro","Monto","Contribuyente","Servicio","Servicio id"});
        for(int i = 0; i < cobrosGenerados.size(); i++){
            data.put(String.valueOf(i+2), new Object[]{cobrosGenerados.get(i).getCl_id(),String.valueOf(cobrosGenerados.get(i).isCl_Estado()), cobrosGenerados.get(i).getCl_FechaCobro(),
            cobrosGenerados.get(i).getCl_Monto(),cobrosGenerados.get(i).getCl_Contribuyente(),cobrosGenerados.get(i).getCl_Servicio(),cobrosGenerados.get(i).getCl_servicioId()});
        }

        //Iterate over data and write to sheet
        Set<String> keyset = data.keySet();

        int rownum = 0;
        for (String key : keyset)
        {
            //create a row of excelsheet
            Row row = sheet.createRow(rownum++);

            //get object array of prerticuler key
            Object[] objArr = data.get(key);

            int cellnum = 0;

            for (Object obj : objArr)
            {
                Cell cell = row.createCell(cellnum++);
                if (obj instanceof String)
                {
                    cell.setCellValue((String) obj);
                }
                else if (obj instanceof Integer)
                {
                    cell.setCellValue((Integer) obj);
                }
                else if (obj instanceof Long)
                {
                    cell.setCellValue((Long) obj);
                }
                else if (obj instanceof Double)
                {
                    cell.setCellValue((Double) obj);
                }
            }
        }
        try
        {
            //Write the workbook in file system
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("XLSX files (*.xlsx)", "*.xlsx"),
                    new FileChooser.ExtensionFilter("XLS files (*.xls)", "*.xls"),
                    new FileChooser.ExtensionFilter("ODS files (*.ods)", "*.ods"),
                    new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv"),
                    new FileChooser.ExtensionFilter("HTML files (*.html)", "*.html")
            );

            File saveFile = fileChooser.showSaveDialog(tbCobrosGenerados.getScene().getWindow());
            FileOutputStream out = new FileOutputStream(saveFile.getAbsoluteFile());
            workbook.write(out);
            out.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void onActionLimpiar(ActionEvent actionEvent) {
        tbCobrosGenerados.getItems().clear();
        msg.show(Alert.AlertType.INFORMATION, "", "Registros limpiados con éxito");
    }
}
