package org.una.municipalidad.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "contribuyentes_servicios")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ContribuyenteServicio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "porcentaje", length = 10)
    private float porcentaje;
    
    @ManyToOne
    @JoinColumn(name="contribuyente_id")
    private Contribuyente contribuyente;

    @ManyToOne
    @JoinColumn(name="servicio_id")
    private Servicio servicio;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contribuyenteServicio")
    private List<CobroGenerado> cobros = new ArrayList<>();

    @Column
    private boolean estado;

    private static final long serialVersionUID = 1L;
}
