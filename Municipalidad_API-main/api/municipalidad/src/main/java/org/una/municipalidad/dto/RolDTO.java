package org.una.municipalidad.dto;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class RolDTO {

    private Long id;
    private String nombre;
    private Date fechaCreacion;
    private boolean estado;
}