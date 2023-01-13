package org.una.municipalidad.dto;
import lombok.*;
import org.una.municipalidad.entities.Usuario;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class SolicitudPermisoDTO {

    private Long id;
    private boolean estado;
    private String persona_solicitante;
    private String persona_autorizante;
    private String accion;
    private Date fechaCreacion;
    private String tabla;
    private Long idEliminar;
}
