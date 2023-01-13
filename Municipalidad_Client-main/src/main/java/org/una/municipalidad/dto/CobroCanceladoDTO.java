package org.una.municipalidad.dto;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CobroCanceladoDTO {

    private Long id;
    private CobroGeneradoDTO cobroGenerado;
    private Date fechaCreacion;
    private String descripcion;
}
