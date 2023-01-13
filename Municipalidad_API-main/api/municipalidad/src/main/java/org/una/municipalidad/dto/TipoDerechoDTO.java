package org.una.municipalidad.dto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.una.municipalidad.entities.Cementerio;
import org.una.municipalidad.entities.TipoDerecho;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class TipoDerechoDTO {

    private Long id;
    private CementerioDTO cementerio;
    private String tipo;
    private int monto;

}
