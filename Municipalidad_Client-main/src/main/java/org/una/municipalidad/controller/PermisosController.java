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
import org.una.municipalidad.data.CobroGenerado;
import org.una.municipalidad.data.Parametros;
import org.una.municipalidad.data.Permiso;
import org.una.municipalidad.dto.*;
import org.una.municipalidad.service.*;
import org.una.municipalidad.util.AppContext;
import org.una.municipalidad.util.Mensaje;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class PermisosController extends Controller{

    @FXML
    public TableView<Permiso> tvSolicitudes;
    @FXML
    public TableColumn tc_Id;
    @FXML
    public TableColumn tc_PersonaSolicitante;
    @FXML
    public TableColumn tc_PersonaAutorizante;
    @FXML
    public TableColumn tc_Estado;
    @FXML
    public TableColumn tc_FechaCreacion;
    @FXML
    public JFXButton btnPermisoSeleccionado;
    @FXML
    public JFXButton onActionConcederTodosPermisos;
    @FXML
    public JFXButton btnCargarPermisos;
    @FXML
    public TableColumn tc_Accion;
    @FXML
    public ImageView imgSpinner;

    private ObservableList<Permiso> solicitudPermisos = FXCollections.observableArrayList();

    private ObservableList<SolicitudPermisoDTO> permisoDTOS = FXCollections.observableArrayList();

    private static Mensaje msg = new Mensaje();

    private AuthenticationResponse authenticationResponse;

    private List<SolicitudPermisoDTO> permisos = null;

    @Override
    public void initialize() {

        authenticationResponse = (AuthenticationResponse) AppContext.getInstance().get("Rol");

        this.tc_Id.setCellValueFactory(new PropertyValueFactory("tc_Id"));
        this.tc_PersonaSolicitante.setCellValueFactory(new PropertyValueFactory("tc_PersonaSolicitante"));
        this.tc_PersonaAutorizante.setCellValueFactory(new PropertyValueFactory("tc_PersonaAutorizante"));
        this.tc_Accion.setCellValueFactory(new PropertyValueFactory("tc_Accion"));
        this.tc_Estado.setCellValueFactory(new PropertyValueFactory("tc_Estado"));
        this.tc_FechaCreacion.setCellValueFactory(new PropertyValueFactory("tc_FechaCreacion"));

    }

    public void onActionPermisoSeleccionado(ActionEvent actionEvent) throws IOException, ExecutionException, InterruptedException {
        int index = tvSolicitudes.getSelectionModel().getSelectedIndex();
        concederPermiso(permisoDTOS.get(index).getTabla(),permisoDTOS.get(index).getIdEliminar(), index);
    }

    public void oActionCocederTodosPermisos(ActionEvent actionEvent) {
    }

    public void onActionCargarPermiso(ActionEvent actionEvent) throws IOException, ExecutionException, InterruptedException {

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
                    permisos = SolicitudPermisoService.getAllPermisos();
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
            if(permisos != null){
                for(int i = 0; i < permisos.size(); i++){
                    if(permisos.get(i).isEstado()){
                        solicitudPermisos.add(new Permiso(permisos.get(i).getId(),permisos.get(i).getPersona_solicitante(),"",permisos.get(i).getAccion(),permisos.get(i).isEstado(),permisos.get(i).getFechaCreacion()));
                        permisoDTOS.add(permisos.get(i));
                    }
                }
                tvSolicitudes.setItems(solicitudPermisos);
            }
        });
    }

    private void concederPermiso(String tabla, Long id, int index) throws IOException, ExecutionException, InterruptedException {

        Runnable rn=()-> {
            Platform.setImplicitExit(false);
            Platform.runLater(() -> {

                if(tabla.equals("cobro_cancelado")){

                }
                else if(tabla.equals("cobro_generado")){
                    CobroGeneradoDTO cobroGeneradoDTO = null;
                    try {
                        cobroGeneradoDTO = CobroGeneradoService.getCobroGeneradoId(id);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(cobroGeneradoDTO != null){
                        cobroGeneradoDTO.setEstado(false);
                        try {
                            CobroGeneradoService.updateCobro(cobroGeneradoDTO,id);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        permisoDTOS.remove(index);
                        tvSolicitudes.getItems().remove(index);
                        msg.show(Alert.AlertType.INFORMATION, "", "Permiso concedido con exito");
                    }
                }
                else if(tabla.equals("contribuyete")){
                    ContribuyenteDTO contribuyenteDTO = null;
                    try {
                        contribuyenteDTO = ContribuyenteService.getContribuyenteByCedula(String.valueOf(id));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(contribuyenteDTO != null){
                        List<ContribuyenteServicioDTO> contribuyenteServicioDTOS = null;
                        try {
                            contribuyenteServicioDTOS = ContribuyenteServicioService.contribuyenteServicio();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        for(int i = 0; i < contribuyenteServicioDTOS.size(); i++){
                            if(contribuyenteServicioDTOS.get(i).getContribuyente().getCedula().equals(contribuyenteDTO.getCedula())){
                                contribuyenteServicioDTOS.get(i).setEstado(false);
                                try {
                                    ContribuyenteServicioService.updateContribuyenteServicio(contribuyenteServicioDTOS.get(i),contribuyenteServicioDTOS.get(i).getId());
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        Permiso permiso = tvSolicitudes.getSelectionModel().getSelectedItem();

                        for(int i = 0; i < permisoDTOS.size(); i++){
                            if(permisoDTOS.get(i).getId().equals(permiso.getTc_Id())){
                                permisoDTOS.get(i).setEstado(false);
                                permisoDTOS.get(i).setPersona_autorizante(authenticationResponse.getUsuarioDTO().getNombreCompleto());
                                try {
                                    SolicitudPermisoService.updateSolicitudPermiso(permisoDTOS.get(i),permisoDTOS.get(i).getId());
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        permisoDTOS.remove(index);
                        tvSolicitudes.getItems().remove(index);
                        msg.show(Alert.AlertType.INFORMATION, "", "Permiso concedido con exito");
                    }
                }
                else if(tabla.equals("contribuyente_servicio")){
                    ContribuyenteServicioDTO contribuyente = null;
                    try {
                        contribuyente = ContribuyenteServicioService.getContribuyenteServicioId(id);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(contribuyente != null){
                        contribuyente.setEstado(false);
                        try {
                            ContribuyenteServicioService.updateContribuyenteServicio(contribuyente,id);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        Permiso permiso = tvSolicitudes.getSelectionModel().getSelectedItem();

                        for(int i = 0; i < permisoDTOS.size(); i++){
                            if(permisoDTOS.get(i).getId().equals(permiso.getTc_Id())){
                                permisoDTOS.get(i).setEstado(false);
                                permisoDTOS.get(i).setPersona_autorizante(authenticationResponse.getUsuarioDTO().getNombreCompleto());
                                try {
                                    SolicitudPermisoService.updateSolicitudPermiso(permisoDTOS.get(i),permisoDTOS.get(i).getId());
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        permisoDTOS.remove(index);
                        tvSolicitudes.getItems().remove(index);
                        msg.show(Alert.AlertType.INFORMATION, "", "Permiso concedido con exito");
                    }
                }else if(tabla.equals("derecho_cementerio")){

                }else if(tabla.equals("lista_salidas")){

                }else if(tabla.equals("propiedades")){

                }else if(tabla.equals("ruta buses")){

                }else if(tabla.equals("servicios")){

                    ServicioDTO servicio = null;
                    try {
                        servicio = ServicioService.getServicioId(String.valueOf(id));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(servicio != null){
                        servicio.setEstado(false);
                        try {
                            ServicioService.updateServicio(servicio,servicio.getId());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        List<ContribuyenteServicioDTO> contribuyenteServicioDTOS = null;
                        try {
                            contribuyenteServicioDTOS = ContribuyenteServicioService.contribuyenteServicio();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        for(int i = 0; i < contribuyenteServicioDTOS.size(); i++){
                            if(contribuyenteServicioDTOS.get(i).getServicio().getId().equals(id)){
                                contribuyenteServicioDTOS.get(i).setEstado(false);
                                try {
                                    ContribuyenteServicioService.updateContribuyenteServicio(contribuyenteServicioDTOS.get(i),contribuyenteServicioDTOS.get(i).getId());
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        Permiso permiso = tvSolicitudes.getSelectionModel().getSelectedItem();

                        for(int i = 0; i < permisoDTOS.size(); i++){
                            if(permisoDTOS.get(i).getId().equals(permiso.getTc_Id())){
                                System.out.println("Se encontro el permiso");
                                permisoDTOS.get(i).setEstado(false);
                                permisoDTOS.get(i).setPersona_autorizante(authenticationResponse.getUsuarioDTO().getNombreCompleto());
                                try {
                                    SolicitudPermisoService.updateSolicitudPermiso(permisoDTOS.get(i),permisoDTOS.get(i).getId());
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        permisoDTOS.remove(index);
                        tvSolicitudes.getItems().remove(index);
                        msg.show(Alert.AlertType.INFORMATION, "", "Permiso concedido con éxito");
                    }

                }else if(tabla.equals("servicios_propiedades")){

                }else if(tabla.equals("tipo_derechos")){

                }
            });
        };

        Thread t=new Thread(rn);
        t.start();
    }
}
