package org.una.municipalidad.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.una.municipalidad.entities.CobroCancelado;
import org.una.municipalidad.dto.ContribuyenteServicioDTO;

import javax.persistence.*;
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
