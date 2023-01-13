package org.una.municipalidad.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@ToString
@Builder
public class DatosTabla {
    private String cedula;
    private String nombre;
    private String correo;
    private String telefono;
    private Date fechaEmision;
    private Long idValorImpositivo;
    private String tipoServicio;
    private String fechaRegistro;
    private Boolean estado;
    private String valorImpuesto;
    private String descripcion;
}
