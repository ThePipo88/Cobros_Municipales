package org.una.municipalidad.services;

import org.una.municipalidad.dto.CobroCanceladoDTO;
import org.una.municipalidad.dto.ParametroDTO;


import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IParametroService {

    public Optional<List<ParametroDTO>> findAll();

    public Optional<ParametroDTO> findById(Long id);

    public Optional<List<ParametroDTO>> findByFormulaAproximate(String formula);

    public Optional<List<ParametroDTO>> findByNombreAproximate(String nombre);

    public Optional<List<ParametroDTO>> findByEstadoAproximate(boolean estado);

    public Optional<List<ParametroDTO>> findByFechaCreacionBetween(Date startDate, Date endDate);

    public Optional<List<ParametroDTO>> findByFechaModificacionBetween(Date startDate, Date endDate);

    public ParametroDTO create(ParametroDTO parametroDTO);

    public Optional<ParametroDTO> update(ParametroDTO parametroDTO, Long id);

    public void delete(Long id);

    public void deleteAll();

}
