package org.una.municipalidad.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.una.municipalidad.entities.ListaSalida;
import org.una.municipalidad.entities.RutaBus;

import java.util.ArrayList;
import java.util.List;

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
