package org.una.municipalidad.services;
import org.una.municipalidad.dto.SolicitudPermisoDTO;

import java.util.Date;
import java.util.List;
import java.util.Optional;
public interface ISolicitudPermisoService {

    public Optional<SolicitudPermisoDTO> findById(Long id);

    public Optional<List<SolicitudPermisoDTO>> findByEstado(boolean estado);

    public Optional<List<SolicitudPermisoDTO>> findByFechaCreacionBetween(Date startDate, Date endDate);

    public SolicitudPermisoDTO create(SolicitudPermisoDTO solicitudPermisoDTO);

    public Optional<SolicitudPermisoDTO> update(SolicitudPermisoDTO solicitudPermisoDTO, Long id);

    public void delete(Long id);

    public void deleteAll();

    public Optional<List<SolicitudPermisoDTO>> findAll();
}
