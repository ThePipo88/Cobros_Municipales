package org.una.municipalidad.dto;

import lombok.*;
import org.una.municipalidad.entities.CobroGenerado;
import org.una.municipalidad.entities.Recibo;
import org.una.municipalidad.dto.ContribuyenteDTO;

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
