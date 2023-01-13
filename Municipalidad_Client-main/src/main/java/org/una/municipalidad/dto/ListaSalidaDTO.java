package org.una.municipalidad.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ListaSalidaDTO {

    private Long id;
    private RutaBusDTO rutaBus;
    private String dia;
    private int cantidad;

}
