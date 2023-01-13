package org.una.municipalidad.services;

import org.springframework.data.repository.query.Param;
import org.una.municipalidad.dto.RutaBusDTO;
import org.una.municipalidad.dto.TipoDerechoDTO;
import org.una.municipalidad.dto.TransaccionDTO;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ITipoDerechoService {

    public Optional<List<TipoDerechoDTO>> findAll();

    public Optional<TipoDerechoDTO> findById(Long id);

    public Optional<List<TipoDerechoDTO>> findByTipo(String tipo);

    public Optional<List<TipoDerechoDTO>> findByMonto(int monto);

    public Optional<TipoDerechoDTO> create(TipoDerechoDTO tipoDerechoDTO);

    public Optional<List<TipoDerechoDTO>> findByServicioId(@Param("id")Long id);

    public Optional<TipoDerechoDTO> update(TipoDerechoDTO tipoDerechoDTO, Long id);

    public void delete(Long id);

    public void deleteAll();


}
