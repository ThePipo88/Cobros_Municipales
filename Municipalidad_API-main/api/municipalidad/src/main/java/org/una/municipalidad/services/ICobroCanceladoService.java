package org.una.municipalidad.services;

import org.springframework.data.repository.query.Param;
import org.una.municipalidad.dto.CobroCanceladoDTO;
import org.una.municipalidad.entities.CobroCancelado;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ICobroCanceladoService {

    public Optional<List<CobroCanceladoDTO>> findAll();

    public Optional<CobroCanceladoDTO> findById(Long id);

    public Optional<List<CobroCanceladoDTO>> findByFechaCreacion(Date startDate);

    public Optional<List<CobroCanceladoDTO>> findByFechaCreacionBetween(Date startDate, Date endDate);

    public Optional<List<CobroCanceladoDTO>> findByCobroBetweenCedulaAndFecha(@Param("cedula")String cedula, @Param("startDate")Date startDate, @Param("endDate")Date endDate);

    public Optional<List<CobroCanceladoDTO>>  findByCobroBetweenFecha(@Param("startDate") Date startDate, @Param("endDate")Date endDate);

    public Optional<CobroCanceladoDTO> create(CobroCanceladoDTO cobroCanceladoDTO);

    public Optional<CobroCanceladoDTO> update(CobroCanceladoDTO cobroCanceladoDTO, Long id);

    public void delete(Long id);

    public void deleteAll();
}
