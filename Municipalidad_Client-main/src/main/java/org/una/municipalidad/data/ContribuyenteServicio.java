package org.una.municipalidad.data;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@ToString
@Builder
public class ContribuyenteServicio {

    private Long tc_Id;
    public String tc_Tipo;
    public String tc_Descripcion;
    public Date tc_FechaCreacion;
    public Date tc_Actualizacion;
    public float tc_Porcentaje;
}
