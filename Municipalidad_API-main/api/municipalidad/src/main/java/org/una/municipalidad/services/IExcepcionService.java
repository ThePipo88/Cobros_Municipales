package org.una.municipalidad.services;

import org.una.municipalidad.dto.ExcepcionDTO;
import org.una.municipalidad.dto.UsuarioDTO;
import org.una.municipalidad.entities.Excepcion;

import javax.swing.text.html.Option;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IExcepcionService {

    public Optional<List<ExcepcionDTO>> findAll();

    public Optional<ExcepcionDTO> findById(Long id);

    public Optional<List<ExcepcionDTO>> findByUsuario(Long id);

    public Optional<List<ExcepcionDTO>> findByEstado(boolean estado);

    public Optional<List<ExcepcionDTO>> findByFechaCreacion(Date startDate);

    public ExcepcionDTO create(ExcepcionDTO excepcionDTO);

    public Optional<ExcepcionDTO> update(ExcepcionDTO excepcionDTO, Long id);

    public void delete(Long id);

    public void deleteAll();

}
