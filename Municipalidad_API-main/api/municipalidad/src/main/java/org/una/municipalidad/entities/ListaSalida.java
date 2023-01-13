package org.una.municipalidad.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "lista_salidas")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ListaSalida implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="ruta_buses_id")
    private RutaBus rutaBus;

    @Column(name = "dia", length = 100)
    private String dia;

    @Column(name = "cantidad")
    private int cantidad;

    private static final long serialVersionUID = 1L;
}
