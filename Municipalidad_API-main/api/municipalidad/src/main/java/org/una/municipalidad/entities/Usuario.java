package org.una.municipalidad.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder

public class Usuario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_completo", length = 100)
    private String nombreCompleto;

    @Column(length = 25, unique = true)
    private String cedula;

    @Column(length = 100, name = "password_encriptado")
    private String passwordEncriptado;

    @ManyToOne
    @JoinColumn(name="roles_id")
    private Rol rol;

    @Column
    private boolean estado;

    @Column(name = "fecha_creacion", updatable = false)
    @Temporal(TemporalType.DATE)
    @Setter(AccessLevel.NONE)
    private Date fechaCreacion;

    @Column(name = "fecha_modificacion")
    @Setter(AccessLevel.NONE)
    @Temporal(TemporalType.DATE)
    private Date fechaModificacion;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private List<Excepcion> excepciones = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private List<Transaccion> transacciones = new ArrayList<>();

    private static final long serialVersionUID = 1L;

    @PrePersist
    public void prePersist() {
        fechaCreacion = new Date();
        fechaModificacion = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        fechaModificacion = new Date();
    }

}
