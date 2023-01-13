package org.una.municipalidad.dto;

import lombok.*;

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
