package org.una.municipalidad.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.una.municipalidad.dto.ServicioDTO;
import org.una.municipalidad.dto.SolicitudPermisoDTO;
import org.una.municipalidad.entities.SolicitudPermiso;
import org.una.municipalidad.exceptions.NotFoundInformationException;
import org.una.municipalidad.repositories.ISolicitudPermisoRepository;
import org.una.municipalidad.utils.MapperUtils;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service

public class SolicitudPermisoServiceImplementation implements  ISolicitudPermisoService {

    @Autowired
    private ISolicitudPermisoRepository solicitudPermisoRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<List<SolicitudPermisoDTO>> findAll() {
        List<SolicitudPermisoDTO> solicitudPermisoDTOList= MapperUtils.DtoListFromEntityList(solicitudPermisoRepository.findAll(), SolicitudPermisoDTO.class);
        return Optional.ofNullable(solicitudPermisoDTOList);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SolicitudPermisoDTO> findById(Long id) {
        Optional<SolicitudPermiso> solicitudPermiso = solicitudPermisoRepository.findById(id);
        if (solicitudPermiso.isEmpty()) throw new NotFoundInformationException();
        SolicitudPermisoDTO solicitudPermisoDTO = MapperUtils.DtoFromEntity(solicitudPermiso.get(), SolicitudPermisoDTO.class);
        return Optional.ofNullable(solicitudPermisoDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<SolicitudPermisoDTO>> findByFechaCreacionBetween(Date startDate, Date endDate) {
        List<SolicitudPermiso> solicitudPermisoList = solicitudPermisoRepository.findByFechaCreacionBetween(startDate,endDate);
        List<SolicitudPermisoDTO> solicitudPermisoDTOList = MapperUtils.DtoListFromEntityList(solicitudPermisoList, SolicitudPermisoDTO.class);
        return Optional.ofNullable(solicitudPermisoDTOList);
    }

    @Override
    @Transactional
    public SolicitudPermisoDTO create(SolicitudPermisoDTO solicitudPermisoDTO) {
        SolicitudPermiso solicitudPermiso = MapperUtils.EntityFromDto(solicitudPermisoDTO, SolicitudPermiso.class);
        solicitudPermiso = solicitudPermisoRepository.save(solicitudPermiso);
        return MapperUtils.DtoFromEntity(solicitudPermiso, SolicitudPermisoDTO.class);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<List<SolicitudPermisoDTO>> findByEstado(boolean estado) {
        List<SolicitudPermiso> solicitudPermisoList = solicitudPermisoRepository.findByEstado(estado);
        List<SolicitudPermisoDTO> SolicitudPermisoDTOList = MapperUtils.DtoListFromEntityList(solicitudPermisoList, SolicitudPermisoDTO.class);
        return Optional.ofNullable(SolicitudPermisoDTOList);
    }

    private SolicitudPermisoDTO getSavedSolicitudPermiso(SolicitudPermisoDTO solicitudPermisoDTO) {
        SolicitudPermiso solicitudPermiso = MapperUtils.EntityFromDto(solicitudPermisoDTO, SolicitudPermiso.class);
        SolicitudPermiso solicitudPermisoCreated = solicitudPermisoRepository.save(solicitudPermiso);
        return MapperUtils.DtoFromEntity(solicitudPermisoCreated, SolicitudPermisoDTO.class);
    }

    @Override
    @Transactional
    public Optional<SolicitudPermisoDTO> update(SolicitudPermisoDTO solicitudPermisoDTO, Long id) {
        if (solicitudPermisoRepository.findById(id).isEmpty()) throw new NotFoundInformationException();
        return Optional.ofNullable(getSavedSolicitudPermiso(solicitudPermisoDTO));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        solicitudPermisoRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAll() {
        solicitudPermisoRepository.deleteAll();
    }

}
