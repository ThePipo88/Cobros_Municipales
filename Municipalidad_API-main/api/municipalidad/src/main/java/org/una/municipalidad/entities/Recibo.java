package org.una.municipalidad.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "recibos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder

public class Recibo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descripcion", length = 250)
    private String descripcion;

    @Column(name = "contribuyente", length = 250)
    private String contribuyente;

    @Column(name = "monto_cancelado", length = 250)
    private String montoCancelado;

    @Column(name = "fecha_emision", updatable = false)
    @Temporal(TemporalType.DATE)
    @Setter(AccessLevel.NONE)
    private Date fechaEmision;

    private static final long serialVersionUID = 1L;

    @PrePersist
    public void prePersist() {
        fechaEmision = new Date();
    }

}
