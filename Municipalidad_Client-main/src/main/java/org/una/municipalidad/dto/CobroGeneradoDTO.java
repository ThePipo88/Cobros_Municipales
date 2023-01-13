package org.una.municipalidad.dto;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CobroGeneradoDTO {

    private Long id;
    private ContribuyenteServicioDTO contribuyenteServicio;
    private Date fechaCobro;
    private Double monto;
    private boolean estado;
}
