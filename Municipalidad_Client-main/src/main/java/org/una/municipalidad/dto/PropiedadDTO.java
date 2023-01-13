package org.una.municipalidad.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PropiedadDTO {

    private Long id;
    private String provincia;
    private String canton;
    private String distrito;
    private String direccion;
    private String valorTerreno;
    private String zona;
    private Double metrosFrente;

}
