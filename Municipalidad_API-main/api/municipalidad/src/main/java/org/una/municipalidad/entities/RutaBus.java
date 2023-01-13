package org.una.municipalidad.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ruta_buses")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class RutaBus implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="servicio_id")
    private Servicio servicio;

    @Column(name = "nombre", length = 100)
    private String nombre;

    @Column(name = "inicio", length = 100)
    private String inicio;

    @Column(name = "final", length = 100)
    private String fin;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rutaBus")
    private List<ListaSalida> salidas = new ArrayList<>();

    private static final long serialVersionUID = 1L;

}
