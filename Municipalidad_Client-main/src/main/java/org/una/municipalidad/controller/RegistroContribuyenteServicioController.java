package org.una.municipalidad.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.una.municipalidad.data.ContribuyenteServicio;
import org.una.municipalidad.data.ValoresImpositivos;
import org.una.municipalidad.dto.ContribuyenteDTO;
import org.una.municipalidad.dto.ContribuyenteServicioDTO;
import org.una.municipalidad.dto.ServicioDTO;
import org.una.municipalidad.service.ContribuyenteService;
import org.una.municipalidad.service.ContribuyenteServicioService;
import org.una.municipalidad.service.ServicioService;
import org.una.municipalidad.util.Mensaje;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class RegistroContribuyenteServicioController extends Controller{

    @FXML
    public JFXTextField txtNombreCont;
    @FXML
    public JFXTextField txtCedulaCont;
    @FXML
    public JFXTextField txtCorreoCont;
    @FXML
    public JFXTextField txtDireccionCont;
    @FXML
    public DatePicker dateNacimientoCont;
    @FXML
    public JFXTextField txtTelefonoCont;
    @FXML
    public TableView<ContribuyenteServicio> tvValoresImpositivos;
    @FXML
    public TableColumn tc_Id;
    @FXML
    public TableColumn tc_Nombre;
    @FXML
    public TableColumn tc_Tipo;
    @FXML
    public TableColumn tc_Descripcion;
    @FXML
    public TableColumn tc_FechaCreacion;
    @FXML
    public JFXTextField txtUsuarioSeleccionado;
    @FXML
    public JFXTextField txtIdServicio;
    @FXML
    public TableColumn tc_Actualizacion;
    @FXML
    public JFXButton btnAsignarValorUsuario;
    @FXML
    public JFXTextField txtPorcentaje;
    @FXML
    public TableColumn tc_Porcentaje;

    private ObservableList<ContribuyenteServicio> contribuyenteServicios = FXCollections.observableArrayList();

    private Mensaje mensaje = new Mensaje();

    private ContribuyenteDTO contribuyenteDTO = null;

    List<ServicioDTO> contribuyenteDTOS = null;

    Mensaje msg = new Mensaje();

    @Override
    public void initialize() throws IOException, ExecutionException, InterruptedException {

        this.tc_Id.setCellValueFactory(new PropertyValueFactory("tc_Id"));
        this.tc_Actualizacion.setCellValueFactory(new PropertyValueFactory("tc_Actualizacion"));
        this.tc_Tipo.setCellValueFactory(new PropertyValueFactory("tc_Tipo"));
        this.tc_Descripcion.setCellValueFactory(new PropertyValueFactory("tc_Descripcion"));
        this.tc_Porcentaje.setCellValueFactory(new PropertyValueFactory("tc_Porcentaje"));
        this.tc_FechaCreacion.setCellValueFactory(new PropertyValueFactory("tc_FechaCreacion"));

        txtIdServicio.setText("");
        txtUsuarioSeleccionado.setText("");
        txtPorcentaje.setText("");

        contribuyenteDTOS = ServicioService.getServicios();

        for(int i = 0; i < contribuyenteDTOS.size(); i++){
            if(contribuyenteDTOS.get(i).isEstado()){
                contribuyenteServicios.add(new ContribuyenteServicio(contribuyenteDTOS.get(i).getId(), contribuyenteDTOS.get(i).getTipoServicio(),contribuyenteDTOS.get(i).getDescripcion(),
                        contribuyenteDTOS.get(i).getFechaRegistro(),contribuyenteDTOS.get(i).getUltimaActualizacion(),contribuyenteDTOS.get(i).getPorcentaje()));
            }
        }
        tvValoresImpositivos.setItems(contribuyenteServicios);
    }

    public void onActionKeyPressed(KeyEvent keyEvent) throws IOException, ExecutionException, InterruptedException {
        if(keyEvent.getCode() == KeyCode.ENTER){
            contribuyenteDTO = ContribuyenteService.getContribuyenteByCedula(txtCedulaCont.getText());
            if(contribuyenteDTO != null){
                txtNombreCont.setText(contribuyenteDTO.getNombre());
                txtCorreoCont.setText(contribuyenteDTO.getCorreoElectronico());
                txtDireccionCont.setText(contribuyenteDTO.getDireccion());
                txtTelefonoCont.setText(contribuyenteDTO.getTelefono());
                dateNacimientoCont.setValue(convertToLocalDateViaSqlDate(contribuyenteDTO.getFechaNacimiento()));
                txtUsuarioSeleccionado.setText(contribuyenteDTO.getCedula());
            }
        }
    }

    public LocalDate convertToLocalDateViaSqlDate(Date dateToConvert) {
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }

    public void onMouseClicked(MouseEvent mouseEvent) {
        ContribuyenteServicio contribuyenteServicio = tvValoresImpositivos.getSelectionModel().getSelectedItem();

        if(tvValoresImpositivos.getSelectionModel().getSelectedIndex() > 0){
            txtIdServicio.setText(contribuyenteServicio.getTc_Id().toString());
        }
    }

    public void onActionAsignarValor(ActionEvent actionEvent) throws ExecutionException, InterruptedException, IOException {
        if(!Objects.equals(txtUsuarioSeleccionado.getText(), "") && !Objects.equals(txtIdServicio.getText(), "") && !Objects.equals(txtPorcentaje.getText(), "")){

            ContribuyenteServicio contribuyenteServicio = tvValoresImpositivos.getSelectionModel().getSelectedItem();

            if(contribuyenteServicio.getTc_Porcentaje() >= Float.parseFloat(txtPorcentaje.getText())){

                ContribuyenteServicioDTO contribuyenteServicioDTO = new ContribuyenteServicioDTO();
                boolean enc = false;
                for(int i = 0; i < contribuyenteDTOS.size(); i++){
                    if(!enc){
                        if(contribuyenteDTOS.get(i).getId() == Long.parseLong(txtIdServicio.getText())){
                            contribuyenteServicioDTO.setContribuyente(contribuyenteDTO);
                            contribuyenteServicioDTO.setServicio(contribuyenteDTOS.get(i));
                            contribuyenteServicioDTO.setPorcentaje(Float.parseFloat(txtPorcentaje.getText()));
                            contribuyenteServicioDTO.setEstado(true);
                            ContribuyenteServicioService.createContribuyenteServicio(contribuyenteServicioDTO);

                            ServicioDTO servicioDTO = ServicioService.getServicioId(contribuyenteServicio.getTc_Id().toString());
                            float porcentaje = servicioDTO.getPorcentaje();
                            porcentaje = porcentaje - Float.parseFloat(txtPorcentaje.getText());
                            servicioDTO.setPorcentaje(porcentaje);
                            ServicioService.updateServicio(servicioDTO,servicioDTO.getId());

                            tvValoresImpositivos.getSelectionModel().getSelectedItem().setTc_Porcentaje(porcentaje);
                            tvValoresImpositivos.refresh();

                            txtIdServicio.setText("");
                            txtUsuarioSeleccionado.setText("");
                            txtPorcentaje.setText("");

                            mensaje.show(Alert.AlertType.INFORMATION, "", "Se ha registrado el servicio al contribuyente con éxito");
                            enc = true;
                        }
                    }
                }

                txtNombreCont.setText("");
                txtCorreoCont.setText("");
                txtDireccionCont.setText("");
                txtTelefonoCont.setText("");
                //dateNacimientoCont.setValue(convertToLocalDateViaSqlDate(contribuyenteDTO.getFechaNacimiento()));
                txtUsuarioSeleccionado.setText("");
            }
            else {
                mensaje.show(Alert.AlertType.ERROR, "Advertencia", "El porcentaje ingresado es mayor al disponible en el servicio");
            }
        }else{
            mensaje.show(Alert.AlertType.ERROR, "Advertencia", "Faltan datos por ingresar");
        }
    }


}
