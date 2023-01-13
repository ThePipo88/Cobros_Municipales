package org.una.municipalidad.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ContribuyenteServicioDTO {

    private Long id;
    private float porcentaje;
    private ContribuyenteDTO contribuyente;
    private ServicioDTO servicio;
    private boolean estado;
}
