package org.una.municipalidad.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "propiedades")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Propiedad implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "provincia", length = 100)
    private String provincia;

    @Column(name = "canton", length = 100)
    private String canton;

    @Column(name = "distrito", length = 100)
    private String distrito;

    @Column(name = "direccion", length = 100)
    private String direccion;

    @Column(name = "valor_terreno", length = 100)
    private String valorTerreno;

    @Column(name = "zona", length = 100)
    private String zona;

    @Column(name = "metros_frente", length = 100)
    private Double metrosFrente;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "propiedad")
    private List<ServicioPropiedad> servicioPropiedad = new ArrayList<>();

    private static final long serialVersionUID = 1L;

}
