package org.una.municipalidad.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "contribuyentes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Contribuyente implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 25, unique = true)
    private String cedula;

    @Column(name = "nombre", length = 100)
    private String nombre;

    @Column(name = "fecha_nacimiento", updatable = false)
    @Temporal(TemporalType.DATE)
    @Setter(AccessLevel.NONE)
    private Date fechaNacimiento;

    @Column(name = "direccion", length = 300)
    private String direccion;

    @Column(name = "correo_electronico", length = 300)
    private String correoElectronico;

    @Column(name = "telefono", length = 300)
    private String telefono;

    private static final long serialVersionUID = 1L;

    @PrePersist
    public void prePersist() {
        fechaNacimiento = new Date();
    }
}
