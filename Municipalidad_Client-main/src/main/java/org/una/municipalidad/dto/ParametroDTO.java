package org.una.municipalidad.dto;

import lombok.*;

import javax.print.DocFlavor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ParametroDTO {

    private Long id;
    private String formula;
    private String nombre;
    private boolean estado;
    private Date fechaCreacion;
    private Date fechaModificacion;

}
