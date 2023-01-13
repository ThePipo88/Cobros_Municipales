package org.una.municipalidad.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
@Builder

public class DataCobros {
    private String id;
    private String Estado;
    private String FechaCobro;
    private String Monto;
    private String cContribuyente;
}
