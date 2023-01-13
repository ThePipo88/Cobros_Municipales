package org.una.municipalidad.data;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.una.municipalidad.dto.PropiedadDTO;

import java.util.Date;

@Data
@AllArgsConstructor
@ToString
@Builder
public class ValoresImpositivos {

    private Long cl_id;
    private String cl_tipo;
    private String tl_descripcion;
    private String tl_estado;
    private Date tl_fechaRegistro;
    private Date tl_ultimaAct;
}
