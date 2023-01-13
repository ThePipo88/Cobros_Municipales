package org.una.municipalidad.services;

import org.springframework.data.repository.query.Param;
import org.una.municipalidad.dto.RutaBusDTO;
import org.una.municipalidad.dto.ServicioPropiedadDTO;

import java.util.List;
import java.util.Optional;

public interface IServicioPropiedadService {

    public Optional<List<ServicioPropiedadDTO>> findAll();

    public Optional<ServicioPropiedadDTO> findById(Long id);

    public Optional<List<ServicioPropiedadDTO>> findByServicioId(@Param("id")Long id);

    public Optional<List<ServicioPropiedadDTO>> findByPropiedadId(@Param("id")Long id);

    public Optional<ServicioPropiedadDTO> create(ServicioPropiedadDTO servicioPropiedadDTO);

    public Optional<ServicioPropiedadDTO> update(ServicioPropiedadDTO servicioPropiedadDTO, Long id);

    public void delete(Long id);

    public void deleteAll();

}
