package org.una.municipalidad.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
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
import javafx.stage.FileChooser;
import javafx.util.Duration;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.una.municipalidad.data.CobroCancelados;
import org.una.municipalidad.dto.CobroCanceladoDTO;
import org.una.municipalidad.service.CobroCanceladoService;
import org.una.municipalidad.service.CobroGeneradoService;
import org.una.municipalidad.util.AppContext;
import org.una.municipalidad.util.Mensaje;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.*;

public class ExportarCobrosCanController extends Controller {


    @FXML
    public JFXButton btnCargarCobros;
    @FXML
    public JFXButton btnGenerarExcel;
    @FXML
    public JFXButton btnLimpiar;
    @FXML
    public TableColumn cl_id;
    @FXML
    public TableColumn cl_Nombre;
    @FXML
    public TableColumn cl_FechaCreacion;
    @FXML
    public TableColumn cl_Descripcion;
    @FXML
    public DatePicker dateInicial;
    @FXML
    public DatePicker dateFinal;
    @FXML
    public TableView<CobroCancelados> tbCancelados;
    @FXML
    public TableColumn cl_Monto;
    @FXML
    public ImageView imgSpinner;

    private static Mensaje msg = new Mensaje();

    private ObservableList<CobroCancelados> cobrosCancelados = FXCollections.observableArrayList();

    private List<CobroCanceladoDTO> cobrosC = null;

    @Override
    public void initialize() {
        this.cl_id.setCellValueFactory(new PropertyValueFactory("cl_id"));
        this.cl_Nombre.setCellValueFactory(new PropertyValueFactory("cl_Contribuyente"));
        this.cl_FechaCreacion.setCellValueFactory(new PropertyValueFactory("cl_FechaCreacion"));
        this.cl_Descripcion.setCellValueFactory(new PropertyValueFactory("cl_Descripcion"));
        this.cl_Monto.setCellValueFactory(new PropertyValueFactory("cl_Monto"));


    }

    public void onActionBtnCargarCobros(ActionEvent actionEvent) throws IOException, ExecutionException, InterruptedException {
        SimpleDateFormat DateFor = new SimpleDateFormat("yyyy-MM-dd");
        ZoneId defaultZoneId = ZoneId.systemDefault();
        LocalDate inicio = dateInicial.getValue();
        Date ini = Date.from(inicio.atStartOfDay(defaultZoneId).toInstant());
        LocalDate fin = dateFinal.getValue();
        Date f = Date.from(fin.atStartOfDay(defaultZoneId).toInstant());

        String inicioC = DateFor.format(ini);

        String finC = DateFor.format(f);

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
                    cobrosC = CobroCanceladoService.getCobrosCancelados(inicioC,finC);
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
            if (cobrosC != null) {

                for (CobroCanceladoDTO cobro : cobrosC) {
                    String stringDate = DateFor.format(cobro.getFechaCreacion());
                    cobrosCancelados.add(new CobroCancelados(cobro.getId(), cobro.getFechaCreacion().toString(), cobro.getCobroGenerado().getContribuyenteServicio().getContribuyente().getNombre(), cobro.getDescripcion(),
                            cobro.getCobroGenerado().getMonto().toString()));
                }
                tbCancelados.setItems(cobrosCancelados);
            }
        });
    }


    public String getServicio(String servicio) {

        if (Objects.equals(servicio, "1")) {
            return "Ruta de buses";

        } else if (Objects.equals(servicio, "2")) {
            return "Parques y ornatos";

        } else if (Objects.equals(servicio, "3")) {
            return "Limpieza de vias";

        } else if (Objects.equals(servicio, "4")) {
            return "Derechos de cementerio";

        } else {
            return " ";
        }


    }

    public void onActionbtnGenerarExcel(ActionEvent actionEvent) throws IOException, ExecutionException, InterruptedException {
        {
            
            //Blank workbook
            XSSFWorkbook workbook = new XSSFWorkbook();

            //Create a blank sheet
            XSSFSheet sheet = workbook.createSheet("DATOS COBROS CANCELADOS ");

            //This data needs to be written (Object[])
            Map<String, Object[]> data = new TreeMap<String, Object[]>();
            data.put("1", new Object[]{"Id", "Contribuyente", "Fecha de Creacion", "Descripcion","Monto cancelado"});
            for (int i = 0; i < cobrosCancelados.size(); i++) {
                data.put(String.valueOf(i + 2), new Object[]{cobrosCancelados.get(i).getCl_id(), cobrosCancelados.get(i).getCl_Contribuyente(),
                        cobrosCancelados.get(i).getCl_FechaCreacion(), cobrosCancelados.get(i).getCl_Descripcion(), cobrosCancelados.get(i).getCl_Monto()});
            }

            //Iterate over data and write to sheet
            Set<String> keyset = data.keySet();

            int rownum = 0;
            for (String key : keyset) {
                //create a row of excelsheet
                Row row = sheet.createRow(rownum++);

                //get object array of prerticuler key
                Object[] objArr = data.get(key);

                int cellnum = 0;

                for (Object obj : objArr) {
                    Cell cell = row.createCell(cellnum++);
                    if (obj instanceof String) {
                        cell.setCellValue((String) obj);
                    } else if (obj instanceof Integer) {
                        cell.setCellValue((Integer) obj);
                    } else if (obj instanceof Long) {
                        cell.setCellValue((Long) obj);
                    } else if (obj instanceof Double) {
                        cell.setCellValue((Double) obj);
                    }
                }
            }
            try {
                //Write the workbook in file system
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("XLSX files (*.xlsx)", "*.xlsx"),
                        new FileChooser.ExtensionFilter("XLS files (*.xls)", "*.xls"),
                        new FileChooser.ExtensionFilter("ODS files (*.ods)", "*.ods"),
                        new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv"),
                        new FileChooser.ExtensionFilter("HTML files (*.html)", "*.html")
                );

                File saveFile = fileChooser.showSaveDialog(tbCancelados.getScene().getWindow());
                FileOutputStream out = new FileOutputStream(saveFile.getAbsoluteFile());
                workbook.write(out);
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    public void onActionBtnLimpiar(ActionEvent actionEvent) {
        tbCancelados.getItems().clear();
        cobrosCancelados.clear();
    }
}