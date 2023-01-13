package org.una.municipalidad.entities;


import java.io.Serializable;
import java.util.Date;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "transacciones")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Transaccion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="usuarios_id")
    private Usuario usuario;

    @Column(name = "objeto", length = 45)
    private String objeto;

    @Column(name = "accion", length = 45)
    private String accion;

    @Column(name = "fecha_creacion", updatable = false)
    @Temporal(TemporalType.DATE)
    @Setter(AccessLevel.NONE)
    private Date fechaCreacion;

    private static final long serialVersionUID = 1L;

    @PrePersist
    public void prePersist() {
        fechaCreacion = new Date();
    }

    @PreUpdate
    public void preUpdate() {
    }
}
