package org.una.municipalidad.dto;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class TipoDerechoDTO {

    private Long id;
    private CementerioDTO cementerio;
    private String tipo;
    private int monto;

}
