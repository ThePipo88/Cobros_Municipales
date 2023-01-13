package org.una.municipalidad.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
@Builder
public class ReporteRecibo implements Comparable<ReporteRecibo> {

    private final String numeroRecibo;

    private final String idContribuyente;

    private final String nombreContribuyente;

    public final String tipoValorImpositivo;

    private final String total;

    private final String FechaEmision;


    @Override
    public int compareTo(ReporteRecibo o) {
        return this.getNumeroRecibo().compareTo(o.getNumeroRecibo());
    }
}
