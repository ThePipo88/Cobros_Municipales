package org.una.municipalidad.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.una.municipalidad.dto.RutaBusDTO;
import org.una.municipalidad.dto.ServicioPropiedadDTO;
import org.una.municipalidad.entities.RutaBus;
import org.una.municipalidad.entities.Servicio;
import org.una.municipalidad.entities.ServicioPropiedad;
import org.una.municipalidad.exceptions.NotFoundInformationException;
import org.una.municipalidad.repositories.IListaSalidaRepository;
import org.una.municipalidad.repositories.IServicioPropiedadRepository;
import org.una.municipalidad.utils.MapperUtils;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioPropiedadServiceImplementation implements IServicioPropiedadService{

    @Autowired
    IServicioPropiedadRepository servicioPropiedadRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<List<ServicioPropiedadDTO>> findAll() {
        List<ServicioPropiedadDTO> servicioPropiedadDTOList = MapperUtils.DtoListFromEntityList(servicioPropiedadRepository.findAll(), ServicioPropiedadDTO.class);
        if(servicioPropiedadDTOList.isEmpty()){
            throw new NotFoundInformationException();
        }else{
            return Optional.ofNullable(servicioPropiedadDTOList);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ServicioPropiedadDTO> findById(Long id) {
        Optional<ServicioPropiedad> servicioPropiedad = servicioPropiedadRepository.findById(id);
        if (servicioPropiedad .isEmpty()) throw new NotFoundInformationException();

        ServicioPropiedadDTO servicioPropiedadDTO = MapperUtils.DtoFromEntity(servicioPropiedad.get(), ServicioPropiedadDTO.class);
        return Optional.ofNullable(servicioPropiedadDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<ServicioPropiedadDTO>> findByServicioId(Long id) {
        List<ServicioPropiedad> servicioPropiedadList = servicioPropiedadRepository.findByServicioId(id);
        List<ServicioPropiedadDTO> rutaBusDTOList = MapperUtils.DtoListFromEntityList(servicioPropiedadList, ServicioPropiedadDTO.class);
        return Optional.ofNullable(rutaBusDTOList);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<ServicioPropiedadDTO>> findByPropiedadId(Long id) {
        List<ServicioPropiedad> servicioPropiedadList = servicioPropiedadRepository.findByServicioId(id);
        List<ServicioPropiedadDTO> rutaBusDTOList = MapperUtils.DtoListFromEntityList(servicioPropiedadList, ServicioPropiedadDTO.class);
        return Optional.ofNullable(rutaBusDTOList);
    }

    @Override
    @Transactional
    public Optional<ServicioPropiedadDTO> create(ServicioPropiedadDTO servicioPropiedadDTO) {
        return Optional.ofNullable(getSavedServicioPropiedadDTO(servicioPropiedadDTO));
    }

    @Override
    @Transactional
    public Optional<ServicioPropiedadDTO> update(ServicioPropiedadDTO servicioPropiedadDTO, Long id) {
        if (servicioPropiedadRepository.findById(id).isEmpty()) throw new NotFoundInformationException();

        return Optional.ofNullable(getSavedServicioPropiedadDTO(servicioPropiedadDTO));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        servicioPropiedadRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAll() {
        servicioPropiedadRepository.deleteAll();
    }

    private ServicioPropiedadDTO getSavedServicioPropiedadDTO(ServicioPropiedadDTO servicioPropiedadDTO) {
        ServicioPropiedad servicioPropiedad = MapperUtils.EntityFromDto(servicioPropiedadDTO, ServicioPropiedad.class);
        ServicioPropiedad servicioPropiedadCreated = servicioPropiedadRepository.save(servicioPropiedad);
        return MapperUtils.DtoFromEntity(servicioPropiedadCreated, ServicioPropiedadDTO.class);
    }
}
