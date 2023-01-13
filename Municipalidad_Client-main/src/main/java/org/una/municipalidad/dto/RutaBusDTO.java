package org.una.municipalidad.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class RutaBusDTO {

    private Long id;
    private ServicioDTO servicio;
    private String nombre;
    private String inicio;
    private String fin;
}
