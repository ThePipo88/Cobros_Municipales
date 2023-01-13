package org.una.municipalidad.dto;

import lombok.*;
import org.una.municipalidad.entities.Usuario;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class TransaccionDTO {

    private Long id;
    private UsuarioDTO usuario;
    private String objeto;
    private String accion;
    private Date fechaCreacion;

}
