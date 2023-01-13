package org.una.municipalidad.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import org.una.municipalidad.data.DatosTabla;
import org.una.municipalidad.dto.CobroCanceladoDTO;
import org.una.municipalidad.dto.CobroGeneradoDTO;
import org.una.municipalidad.service.CobroCanceladoService;
import org.una.municipalidad.service.CobroGeneradoService;
import org.una.municipalidad.util.Mensaje;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class RealizarPagosController extends Controller{

    @FXML
    public JFXButton btnBuscar;
    @FXML
    public JFXButton btnCancelarCobro;
    @FXML
    public JFXButton btnCancelarCobroEspecifico;
    @FXML
    public JFXButton btnLimpiar;
    @FXML
    public TableView<DatosTabla> tbPagos;
    @FXML
    public TableColumn<DatosTabla,String> clCedula;
    @FXML
    public TableColumn<DatosTabla,String> clNombre;
    @FXML
    public TableColumn<DatosTabla,String> clCorreo;
    @FXML
    public TableColumn<DatosTabla,String> clTelefono;
    @FXML
    public TableColumn<DatosTabla,String> clEmisionCobro;
    @FXML
    public TableColumn<DatosTabla,Long> clIdValorImpositivo;
    @FXML
    public TableColumn<DatosTabla,Integer> clTipoServicio;
    @FXML
    public TableColumn<DatosTabla,String> clRegistro;
    @FXML
    public TableColumn<DatosTabla,Boolean> clEstado;
    @FXML
    public TableColumn<DatosTabla,String> clValorImpuesto;
    @FXML
    public TableColumn<DatosTabla,String> clDescripcion;
    @FXML
    public JFXTextField txtTotal;
    @FXML
    public TextField txtBuscarxCedula;
    @FXML
    public ImageView imgSpinner;

    private ObservableList<DatosTabla> cobrosGenerados = FXCollections.observableArrayList();

    private List<CobroGeneradoDTO> cobros = null;

    private ObservableList<CobroGeneradoDTO> cobroGeneradoDTO = FXCollections.observableArrayList();

    Mensaje msg = new Mensaje();

    private double suma = 0;

    @Override
    public void initialize() {
        tabla();
    }

    public void Buscar(ActionEvent actionEvent) throws IOException, ExecutionException, InterruptedException {
        tbPagos.getItems().clear();
        datosTabla();
    }

    public void CancelarCobro(ActionEvent actionEvent) throws IOException, ExecutionException, InterruptedException {
       for(int i = 0; i < cobroGeneradoDTO.size(); i++){
            cobroGeneradoDTO.get(i).setEstado(false);
            CobroGeneradoService.updateCobro(cobroGeneradoDTO.get(i),cobroGeneradoDTO.get(i).getId());
            CobroCanceladoDTO cobroCanceladoDTO = new CobroCanceladoDTO();
            cobroCanceladoDTO.setDescripcion("Cobro cancelado correctamente");
            cobroCanceladoDTO.setCobroGenerado(cobroGeneradoDTO.get(i));
            cobroCanceladoDTO.setFechaCreacion(new Date());
           CobroCanceladoService.createSolicitud(cobroCanceladoDTO);

        }
        for(int i = 0; i < cobrosGenerados.size(); i++){
            cobrosGenerados.get(i).setEstado(false);
        }
        tbPagos.getItems().clear();
    }

    public void Limpiar(ActionEvent actionEvent) {
        tbPagos.getItems().clear();
    }

    public void CancelarCobroEspecifico(ActionEvent actionEvent) throws IOException, ExecutionException, InterruptedException {
        for(int i = 0; i < cobroGeneradoDTO.size(); i++){
            if(cobroGeneradoDTO.get(i).getId().equals(tbPagos.getSelectionModel().getSelectedItem().getIdValorImpositivo())){
                cobroGeneradoDTO.get(i).setEstado(false);
                CobroGeneradoService.updateCobro(cobroGeneradoDTO.get(i),cobroGeneradoDTO.get(i).getId());
                CobroCanceladoDTO cobroCanceladoDTO = new CobroCanceladoDTO();
                cobroCanceladoDTO.setDescripcion("Cobro cancelado correctamente");
                cobroCanceladoDTO.setCobroGenerado(cobroGeneradoDTO.get(i));
                cobroCanceladoDTO.setFechaCreacion(new Date());
                CobroCanceladoService.createSolicitud(cobroCanceladoDTO);

                msg.show(Alert.AlertType.INFORMATION, "", "Cobro cancelado con exito");
            }
        }
        double resta = cobroGeneradoDTO.get(tbPagos.getSelectionModel().getSelectedIndex()).getMonto();
        suma = suma - resta;
        txtTotal.setText(String.valueOf(suma));
        tbPagos.getItems().remove(tbPagos.getSelectionModel().getSelectedIndex());
    }

    public void tabla(){
        this.clCedula.setCellValueFactory(new PropertyValueFactory<DatosTabla,String>("cedula"));
        this.clNombre.setCellValueFactory(new PropertyValueFactory<DatosTabla,String>("nombre"));
        this.clCorreo.setCellValueFactory(new PropertyValueFactory<DatosTabla,String>("correo"));
        this.clTelefono.setCellValueFactory(new PropertyValueFactory<DatosTabla,String>("telefono"));
        this.clEmisionCobro.setCellValueFactory(new PropertyValueFactory<DatosTabla,String>("fechaEmision"));
        this.clIdValorImpositivo.setCellValueFactory(new PropertyValueFactory<DatosTabla,Long>("idValorImpositivo"));
        this.clTipoServicio.setCellValueFactory(new PropertyValueFactory<DatosTabla,Integer>("tipoServicio"));
        this.clRegistro.setCellValueFactory(new PropertyValueFactory<DatosTabla,String>("fechaRegistro"));
        this.clEstado.setCellValueFactory(new PropertyValueFactory<DatosTabla,Boolean>("estado"));
        this.clValorImpuesto.setCellValueFactory(new PropertyValueFactory<DatosTabla,String>("valorImpuesto"));
        this.clDescripcion.setCellValueFactory(new PropertyValueFactory<DatosTabla,String>("descripcion"));
    }

    public void datosTabla() throws IOException, ExecutionException, InterruptedException {

         cobros = CobroGeneradoService.findCobroByCedula(txtBuscarxCedula.getText());
            if(cobros != null && cobros.size() > 0) {
                    for (CobroGeneradoDTO cobro : cobros) {
                        suma = suma + cobro.getMonto();
                        cobroGeneradoDTO.add(cobro);
                        cobrosGenerados.add(new DatosTabla(cobro.getContribuyenteServicio().getContribuyente().getCedula(),cobro.getContribuyenteServicio().getContribuyente().getNombre(),
                                cobro.getContribuyenteServicio().getContribuyente().getCorreoElectronico(),cobro.getContribuyenteServicio().getContribuyente().getTelefono(),
                                cobro.getFechaCobro(),cobro.getId(),cobro.getContribuyenteServicio().getServicio().getTipoServicio(),
                                cobro.getContribuyenteServicio().getServicio().getFechaRegistro().toString(),cobro.getContribuyenteServicio().getServicio().isEstado(),
                                cobro.getMonto().toString(),cobro.getContribuyenteServicio().getServicio().getDescripcion()));
                    }
                }else msg.show(Alert.AlertType.INFORMATION, "Hubo un problema","Cedula no existe o esta mal digitada");
        this.tbPagos.setItems(cobrosGenerados);
            txtTotal.setText(String.valueOf(suma));
    }
}

