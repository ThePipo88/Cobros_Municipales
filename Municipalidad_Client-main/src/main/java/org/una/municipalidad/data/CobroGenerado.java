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
public class CobroGenerado {

    private Long cl_id;
    private boolean cl_Estado;
    private String cl_FechaCobro;
    private double cl_Monto;
    private String cl_Contribuyente;
    private String cl_Servicio;
    private Long cl_servicioId;

}
