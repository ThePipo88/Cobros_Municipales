package org.una.municipalidad.dto;

import lombok.*;

import java.util.Date;

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
