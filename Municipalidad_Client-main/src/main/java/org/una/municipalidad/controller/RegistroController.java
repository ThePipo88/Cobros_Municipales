package org.una.municipalidad.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.una.municipalidad.data.CantidadSalidas;
import org.una.municipalidad.dto.*;
import org.una.municipalidad.service.*;
import org.una.municipalidad.util.AppContext;
import org.una.municipalidad.util.Mensaje;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class RegistroController extends Controller{

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
    public ComboBox cbServicio;
    @FXML
    public VBox vbPropiedades;
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
    public JFXTextField txtNombreRutas;
    @FXML
    public JFXTextField txtInicioBus;
    @FXML
    public JFXTextField txtFinBus;
    @FXML
    public JFXTextField txtDia;
    @FXML
    public JFXTextField txtCantidad;
    @FXML
    public TableView<CantidadSalidas> tvListaSalida;
    @FXML
    public JFXButton btnAgregarListaSalida;
    @FXML
    public VBox vbCementerios;
    @FXML
    public JFXTextField txtSectorC;
    @FXML
    public JFXTextField txtOcupadoC;
    @FXML
    public ComboBox cbTipoDerecho;
    @FXML
    public JFXButton btnRegistrarContribuyete;
    @FXML
    public JFXButton btnRegistrarServicio;
    @FXML
    public VBox vbRutaBuses;
    @FXML
    public JFXTextField txtTipoServicio;
    @FXML
    public JFXTextField txtDescripcion;
    @FXML
    public JFXTextField txtEstado;
    @FXML
    public TableColumn cl_Cantidad;
    @FXML
    public TableColumn cl_Dia;
    @FXML
    public ComboBox cbDia;
    @FXML
    public ComboBox cbZona;
    @FXML
    public ComboBox cbDistritos;
    @FXML
    public ComboBox cbOcupado;

    private Mensaje mensaje = new Mensaje();

    @Override
    public void initialize() {

        cbServicio.getItems().add("Ruta de buses");
        cbServicio.getItems().add("Parques y Ornatos");
        cbServicio.getItems().add("Limpieza de vías");
        cbServicio.getItems().add("Derechos de cementerio");

        cbDia.getItems().add("Lunes");
        cbDia.getItems().add("Martes");
        cbDia.getItems().add("Miercoles");
        cbDia.getItems().add("Jueves");
        cbDia.getItems().add("Viernes");
        cbDia.getItems().add("Sábado");
        cbDia.getItems().add("Domingo");

        cbZona.getItems().add("Comercial");
        cbZona.getItems().add("Residecial");
        cbZona.getItems().add("Industrial");

        cbDistritos.getItems().add("1");
        cbDistritos.getItems().add("2");
        cbDistritos.getItems().add("3");
        cbDistritos.getItems().add("4");
        cbDistritos.getItems().add("5");

        cbTipoDerecho.getItems().add("1. 1 niño: 20000");
        cbTipoDerecho.getItems().add("2. 1 niño: 25000");
        cbTipoDerecho.getItems().add("3. 2 personas: 40000");
        cbTipoDerecho.getItems().add("4. 4 personas: 60000");

        cbOcupado.getItems().add("Ocupado");
        cbOcupado.getItems().add("Desocupado");

        this.cl_Dia.setCellValueFactory(new PropertyValueFactory("dia"));
        this.cl_Cantidad.setCellValueFactory(new PropertyValueFactory("cantidad"));
    }

    public void btnAgregarListaSalida(ActionEvent actionEvent) {
            tvListaSalida.getItems().add(new CantidadSalidas(getDia((String)cbDia.getSelectionModel().getSelectedItem()),Integer.parseInt(txtCantidad.getText())));
            txtCantidad.setText("");
    }

    public void onActionRegistrarContribuyente(ActionEvent actionEvent) throws ExecutionException, InterruptedException, JsonProcessingException {
        if(!Objects.equals(txtCedulaCont.getText(), "")){

            Runnable rn=()-> {
                Platform.setImplicitExit(false);
                Platform.runLater(() -> {

                    ContribuyenteDTO contribuyenteDTO = new ContribuyenteDTO();
                    contribuyenteDTO.setNombre(txtNombreCont.getText());
                    contribuyenteDTO.setTelefono(txtTelefonoCont.getText());
                    contribuyenteDTO.setCedula(txtCedulaCont.getText());
                    contribuyenteDTO.setCorreoElectronico(txtCorreoCont.getText());
                    contribuyenteDTO.setDireccion(txtDireccionCont.getText());
                    LocalDate Ldate = dateNacimientoCont.getValue();
                    Date date = Date.from(Ldate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                    contribuyenteDTO.setFechaNacimiento(date);

                    try {
                        ContribuyenteService.createContribuyente(contribuyenteDTO);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }

                    txtCedulaCont.setText("");
                    txtNombreCont.setText("");
                    txtTelefonoCont.setText("");
                    txtCedulaCont.setText("");
                    txtCorreoCont.setText("");
                    txtDireccionCont.setText("");

                });
            };

            Thread t=new Thread(rn);
            t.start();

            mensaje.show(Alert.AlertType.INFORMATION, "", "Se ha registrado el contribuyente con éxito");


        }
        else{

        }
    }

    public void onActionRegistrarServicio(ActionEvent actionEvent) throws ExecutionException, InterruptedException, JsonProcessingException {
        String dato = (String) cbServicio.getSelectionModel().getSelectedItem();

        Runnable rn=()->{
            Platform.setImplicitExit(false);
            Platform.runLater(()-> {
                if(dato.equals("Ruta de buses")){
                    ServicioDTO servicioDTO = new ServicioDTO();
                    servicioDTO.setTipoServicio(getTipo("Ruta de buses"));
                    servicioDTO.setDescripcion(txtDescripcion.getText());
                    servicioDTO.setPorcentaje(100);
                    servicioDTO.setEstado(true);
                    ServicioDTO newServ = null;
                    try {
                        newServ = ServicioService.createServicio(servicioDTO);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }

                    RutaBusDTO rutaBusDTO = new RutaBusDTO();
                    rutaBusDTO.setFin(txtFinBus.getText());
                    rutaBusDTO.setInicio(txtInicioBus.getText());
                    rutaBusDTO.setNombre(txtNombreRutas.getText());
                    rutaBusDTO.setServicio(newServ);
                    RutaBusDTO ruta = null;
                    try {
                        ruta = RutaBusService.createRutaBus(rutaBusDTO);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }

                    ObservableList<CantidadSalidas> observableList = FXCollections.observableArrayList();
                    observableList = tvListaSalida.getItems();

                    if(observableList.size() != 0){
                        for(int i = 0; i < observableList.size(); i++){
                            ListaSalidaDTO salida = new ListaSalidaDTO();
                            salida.setCantidad(observableList.get(i).getCantidad());
                            salida.setDia(observableList.get(i).getDia());
                            salida.setRutaBus(ruta);
                            try {
                                ListaSalidaService.createListaSalida(salida);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    tvListaSalida.getItems().clear();
                    txtFinBus.setText("");
                    txtInicioBus.setText("");
                    txtDescripcion.setText("");
                    txtNombreRutas.setText("");
                    cbDia.getSelectionModel().clearSelection();

                    txtDirrecionP.setText("");
                    txtEstado.setText("");
                    txtTipoServicio.setText("");

                }else if(dato.equals("Parques y Ornatos") || dato.equals("Limpieza de vías")){
                    ServicioDTO servicioDTO = new ServicioDTO();
                    servicioDTO.setTipoServicio(getTipo("Parques y Ornatos"));
                    servicioDTO.setDescripcion(txtDescripcion.getText());
                    servicioDTO.setPorcentaje(100);
                    servicioDTO.setEstado(true);
                    ServicioDTO newServ = null;
                    try {
                        newServ = ServicioService.createServicio(servicioDTO);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }

                    PropiedadDTO propiedadDTO = new PropiedadDTO();
                    propiedadDTO.setMetrosFrente(Double.parseDouble(txtMetrosP.getText()));
                    propiedadDTO.setZona(getZona((String)cbZona.getSelectionModel().getSelectedItem()));
                    propiedadDTO.setProvincia(txtProvinciaP.getText());
                    propiedadDTO.setDireccion(txtDirrecionP.getText());
                    propiedadDTO.setDistrito((String)cbDistritos.getSelectionModel().getSelectedItem());
                    propiedadDTO.setCanton(txtCantonP.getText());
                    propiedadDTO.setValorTerreno(txtValorTerreoP.getText());
                    PropiedadDTO newPropiedadd = null;
                    try {
                        newPropiedadd = PropiedadService.createPropiedad(propiedadDTO);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }

                    ServicioPropiedadDTO servicioPropiedadDTO = new ServicioPropiedadDTO();
                    servicioPropiedadDTO.setServicio(newServ);
                    servicioPropiedadDTO.setPropiedad(newPropiedadd);

                    try {
                        ServicioPropiedadService.createServicioPropiedad(servicioPropiedadDTO);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }

                    txtMetrosP.setText("");
                    txtProvinciaP.setText("");
                    txtDescripcion.setText("");
                    txtCantonP.setText("");
                    txtValorTerreoP.setText("");
                    cbDistritos.getSelectionModel().clearSelection();
                    cbZona.getSelectionModel().clearSelection();

                    txtDirrecionP.setText("");
                    txtEstado.setText("");
                    txtTipoServicio.setText("");

                }else if(dato.equals("Derechos de cementerio")){
                    ServicioDTO servicioDTO = new ServicioDTO();
                    servicioDTO.setTipoServicio(getTipo("Derechos de cementerio"));
                    servicioDTO.setDescripcion(txtDescripcion.getText());
                    servicioDTO.setPorcentaje(100);
                    servicioDTO.setEstado(true);
                    ServicioDTO newServ = null;
                    try {
                        newServ = ServicioService.createServicio(servicioDTO);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }

                    CementerioDTO cementerioDTO = new CementerioDTO();
                    cementerioDTO.setSector(txtSectorC.getText());
                    if(cbOcupado.getSelectionModel().getSelectedItem().equals("Ocupado")){
                        cementerioDTO.setOcupado("1");
                    }else{
                        cementerioDTO.setOcupado("0");
                    }
                    cementerioDTO.setServicio(newServ);
                    CementerioDTO cementerio = null;
                    try {
                        cementerio = DerechoCementerioService.createCementerio(cementerioDTO);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }

                    TipoDerechoDTO tipoDerechoDTO = getTipoDerecho((String)cbTipoDerecho.getSelectionModel().getSelectedItem());
                    tipoDerechoDTO.setCementerio(cementerio);
                    try {
                        TipoDerechoService.createTipoDerecho(tipoDerechoDTO);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }

                    txtDirrecionP.setText("");
                    txtEstado.setText("");
                    txtTipoServicio.setText("");
                    txtSectorC.setText("");
                    cbOcupado.getSelectionModel().clearSelection();
                    cbTipoDerecho.getSelectionModel().clearSelection();
                }

            });
        };

        Thread t=new Thread(rn);
        t.start();

        mensaje.show(Alert.AlertType.INFORMATION, "", "Se ha registrado el servicio con éxito");

    }

    private String getTipo(String tipo){

        if(Objects.equals(tipo, "Ruta de buses")){
            return "1";
        }else if(Objects.equals(tipo, "Parques y Ornatos")){
            return "2";
        }else if(Objects.equals(tipo, "Limpieza de vías")){
            return "3";
        }else if(Objects.equals(tipo, "Derechos de cementerio")){
            return "4";
        }
        else{
            return "";
        }
    }

    private String getDia(String tipo){

        if(Objects.equals(tipo, "Lunes")){
            return "1";
        }else if(Objects.equals(tipo, "Martes")){
            return "2";
        }else if(Objects.equals(tipo, "Miercoles")){
            return "3";
        }else if(Objects.equals(tipo, "Jueves")){
            return "4";
        }else if(Objects.equals(tipo, "Viernes")){
            return "5";
        }else if(Objects.equals(tipo, "Sábado")){
            return "6";
        }else if(Objects.equals(tipo, "Domingo")){
            return "7";
        }
        else{
            return "";
        }
    }

    private String getZona(String tipo){

        if(Objects.equals(tipo, "Comercial")){
            return "1";
        }else if(Objects.equals(tipo, "Residecial")){
            return "2";
        }else if(Objects.equals(tipo, "Industrial")){
            return "3";
        }
        else{
            return "";
        }
    }

    private TipoDerechoDTO getTipoDerecho(String tipo){

        TipoDerechoDTO tipoDerechoDTO = new TipoDerechoDTO();
        if(Objects.equals(tipo, "1. 1 niño: 20000")){
            tipoDerechoDTO.setTipo("1");
            tipoDerechoDTO.setMonto(Integer.parseInt("20000"));
            return tipoDerechoDTO;
        }else if(Objects.equals(tipo, "2. 1 niño: 25000")){
            tipoDerechoDTO.setTipo("2");
            tipoDerechoDTO.setMonto(Integer.parseInt("25000"));
            return tipoDerechoDTO;
        }else if(Objects.equals(tipo, "3. 2 personas: 40000")){
            tipoDerechoDTO.setTipo("3");
            tipoDerechoDTO.setMonto(Integer.parseInt("40000"));
            return tipoDerechoDTO;
        }
        else if(Objects.equals(tipo, "4. 4 personas: 60000")){
            tipoDerechoDTO.setTipo("4");
            tipoDerechoDTO.setMonto(Integer.parseInt("60000"));
            return tipoDerechoDTO;
        }
        else{
            return tipoDerechoDTO;
        }
    }

    public void onActionServicios(ActionEvent actionEvent) {
        String dato = (String) cbServicio.getSelectionModel().getSelectedItem();

        if(dato.equals("Ruta de buses")){
            txtTipoServicio.setText("1");
            txtEstado.setText("true");
            vbCementerios.setVisible(false);
            vbPropiedades.setVisible(false);
            vbRutaBuses.setVisible(true);
        }else if(dato.equals("Parques y Ornatos")){
            txtTipoServicio.setText("2");
            txtEstado.setText("true");
            vbCementerios.setVisible(false);
            vbPropiedades.setVisible(true);
            vbRutaBuses.setVisible(false);
        }else if(dato.equals("Limpieza de vías")){
            txtTipoServicio.setText("3");
            txtEstado.setText("true");
            vbCementerios.setVisible(false);
            vbPropiedades.setVisible(true);
            vbRutaBuses.setVisible(false);
        }else if(dato.equals("Derechos de cementerio")){
            txtTipoServicio.setText("4");
            txtEstado.setText("true");
            vbCementerios.setVisible(true);
            vbPropiedades.setVisible(false);
            vbRutaBuses.setVisible(false);
        }
    }

    public void onActionDia(ActionEvent actionEvent) {
    }

    public void oActionZona(ActionEvent actionEvent) {
    }

    public void onActionDistritoa(ActionEvent actionEvent) {
    }
}
