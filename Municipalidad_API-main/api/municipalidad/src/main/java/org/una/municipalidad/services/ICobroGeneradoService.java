package org.una.municipalidad.services;

import org.springframework.data.repository.query.Param;
import org.una.municipalidad.dto.CobroGeneradoDTO;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ICobroGeneradoService {

    public Optional<List<CobroGeneradoDTO>> findAll();

    public Optional<CobroGeneradoDTO> findById(Long id);

    public Optional<List<CobroGeneradoDTO>> findByBusquedaId(Long id);

    public Optional<List<CobroGeneradoDTO>> findByMonto(Double monto);

    //public Optional<List<CobroGeneradoDTO>> findByObjetoAndFechaCobroBetween(String objetoId, Date startDate, Date endDate);

    public Optional<List<CobroGeneradoDTO>> findCobroByCedulaAndTipo(@Param("cedula")String cedula,@Param("tipo")String tipo);

    public Optional<List<CobroGeneradoDTO>> findCobroByCedula(@Param("cedula")String cedula);

    //public Optional<CobroGeneradoDTO> findByFechaCobro(Date fechaCobro);

    public Optional<CobroGeneradoDTO> findByCobroRutaBus(Date startDate,Date endDate);

    public Optional<CobroGeneradoDTO> findByCobroLimpiezaParque(Date startDate,Date endDate);

    public Optional<CobroGeneradoDTO> findByCobroCementerio(Date startDate,Date endDate);

    public Optional<List<CobroGeneradoDTO>> findByFechaCobroBetween(Date startDate, Date endDate);

    public Optional<List<CobroGeneradoDTO>> findByEstado(boolean estado);

    public CobroGeneradoDTO create(CobroGeneradoDTO cobroGeneradoDTO);

    public Optional<CobroGeneradoDTO> update(CobroGeneradoDTO cobroGeneradoDTO, Long id);

    public void delete(Long id);

    public void deleteAll();
}
