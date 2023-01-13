package org.una.municipalidad.services;


import org.una.municipalidad.dto.ContribuyenteServicioDTO;
import org.una.municipalidad.entities.Contribuyente;
import org.una.municipalidad.entities.Servicio;

import java.util.List;
import java.util.Optional;

public interface IContribuyenteServicioService {

    public Optional<List<ContribuyenteServicioDTO>> findAll();

    public Optional<ContribuyenteServicioDTO> findById(Long id);

    public Optional<List<ContribuyenteServicioDTO>> findByPorcentaje(float porcentaje);

    public ContribuyenteServicioDTO create(ContribuyenteServicioDTO contribuyenteServicioDTO);

    public Optional<ContribuyenteServicioDTO> update(ContribuyenteServicioDTO contribuyenteServicioDTO, Long id);

    public void delete(Long id);

    public void deleteAll();
}
