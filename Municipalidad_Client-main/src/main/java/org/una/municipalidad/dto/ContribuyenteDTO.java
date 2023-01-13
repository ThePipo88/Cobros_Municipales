package org.una.municipalidad.dto;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ContribuyenteDTO {

    private Long id;
    private String cedula;
    private String nombre;
    private Date fechaNacimiento;
    private String direccion;
    private String correoElectronico;
    private String telefono;
}
