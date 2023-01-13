package org.una.municipalidad.data;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Date;


@Data
@AllArgsConstructor
@ToString
@Builder
public class CobroCancelados {

    private Long cl_id;
    private String cl_FechaCreacion;
    private String cl_Contribuyente;
    private String cl_Descripcion;
    private String cl_Monto;
}
