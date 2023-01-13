package org.una.municipalidad.dto;

import lombok.*;
import org.una.municipalidad.entities.Servicio;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ServicioPropiedadDTO {

    private Long id;
    private ServicioDTO servicio;
    private PropiedadDTO propiedad;

}
