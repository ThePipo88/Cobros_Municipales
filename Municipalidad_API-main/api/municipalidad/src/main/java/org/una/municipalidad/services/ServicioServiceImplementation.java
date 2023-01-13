package org.una.municipalidad.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.una.municipalidad.dto.*;
import org.una.municipalidad.entities.Recibo;
import org.una.municipalidad.entities.RutaBus;
import org.una.municipalidad.entities.Servicio;
import org.una.municipalidad.entities.Usuario;
import org.una.municipalidad.exceptions.NotFoundInformationException;
import org.una.municipalidad.repositories.IRutaBusRepository;
import org.una.municipalidad.repositories.IServicioRepository;
import org.una.municipalidad.utils.MapperUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ServicioServiceImplementation implements IServicioService{

    @Autowired
    private IServicioRepository servicioRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<List<ServicioDTO>> findAll() {
        List<ServicioDTO> servicioDTOList = MapperUtils.DtoListFromEntityList(servicioRepository.findAll(), ServicioDTO.class);
        return Optional.ofNullable(servicioDTOList);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ServicioDTO> findById(Long id) {
        Optional<Servicio> servicio = servicioRepository.findById(id);
        if (servicio.isEmpty()) throw new NotFoundInformationException();

        ServicioDTO servicioDTO = MapperUtils.DtoFromEntity(servicio.get(), ServicioDTO.class);
        return Optional.ofNullable(servicioDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<ServicioDTO>> findByTipoServicio(String servicio) {
        List<Servicio> servicioList = servicioRepository.findByTipoServicio(servicio);
        List<ServicioDTO> servicioDTOList = MapperUtils.DtoListFromEntityList(servicioList, ServicioDTO.class);
        return Optional.ofNullable(servicioDTOList);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<ServicioDTO>> findByEstado(boolean estado) {
        List<Servicio> servicioList = servicioRepository.findByEstado(estado);
        List<ServicioDTO> servicioDTOList = MapperUtils.DtoListFromEntityList(servicioList, ServicioDTO.class);
        return Optional.ofNullable(servicioDTOList);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<ServicioDTO>> findByFechaRegistro(Date fechaRegistro) {
        List<Servicio> servicioList = servicioRepository.findByFechaRegistro(fechaRegistro);
        List<ServicioDTO> servicioDTOList = MapperUtils.DtoListFromEntityList(servicioList, ServicioDTO.class);
        return Optional.ofNullable(servicioDTOList);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<ServicioDTO>> findByUltimaActualizacion(Date ultimaActualizacion) {
        List<Servicio> servicioList = servicioRepository.findByUltimaActualizacion(ultimaActualizacion);
        List<ServicioDTO> servicioDTOList = MapperUtils.DtoListFromEntityList(servicioList, ServicioDTO.class);
        return Optional.ofNullable(servicioDTOList);
    }

    @Override
    @Transactional
    public ServicioDTO create(ServicioDTO servicioDTO) {
        Servicio servicio = MapperUtils.EntityFromDto(servicioDTO, Servicio.class);
        servicio = servicioRepository.save(servicio);
        return MapperUtils.DtoFromEntity(servicio, ServicioDTO.class);
    }

    @Override
    @Transactional
    public Optional<ServicioDTO> update(ServicioDTO servicioDTO, Long id) {
        if (servicioRepository.findById(id).isEmpty()) throw new NotFoundInformationException();
        return Optional.ofNullable(getSavedServicioDTO(servicioDTO));
    }

    @Override
    @Transactional
    public void delete(Long id) {
            servicioRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAll() {
        servicioRepository.deleteAll();
    }

    private ServicioDTO getSavedServicioDTO(ServicioDTO servicioDTO) {
        Servicio servicio = MapperUtils.EntityFromDto(servicioDTO, Servicio.class);
        Servicio servicioCreated = servicioRepository.save(servicio);
        return MapperUtils.DtoFromEntity(servicioCreated, ServicioDTO.class);
    }

}
