package org.una.municipalidad.entities;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tipos_derechos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class TipoDerecho implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="cementerio_id")
    private Cementerio cementerio;

    @Column(name = "tipo_derechos", length = 100)
    private String tipo;

    @Column(name = "monto_fijo")
    private int monto;

    private static final long serialVersionUID = 1L;
}
