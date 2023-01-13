package org.una.municipalidad.dto;

import lombok.*;
import org.una.municipalidad.entities.CobroCancelado;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ReciboDTO {

    private Long id;
    private String descripcion;
    private String contribuyente;
    private String montoCancelado;
    private Date fechaEmision;

}
