package org.una.municipalidad.dto;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ServicioDTO {

    private Long id;
    private String tipoServicio;
    private String descripcion;
    private boolean estado;
    private PropiedadDTO propiedad;
    private Date fechaRegistro;
    private Date ultimaActualizacion;
    private float porcentaje;
}
