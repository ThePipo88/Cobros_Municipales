package org.una.municipalidad.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.jfoenix.controls.JFXTextField;


import com.jfoenix.controls.JFXTextField;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import org.una.municipalidad.data.Parametros;
import org.una.municipalidad.dto.ParametroDTO;
import org.una.municipalidad.service.ParametroService;
import org.una.municipalidad.util.Mensaje;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class ParametrosController extends Controller {



    @FXML
    public JFXButton btnCrearParametros;
    @FXML
    public JFXButton btnCargarParametros;
    @FXML
    public JFXButton btnLimpiar;
    @FXML
    public JFXTextField txtFecha_Creacion;
    @FXML
    public JFXTextField txtFecha_Modificacion;
    @FXML
    public JFXButton btnDescartar;
    @FXML
    public JFXTextField txtparametro_ID;
    @FXML
    public JFXTextField txtparametro_Nombre;
    @FXML
    public JFXTextField txtParemetro_objeto;
    @FXML
    public JFXTextField txtParametro_estado;
    @FXML
    public TableView<Parametros> tbParametros;
    @FXML
    public TableColumn cl_Id;
    @FXML
    public TableColumn cl_Nombre;
    @FXML
    public TableColumn cl_FechaCreacion;
    @FXML
    public TableColumn cl_FechaModificacion;
    @FXML
    public TableColumn cl_Objeto;
    @FXML
    public TableColumn cl_Estado;
    @FXML
    public JFXButton btnActualizarParametros;
    @FXML
    public ImageView imgSpinner;

    private static Mensaje msg = new Mensaje();
    private int j = 0;
    private ObservableList<Parametros> parametros = FXCollections.observableArrayList();
    private List<ParametroDTO> parametro = null;

    @Override
    public void initialize() {
        this.cl_Id.setCellValueFactory(new PropertyValueFactory("cl_Id"));
        this.cl_Objeto.setCellValueFactory(new PropertyValueFactory("cl_Objeto"));
        this.cl_Nombre.setCellValueFactory(new PropertyValueFactory("cl_Nombre"));
        this.cl_Estado.setCellValueFactory(new PropertyValueFactory("cl_Estado"));
        this.cl_FechaCreacion.setCellValueFactory(new PropertyValueFactory("cl_FechaCreacion"));
        this.cl_FechaModificacion.setCellValueFactory(new PropertyValueFactory("cl_FechaModificacion"));


      //  parametros.add(new Parametros("", "", "", "", "", ""));
        this.tbParametros.setItems(parametros);
        tbParametros.setEditable(true);

    }

    public void onActionBtnCargarParametros(ActionEvent actionEvent) throws IOException, ExecutionException, InterruptedException {


        imgSpinner.setVisible(true);

        TranslateTransition translate = new TranslateTransition();
        translate.setDuration(Duration.millis(2000));
        translate.setAutoReverse(true);
        translate.setNode(imgSpinner);
        translate.play();

        Runnable rn=()-> {
            Platform.setImplicitExit(false);
            Platform.runLater(() -> {
                try {
                    parametro = ParametroService.getParametros();
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
            if (parametro != null && parametro.size()!=0) {
                for (ParametroDTO parametroDTO : parametro) {
                    if(parametroDTO.isEstado()){
                        SimpleDateFormat DateFor = new SimpleDateFormat("yyyy/MM/dd");
                        String stringDate = DateFor.format(parametroDTO.getFechaModificacion());
                        String stringDat = DateFor.format(parametroDTO.getFechaCreacion());
                        parametros.add(new Parametros(parametroDTO.getId().toString(),parametroDTO.isEstado(),stringDat,stringDate, parametroDTO.getFormula(),parametroDTO.getNombre()));
                    }
                }

                tbParametros.setItems(parametros);
            }
        });


    }

    private void editarTexto(TableColumn<Parametros, String> columna, String texto) {

        columna.setCellFactory(column -> new TableCell<Parametros, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                if (item != null) {
                    if (j == tbParametros.getSelectionModel().getSelectedIndex()) {
                        setText(texto);
                    }
                    j++;
                    super.updateItem(item, empty);
                    setText(empty ? null : item);
                }
            }
        });
    }

    public void onActionBtnActualizarParametros(ActionEvent actionEvent) throws IOException, ExecutionException, InterruptedException {


        Parametros parametro = tbParametros.getSelectionModel().getSelectedItem();

        ParametroDTO parametroDTO = ParametroService.getParametroById(Long.parseLong(parametro.getCl_Id()));

        parametroDTO.setEstado(true);
        parametroDTO.setFormula(txtParemetro_objeto.getText());
        parametroDTO.setFechaModificacion(new Date());
        parametroDTO.setNombre(txtparametro_Nombre.getText());
        ParametroService.updateParametro(parametroDTO,parametroDTO.getId());

        int index = tbParametros.getSelectionModel().getSelectedIndex();
        tbParametros.getItems().get(index).setCl_Estado(Boolean.parseBoolean(txtParametro_estado.getText()));
        tbParametros.getItems().get(index).setCl_Nombre(txtparametro_Nombre.getText());
        tbParametros.getItems().get(index).setCl_FechaModificacion(new Date().toString());
        tbParametros.getItems().get(index).setCl_Objeto(txtParemetro_objeto.getText());
        ParametroService.updateParametro(parametroDTO,parametroDTO.getId());
        tbParametros.refresh();

        msg.show(Alert.AlertType.INFORMATION, "", "Parametro actualizado con exito");

    }

    public void onActionBtnCrearParametros(ActionEvent actionEvent) throws ParseException, InterruptedException, ExecutionException, JsonProcessingException {

        ParametroDTO newParametro = new ParametroDTO();

        newParametro.setEstado(true);
        newParametro.setFormula(txtParemetro_objeto.getText());
        newParametro.setFechaModificacion(new Date());
        newParametro.setFechaCreacion(new Date());
        newParametro.setNombre(txtparametro_Nombre.getText());

        ParametroService.createParametro(newParametro);

        msg.show(Alert.AlertType.INFORMATION, "", "Parametro creado con exito");
    }

    public void onActionBtnLimpiar(ActionEvent actionEvent) {

        tbParametros.getItems().clear();
        msg.show(Alert.AlertType.INFORMATION, "", "Registros limpiados con éxito");
    }

    public void onActionDescartar(ActionEvent actionEvent) throws IOException, ExecutionException, InterruptedException {

        Parametros parametros = tbParametros.getSelectionModel().getSelectedItem();
        ParametroService.deleteParametro(parametros.getCl_Id());
        tbParametros.getItems().remove(tbParametros.getSelectionModel().getSelectedIndex());
        msg.show(Alert.AlertType.INFORMATION, "", "Parametro eliminado con exito");
    }

    public void onMouseClicked(MouseEvent mouseEvent) {

        Parametros parametros = tbParametros.getSelectionModel().getSelectedItem();

        if(tbParametros.getSelectionModel().getSelectedIndex() > 0){
            txtparametro_ID.setText(parametros.getCl_Id());
            txtFecha_Modificacion.setText(parametros.getCl_FechaModificacion());
            txtparametro_Nombre.setText(parametros.getCl_Nombre());
            txtParametro_estado.setText(String.valueOf(parametros.isCl_Estado()));
            txtFecha_Creacion.setText(parametros.getCl_FechaCreacion());
            txtParemetro_objeto.setText(parametros.getCl_Objeto());
        }
    }
}



