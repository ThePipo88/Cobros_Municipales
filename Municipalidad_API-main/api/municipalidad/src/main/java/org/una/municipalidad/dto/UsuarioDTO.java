package org.una.municipalidad.dto;

import lombok.*;
import org.una.municipalidad.entities.Rol;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UsuarioDTO {

    private Long id;
    private String nombreCompleto;
    private String cedula;
    private String passwordEncriptado;
    private boolean estado;
    private Date fechaCreacion;
    private Date fechaModificacion;
    private RolDTO rol;

}

