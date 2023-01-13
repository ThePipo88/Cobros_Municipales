package org.una.municipalidad.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
@Builder
public class CantidadSalidas {

    private String dia;
    private int cantidad;

}
