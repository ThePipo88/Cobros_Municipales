package org.una.municipalidad.dto;

import lombok.*;

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
