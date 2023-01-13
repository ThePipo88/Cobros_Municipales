package org.una.municipalidad.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import lombok.SneakyThrows;
import org.una.municipalidad.data.CobroGenerado;
import org.una.municipalidad.data.ValoresImpositivos;
import org.una.municipalidad.dto.*;
import org.una.municipalidad.service.*;
import org.una.municipalidad.util.AppContext;
import org.una.municipalidad.util.Mensaje;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class ValoresImpositivosController extends Controller{

    @FXML
    public AnchorPane apRoot;
    @FXML
    public TextField tfBuscar;
    @FXML
    public ImageView imgBuscar;
    @FXML
    public JFXButton btnBuscar;
    @FXML
    public JFXButton btnEliminar;
    @FXML
    public JFXButton btnActualizar;
    @FXML
    public JFXTextField txtTipoServicio;
    @FXML
    public JFXTextField txtIdServicio;
    @FXML
    public JFXTextField txtDireccionServicio;
    @FXML
    public JFXTextField txtEstado;
    @FXML
    public JFXTextField txtValorPropiedad;
    @FXML
    public ComboBox cbxDuenos;
    @FXML
    public JFXTextField txtNombreCot;
    @FXML
    public JFXTextField txtCedulaCont;
    @FXML
    public JFXTextField txtTelefonoCot;
    @FXML
    public JFXTextField txtCorreoCont;
    @FXML
    public JFXTextField txtDireccionCont;
    @FXML
    public JFXButton btnLimpiar;
    @FXML
    public VBox vbPropiedades;
    @FXML
    public JFXTextField txtIdP;
    @FXML
    public JFXTextField txtProvinciaP;
    @FXML
    public JFXTextField txtCantonP;
    @FXML
    public JFXTextField txtDistritoP;
    @FXML
    public JFXTextField txtDirrecionP;
    @FXML
    public JFXTextField txtValorTerreoP;
    @FXML
    public JFXTextField txtZonaP;
    @FXML
    public JFXTextField txtMetrosP;
    @FXML
    public VBox vbRutaBuses;
    @FXML
    public JFXTextField txtIdBus;
    @FXML
    public JFXTextField txtNombreRutas;
    @FXML
    public JFXTextField txtInicioBus;
    @FXML
    public JFXTextField txtFinBus;
    @FXML
    public ComboBox cbCantidadSalidas;
    @FXML
    public VBox vbCementerios;
    @FXML
    public JFXTextField txtIdC;
    @FXML
    public JFXTextField txtSectorC;
    @FXML
    public JFXTextField txtOcupadoC;
    @FXML
    public JFXTextField txtTipoDerechoC;
    @FXML
    public JFXTextField txtMontoC;
    @FXML
    public TableView<ValoresImpositivos> tvTabla;
    @FXML
    public TableColumn cl_id;
    @FXML
    public TableColumn cl_tipo;
    @FXML
    public TableColumn tl_descripcion;
    @FXML
    public TableColumn tl_fechaRegistro;
    @FXML
    public TableColumn tl_estado;
    @FXML
    public TableColumn tl_ultimaAct;
    @FXML
    public JFXButton btnEliminarValorImp;
    @FXML
    public JFXButton btnEliminarContr;
    @FXML
    public ImageView imgSpinner;

    private ObservableList<ValoresImpositivos> contribuyenteServicios = FXCollections.observableArrayList();

    private List<ContribuyenteServicioDTO> datos = null;

    private List<ServicioDTO> servicios = null;

    private Mensaje mensaje = new Mensaje();

    private String cedula;

    @Override
    public void initialize() {

        this.cl_id.setCellValueFactory(new PropertyValueFactory("cl_id"));
        this.cl_tipo.setCellValueFactory(new PropertyValueFactory("cl_tipo"));
        this.tl_descripcion.setCellValueFactory(new PropertyValueFactory("tl_descripcion"));
        this.tl_fechaRegistro.setCellValueFactory(new PropertyValueFactory("tl_fechaRegistro"));
        this.tl_estado.setCellValueFactory(new PropertyValueFactory("tl_estado"));
        this.tl_ultimaAct.setCellValueFactory(new PropertyValueFactory("tl_ultimaAct"));

        tvTabla.setEditable(true);
        this.cl_tipo.setCellFactory(TextFieldTableCell.forTableColumn());
        this.tl_estado.setCellFactory(TextFieldTableCell.forTableColumn());
        this.tl_descripcion.setCellFactory(TextFieldTableCell.forTableColumn());
        itemSeleccionado();
    }

    public void oActionBuscar(ActionEvent actionEvent) throws IOException, ExecutionException, InterruptedException {

        imgSpinner.setVisible(true);

        TranslateTransition translate = new TranslateTransition();
        translate.setDuration(Duration.millis(4000));
        translate.setAutoReverse(true);
        translate.setNode(imgSpinner);
        translate.play();

        Thread t = new Thread(){
        @SneakyThrows
        public void run(){
            datos = ContribuyenteServicioService.contribuyenteServicio();
            servicios = ServicioService.getServicios();
          }
        };

        t.start();

        translate.setOnFinished(event -> {
            imgSpinner.setVisible(false);
            contribuyenteServicios.clear();

            for(int i = 0; i < servicios.size(); i++){
                if(servicios.get(i).isEstado()){
                    contribuyenteServicios.add(new ValoresImpositivos(servicios.get(i).getId(),getTipo(servicios.get(i).getTipoServicio()),
                            servicios.get(i).getDescripcion(),String.valueOf(servicios.get(i).isEstado()),servicios.get(i).getFechaRegistro(),servicios.get(i).getUltimaActualizacion()));
                }
            }
            tvTabla.setItems(contribuyenteServicios);
        });
    }

    private void itemSeleccionado(){
        tvTabla.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @SneakyThrows
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    if(tvTabla.getSelectionModel().getSelectedIndex() >= 0){
                        cbxDuenos.getItems().clear();
                        txtNombreCot.setText("");
                        txtCedulaCont.setText("");
                        txtTelefonoCot.setText("");
                        txtCorreoCont.setText("");
                        txtDireccionCont.setText("");
                        ValoresImpositivos act = tvTabla.getSelectionModel().getSelectedItem();
                        int index = tvTabla.getSelectionModel().getSelectedIndex();
                        if(act.getCl_tipo().equals("Ruta de buses")){

                            cbCantidadSalidas.getItems().clear();
                            List<RutaBusDTO> rutaBusDTOS = RutaBusService.findByServicioId(act.getCl_id());
                            System.out.println(rutaBusDTOS.size());

                            txtNombreRutas.setText(rutaBusDTOS.get(0).getNombre());
                            txtInicioBus.setText(rutaBusDTOS.get(0).getInicio());
                            txtFinBus.setText(rutaBusDTOS.get(0).getFin());
                            List<ListaSalidaDTO> salidaDTOS = ListaSalidaService.findByRutaId(rutaBusDTOS.get(0).getId());

                            for(int i = 0; i < salidaDTOS.size(); i++){
                                cbCantidadSalidas.getItems().add("Dia: "+salidaDTOS.get(i).getDia()+" Cantidad salidas: "+salidaDTOS.get(i).getCantidad());
                            }
                            vbPropiedades.setVisible(false);
                            vbCementerios.setVisible(false);
                            vbRutaBuses.setVisible(true);

                        }
                        else if(act.getCl_tipo().equals("Derechos de cementerio")){
                            List<CementerioDTO> cementerioDTOS = DerechoCementerioService.findByDerechoId(act.getCl_id());
                            txtOcupadoC.setText(cementerioDTOS.get(0).getOcupado());
                            txtSectorC.setText(cementerioDTOS.get(0).getSector());
                            List<TipoDerechoDTO> tipoDerechoDTOS = TipoDerechoService.findByServicioId(cementerioDTOS.get(0).getId());
                            txtTipoDerechoC.setText(tipoDerechoDTOS.get(0).getTipo());
                            vbPropiedades.setVisible(false);
                            vbCementerios.setVisible(true);
                            vbRutaBuses.setVisible(false);
                        }else if(act.getCl_tipo().equals("Parques y Ornatos") || act.getCl_tipo().equals("Limpieza de vías")){
                            List<ServicioPropiedadDTO> servicioPropiedadDTOS = ServicioPropiedadService.findByServicioId(act.getCl_id());
                            txtProvinciaP.setText(servicioPropiedadDTOS.get(0).getPropiedad().getProvincia());
                            txtCantonP.setText(servicioPropiedadDTOS.get(0).getPropiedad().getCanton());
                            txtDistritoP.setText(servicioPropiedadDTOS.get(0).getPropiedad().getDistrito());
                            txtDirrecionP.setText(servicioPropiedadDTOS.get(0).getPropiedad().getDireccion());
                            txtValorTerreoP.setText(servicioPropiedadDTOS.get(0).getPropiedad().getValorTerreno());
                            txtZonaP.setText(servicioPropiedadDTOS.get(0).getPropiedad().getZona());
                            txtMetrosP.setText(servicioPropiedadDTOS.get(0).getPropiedad().getMetrosFrente().toString());
                            vbPropiedades.setVisible(true);
                            vbCementerios.setVisible(false);
                            vbRutaBuses.setVisible(false);
                        }
                        for(int i = 0; i < datos.size(); i++){
                            if(datos.get(i).getServicio().getId().equals(contribuyenteServicios.get(tvTabla.getSelectionModel().getSelectedIndex()).getCl_id())){
                                boolean enc = false;
                                for(int j = 0; j < cbxDuenos.getItems().size(); j++){
                                    if(datos.get(i).getContribuyente().getNombre().equals(cbxDuenos.getItems().get(j))){
                                        enc = true;
                                    }
                                }
                                if(!enc && datos.get(i).isEstado()){
                                    cbxDuenos.getItems().add(datos.get(i).getContribuyente().getNombre());
                                }
                                enc = false;
                            }
                        }
                    }
                }catch (Exception e){

                }
            }
        });
    }

    private Long buscarId(Long id){

        for(int i = 0; i < datos.size(); i++){
            if(datos.get(i).getServicio().getId().equals(id)){
                return datos.get(i).getServicio().getId();
            }
        }
        return 0L;
    }

    private String getTipo(String tipo){

        if(Objects.equals(tipo, "1")){
           return "Ruta de buses";
        }else if(Objects.equals(tipo, "2")){
            return "Parques y Ornatos";
        }else if(Objects.equals(tipo, "3")){
            return "Limpieza de vías";
        }else if(Objects.equals(tipo, "4")){
            return "Derechos de cementerio";
        }
        else{
            return "";
        }
    }

    public void onActionEliminar(ActionEvent actionEvent) {
        //vbPropiedades.setVisible(false);
        //vbCementerios.setVisible(true);
        //vbRutaBuses.setVisible(false);
    }

    public void onActionActualizar(ActionEvent actionEvent) throws IOException, ExecutionException, InterruptedException {
        ValoresImpositivos act = tvTabla.getItems().get(tvTabla.getSelectionModel().getSelectedIndex());

        if(act.getCl_tipo().equals("Ruta de buses")){
            System.out.println("Ruta buses encontrada");
            List<RutaBusDTO> rutaBusDTOS = RutaBusService.findByServicioId(act.getCl_id());
            rutaBusDTOS.get(0).setNombre(txtNombreRutas.getText());
            rutaBusDTOS.get(0).setInicio(txtInicioBus.getText());
            rutaBusDTOS.get(0).setFin(txtFinBus.getText());

            RutaBusService.updateRuta(rutaBusDTOS.get(0),rutaBusDTOS.get(0).getId());

        }
        else if(act.getCl_tipo().equals("Derechos de cementerio")){
            List<CementerioDTO> cementerioDTOS = DerechoCementerioService.findByDerechoId(Long.valueOf(act.getCl_id()));
            cementerioDTOS.get(0).setOcupado(txtOcupadoC.getText());
            cementerioDTOS.get(0).setSector(txtSectorC.getText());
            DerechoCementerioService.updateDerecho(cementerioDTOS.get(0),cementerioDTOS.get(0).getId());

        }else if(act.getCl_tipo().equals("Parques y Ornatos") || act.getCl_tipo().equals("Limpieza de vías")){
            List<ServicioPropiedadDTO> servicioPropiedadDTOS = ServicioPropiedadService.findByServicioId(Long.valueOf(act.getCl_id()));

            servicioPropiedadDTOS.get(0).getPropiedad().setProvincia(txtProvinciaP.getText());
            servicioPropiedadDTOS.get(0).getPropiedad().setCanton(txtCantonP.getText());
            servicioPropiedadDTOS.get(0).getPropiedad().setDistrito(txtDistritoP.getText());
            servicioPropiedadDTOS.get(0).getPropiedad().setDireccion(txtDirrecionP.getText());
            servicioPropiedadDTOS.get(0).getPropiedad().setValorTerreno(txtValorTerreoP.getText());
            servicioPropiedadDTOS.get(0).getPropiedad().setZona(txtZonaP.getText());
            servicioPropiedadDTOS.get(0).getPropiedad().setMetrosFrente(Double.valueOf(txtMetrosP.getText()));

            PropiedadService.updatePropiedad(servicioPropiedadDTOS.get(0).getPropiedad(),servicioPropiedadDTOS.get(0).getPropiedad().getId());
        }

        ContribuyenteDTO contribuyenteDTO = ContribuyenteService.getContribuyenteByCedula(cedula);

        if(cbxDuenos.getSelectionModel().getSelectedIndex() >= 0){
            int select = cbxDuenos.getSelectionModel().getSelectedIndex();
            contribuyenteDTO.setDireccion(txtDireccionCont.getText());
            contribuyenteDTO.setNombre(txtNombreCot.getText());
            contribuyenteDTO.setCorreoElectronico(txtCorreoCont.getText());
            contribuyenteDTO.setCedula(txtCedulaCont.getText());
            contribuyenteDTO.setTelefono(txtTelefonoCot.getText());
            ContribuyenteService.updateCotribuyete(contribuyenteDTO,contribuyenteDTO.getId());

            for(int i = 0; i < datos.size(); i++){
                if(datos.get(i).getContribuyente().getCedula().equals(contribuyenteDTO.getCedula())){
                    datos.get(i).getContribuyente().setDireccion(txtDireccionCont.getText());
                    datos.get(i).getContribuyente().setNombre(txtNombreCot.getText());
                    datos.get(i).getContribuyente().setCorreoElectronico(txtCorreoCont.getText());
                    datos.get(i).getContribuyente().setCedula(txtCedulaCont.getText());
                    datos.get(i).getContribuyente().setTelefono(txtTelefonoCot.getText());
                }
            }
        }

        mensaje.show(Alert.AlertType.INFORMATION, "","Datos actualizados con exito");
    }

    public void onActionLimpiar(ActionEvent actionEvent) {
    }

    public void onActionDuenos(ActionEvent actionEvent) {

           String dato = (String) cbxDuenos.getSelectionModel().getSelectedItem();

           for(int i = 0; i < datos.size(); i++){
               if(datos.get(i).getContribuyente().getNombre().equals(dato)){
                   txtNombreCot.setText(dato);
                   txtCedulaCont.setText(datos.get(i).getContribuyente().getCedula());
                   txtTelefonoCot.setText(datos.get(i).getContribuyente().getTelefono());
                   txtCorreoCont.setText(datos.get(i).getContribuyente().getCorreoElectronico());
                   txtDireccionCont.setText(datos.get(i).getContribuyente().getDireccion());
                   cedula = txtCedulaCont.getText();
               }
           }
    }

    public void onEditId(TableColumn.CellEditEvent cellEditEvent) {

    }

    public void onEditTipo(TableColumn.CellEditEvent cellEditEvent) throws IOException, ExecutionException, InterruptedException {
        ValoresImpositivos activoData = tvTabla.getSelectionModel().getSelectedItem();
        contribuyenteServicios.get(tvTabla.getSelectionModel().getSelectedIndex()).setCl_tipo((String) cellEditEvent.getNewValue());

        for(int i = 0; i < datos.size(); i++){
            if(datos.get(i).getServicio().getId().equals(contribuyenteServicios.get(tvTabla.getSelectionModel().getSelectedIndex()).getCl_id())){
                 datos.get(i).getServicio().setTipoServicio(contribuyenteServicios.get(tvTabla.getSelectionModel().getSelectedIndex()).getCl_tipo());
                 ServicioService.updateServicio(datos.get(i).getServicio(),datos.get(i).getId());
            }
        }
    }

    public void onEditDescripcion(TableColumn.CellEditEvent cellEditEvent) throws IOException, ExecutionException, InterruptedException {
        ValoresImpositivos activoData = tvTabla.getSelectionModel().getSelectedItem();
        contribuyenteServicios.get(tvTabla.getSelectionModel().getSelectedIndex()).setTl_descripcion((String) cellEditEvent.getNewValue());
        System.out.println(contribuyenteServicios.get(tvTabla.getSelectionModel().getSelectedIndex()).getTl_descripcion());

        boolean enc = false;
        for(int i = 0; i < datos.size(); i++){
            if(datos.get(i).getServicio().getId().equals(contribuyenteServicios.get(tvTabla.getSelectionModel().getSelectedIndex()).getCl_id()) && !enc){
                System.out.println("Se metio");
                datos.get(i).getServicio().setDescripcion(" ");
                ServicioService.updateServicio(datos.get(i).getServicio(),datos.get(i).getId());
                enc = true;
            }
        }
    }

    public void onEditFechaRegistro(TableColumn.CellEditEvent cellEditEvent) {

    }

    public void onEditEstado(TableColumn.CellEditEvent cellEditEvent) throws IOException, ExecutionException, InterruptedException {

        ValoresImpositivos activoData = tvTabla.getSelectionModel().getSelectedItem();
        contribuyenteServicios.get(tvTabla.getSelectionModel().getSelectedIndex()).setTl_descripcion((String) cellEditEvent.getNewValue());

        boolean enc = false;
        for(int i = 0; i < datos.size(); i++){
            if(datos.get(i).getServicio().getId().equals(contribuyenteServicios.get(tvTabla.getSelectionModel().getSelectedIndex()).getCl_id()) && !enc){
                datos.get(i).getServicio().setEstado(Boolean.parseBoolean(contribuyenteServicios.get(tvTabla.getSelectionModel().getSelectedIndex()).getTl_estado()));
                ServicioService.updateServicio(datos.get(i).getServicio(),datos.get(i).getId());
                enc = true;
            }
        }
    }

    public void onEditActualizacion(TableColumn.CellEditEvent cellEditEvent) throws IOException, ExecutionException, InterruptedException {

    }

    public void onActionEliminarValorImp(ActionEvent actionEvent) throws IOException, ExecutionException, InterruptedException {

        List<CobroGeneradoDTO> cobrosG = CobroGeneradoService.getCobrosGenerados();

        ValoresImpositivos activoData = tvTabla.getSelectionModel().getSelectedItem();

        boolean enc = false;
        if(cobrosG != null){
            for(int i = 0; i < cobrosG.size(); i++){
                if(cobrosG.get(i).getContribuyenteServicio().getServicio().getId().equals(activoData.getCl_id()) && cobrosG.get(i).isEstado()){
                    enc = true;
                }
            }
        }

        AuthenticationResponse aut = (AuthenticationResponse) AppContext.getInstance().get("Rol");

        if(!enc){

            mensaje.show(Alert.AlertType.INFORMATION, "Advertencia", "Valor impositivo eliminado correctamente");

            SolicitudPermisoDTO solicitudPermisoDTO = new SolicitudPermisoDTO();
            solicitudPermisoDTO.setIdEliminar(activoData.getCl_id());
            solicitudPermisoDTO.setPersona_solicitante(aut.getUsuarioDTO().getNombreCompleto());
            solicitudPermisoDTO.setPersona_autorizante(" ");
            solicitudPermisoDTO.setEstado(true);
            solicitudPermisoDTO.setFechaCreacion(new Date());
            solicitudPermisoDTO.setTabla("servicios");
            solicitudPermisoDTO.setAccion("Se requiere eliminar el servicio con id "+ activoData.getCl_id().toString());
            SolicitudPermisoService.createSolicitud(solicitudPermisoDTO);
            contribuyenteServicios.remove(activoData);

            tvTabla.getItems().remove(activoData);
        }else{
            mensaje.show(Alert.AlertType.ERROR, "Advertencia", "No se puede eliminar el valor impositivo, posee cobros pendientes");
        }
        enc = false;
    }

    public void onActionEliminarCont(ActionEvent actionEvent) throws IOException, ExecutionException, InterruptedException {

        List<CobroGeneradoDTO> cobrosG = CobroGeneradoService.getCobrosGenerados();

        ValoresImpositivos activoData = tvTabla.getSelectionModel().getSelectedItem();

        AuthenticationResponse aut = (AuthenticationResponse) AppContext.getInstance().get("Rol");

        ContribuyenteDTO contribuyenteDTO = ContribuyenteService.getContribuyenteByCedula(txtCedulaCont.getText());
        boolean enc = false;
        if(cobrosG != null){
            for(int i = 0; i < cobrosG.size(); i++){
                if(cobrosG.get(i).isEstado()){
                    if(cobrosG.get(i).getContribuyenteServicio().getContribuyente().getCedula().equals(txtCedulaCont.getText())){
                        enc = true;
                    }
                }
            }
        }

        if(!enc){
            for(int i = 0; i < datos.size(); i++){
                if(datos.get(i).getServicio().getId().equals(activoData.getCl_id()) && datos.get(i).getContribuyente().getCedula().equals(txtCedulaCont.getText())){
                    mensaje.show(Alert.AlertType.INFORMATION, " ", "Servicio eliminado al contribuyente con éxito");
                    SolicitudPermisoDTO solicitudPermisoDTO = new SolicitudPermisoDTO();
                    solicitudPermisoDTO.setIdEliminar(datos.get(i).getId());
                    solicitudPermisoDTO.setPersona_solicitante(aut.getUsuarioDTO().getNombreCompleto());
                    solicitudPermisoDTO.setPersona_autorizante(" ");
                    solicitudPermisoDTO.setEstado(true);
                    solicitudPermisoDTO.setFechaCreacion(new Date());
                    solicitudPermisoDTO.setTabla("contribuyente_servicio");
                    solicitudPermisoDTO.setAccion("Se requiere eliminar el contribuyente servicio con id "+datos.get(i).getId());
                    SolicitudPermisoService.createSolicitud(solicitudPermisoDTO);
                }
            }

        }else{
            mensaje.show(Alert.AlertType.ERROR, "Advertencia", "No se puede eliminar el contribuyente, posee cobros pendientes");
        }
        enc = false;
    }


}
