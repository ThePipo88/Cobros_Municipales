package org.una.municipalidad.services;

import org.springframework.data.repository.query.Param;
import org.una.municipalidad.dto.RutaBusDTO;
import org.una.municipalidad.entities.RutaBus;

import java.util.List;
import java.util.Optional;

public interface IRutaBusService {

    public Optional<List<RutaBusDTO>> findAll();

    public Optional<RutaBusDTO> findById(Long id);

    public Optional<List<RutaBusDTO>> findByNombre(String nombre);

    public Optional<List<RutaBusDTO>> findByInicio(String inicio);

    public Optional<List<RutaBusDTO>> findByFin(String fin);

    public Optional<List<RutaBusDTO>> findByServicioId(@Param("id")Long id);

    public Optional<RutaBusDTO> create(RutaBusDTO rutaBusDTO);

    public Optional<RutaBusDTO> update(RutaBusDTO rutaBusDTO, Long id);

    public void delete(Long id);

    public void deleteAll();
}
