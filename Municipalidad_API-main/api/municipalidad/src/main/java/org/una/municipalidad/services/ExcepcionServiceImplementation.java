package org.una.municipalidad.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.una.municipalidad.dto.ExcepcionDTO;
import org.una.municipalidad.dto.UsuarioDTO;
import org.una.municipalidad.entities.Excepcion;
import org.una.municipalidad.entities.Usuario;
import org.una.municipalidad.exceptions.NotFoundInformationException;
import org.una.municipalidad.repositories.IExcepcionRepository;
import org.una.municipalidad.repositories.IUsuarioRepository;
import org.una.municipalidad.utils.MapperUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ExcepcionServiceImplementation implements IExcepcionService{

    @Autowired
    private IExcepcionRepository excepcionRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<List<ExcepcionDTO>> findAll() {
        List<ExcepcionDTO> usuarioDTOList = MapperUtils.DtoListFromEntityList(excepcionRepository.findAll(), ExcepcionDTO.class);
        return Optional.ofNullable(usuarioDTOList);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ExcepcionDTO> findById(Long id) {
        Optional<Excepcion> excepcion = excepcionRepository.findById(id);
        if (excepcion.isEmpty()) throw new NotFoundInformationException();

        ExcepcionDTO excepcionDTO = MapperUtils.DtoFromEntity(excepcion.get(), ExcepcionDTO.class);
        return Optional.ofNullable(excepcionDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<ExcepcionDTO>> findByUsuario(Long id) {
        List<Excepcion> excepcionList = excepcionRepository.findByUsuario(id);
        List<ExcepcionDTO> excepcionDTOList = MapperUtils.DtoListFromEntityList(excepcionList, ExcepcionDTO.class);
        return Optional.ofNullable(excepcionDTOList);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<ExcepcionDTO>> findByEstado(boolean estado) {
        List<Excepcion> excepcionList = excepcionRepository.findByEstado(estado);
        List<ExcepcionDTO> excepcionDTOList = MapperUtils.DtoListFromEntityList(excepcionList, ExcepcionDTO.class);
        return Optional.ofNullable(excepcionDTOList);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<ExcepcionDTO>> findByFechaCreacion(Date startDate) {
        List<Excepcion> excepcionList = excepcionRepository.findByFechaCreacion(startDate);
        List<ExcepcionDTO> excepcionDTOList = MapperUtils.DtoListFromEntityList(excepcionList, ExcepcionDTO.class);
        return Optional.ofNullable(excepcionDTOList);
    }

    @Override
    @Transactional
    public ExcepcionDTO create(ExcepcionDTO excepcionDTO) {
        Excepcion excepcion = MapperUtils.EntityFromDto(excepcionDTO, Excepcion.class);
        excepcion = excepcionRepository.save(excepcion);
        return MapperUtils.DtoFromEntity(excepcion, ExcepcionDTO.class);
    }

    @Override
    @Transactional
    public Optional<ExcepcionDTO> update(ExcepcionDTO excepcionDTO, Long id) {

        if (excepcionRepository.findById(id).isEmpty()) throw new NotFoundInformationException();
        return Optional.ofNullable(getSavedExcepcionDTO(excepcionDTO));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        excepcionRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAll() {
         excepcionRepository.deleteAll();
    }

    private ExcepcionDTO getSavedExcepcionDTO(ExcepcionDTO excepcionDTO) {
        Excepcion excepcion = MapperUtils.EntityFromDto(excepcionDTO, Excepcion.class);
        Excepcion excepcionCreated = excepcionRepository.save(excepcion);
        return MapperUtils.DtoFromEntity(excepcionCreated, ExcepcionDTO.class);
    }

}
