package org.una.municipalidad.data;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@ToString
@Builder
public class Permiso {

    private Long tc_Id;
    private String tc_PersonaSolicitante;
    private String tc_PersonaAutorizante;
    private String tc_Accion;
    private boolean tc_Estado;
    private Date tc_FechaCreacion;
}


