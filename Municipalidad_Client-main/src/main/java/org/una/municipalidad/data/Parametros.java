package org.una.municipalidad.data;
import com.jfoenix.controls.JFXTextField;
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
public class Parametros {

    private String cl_Id;
    private boolean cl_Estado;
    private String cl_FechaCreacion;
    private String cl_FechaModificacion;
    private String cl_Objeto;
    private String cl_Nombre;


}
