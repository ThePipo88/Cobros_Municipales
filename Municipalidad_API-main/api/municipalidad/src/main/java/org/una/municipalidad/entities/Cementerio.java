package org.una.municipalidad.entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;


@Entity
@Table(name = "derechos_cementerios")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Cementerio implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="servicio_id")
    private Servicio servicio;

    @Column(name = "sector", length = 100)
    private String sector;

    @Column(name = "ocupado", length = 100)
    private String ocupado;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cementerio")
    private List<TipoDerecho> tipoDerecho = new ArrayList<>();

    private static final long serialVersionUID = 1L;
}
