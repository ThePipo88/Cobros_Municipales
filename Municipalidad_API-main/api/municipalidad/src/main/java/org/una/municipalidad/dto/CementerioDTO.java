package org.una.municipalidad.dto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.una.municipalidad.entities.Servicio;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CementerioDTO {

    private Long id;
    private ServicioDTO servicio;
    private String sector;
    private String ocupado;
}
