package org.una.municipalidad.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "serviciosPropiedades")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ServicioPropiedad implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="propiedad_id")
    private Propiedad propiedad;

    @ManyToOne
    @JoinColumn(name="servicio_id")
    private Servicio servicio;

    private static final long serialVersionUID = 1L;

}
