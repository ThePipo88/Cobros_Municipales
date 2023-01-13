package org.una.municipalidad.services;

import org.una.municipalidad.dto.TipoDerechoDTO;
import org.una.municipalidad.dto.TransaccionDTO;
import org.una.municipalidad.dto.UsuarioDTO;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ITransaccionService {

    public Optional<TransaccionDTO> findById(Long id);

    public Optional<List<TransaccionDTO>> findAll();

    public Optional<List<TransaccionDTO>> findByUsuarioIdAndFechaCreacionBetween(Long usuarioId, Date startDate, Date endDate);

    public Optional<List<TransaccionDTO>> findByObjetoAndFechaCreacionBetween(String objetoId, Date startDate, Date endDate);

    public Optional<List<TransaccionDTO>> findByFechaCreacionBetween(Date startDate, Date endDate);

    public TransaccionDTO create(TransaccionDTO transaccion);

    public Optional<TransaccionDTO> update(TransaccionDTO transaccionDTO, Long id);

    public void delete(Long id);

    public void deleteAll();
}
