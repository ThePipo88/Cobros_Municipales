package org.una.municipalidad.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
@Builder
public class Transacciones {

    private String id;
    private String accion;
    private String fechaCreacion;
    private String objeto;
    private String usuario;
}
