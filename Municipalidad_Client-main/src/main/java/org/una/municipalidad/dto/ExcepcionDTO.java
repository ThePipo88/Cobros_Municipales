package org.una.municipalidad.dto;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ExcepcionDTO {

    private Long id;
    private UsuarioDTO usuario;
    private String descripcion;
    private boolean estado;
    private Date fechaCreacion;

}
