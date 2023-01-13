package org.una.municipalidad.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.una.municipalidad.dto.CobroGeneradoDTO;
import org.una.municipalidad.dto.RutaBusDTO;
import org.una.municipalidad.entities.CobroGenerado;
import org.una.municipalidad.entities.RutaBus;
import org.una.municipalidad.exceptions.NotFoundInformationException;
import org.una.municipalidad.repositories.IRutaBusRepository;
import org.una.municipalidad.utils.MapperUtils;

import java.util.List;
import java.util.Optional;

@Service
public class RutaBusServiceImplementation implements IRutaBusService{

    @Autowired
    private IRutaBusRepository rutaBusRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<List<RutaBusDTO>> findAll() {
        List<RutaBusDTO> rutaBusDTOList = MapperUtils.DtoListFromEntityList(rutaBusRepository.findAll(), RutaBusDTO.class);
        if(rutaBusDTOList .isEmpty()){
            throw new NotFoundInformationException();
        }else{
            return Optional.ofNullable(rutaBusDTOList);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RutaBusDTO> findById(Long id) {
        Optional<RutaBus> rutaBus = rutaBusRepository.findById(id);
        if (rutaBus.isEmpty()) throw new NotFoundInformationException();

        RutaBusDTO rutaBusDTO = MapperUtils.DtoFromEntity(rutaBus.get(), RutaBusDTO.class);
        return Optional.ofNullable(rutaBusDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<RutaBusDTO>> findByNombre(String nombre) {
        List<RutaBus> rutaBusList = rutaBusRepository.findByNombre(nombre);
        List<RutaBusDTO> rutaBusDTOList = MapperUtils.DtoListFromEntityList(rutaBusList, RutaBusDTO.class);
        return Optional.ofNullable(rutaBusDTOList);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<RutaBusDTO>> findByInicio(String inicio) {
        List<RutaBus> rutaBusList = rutaBusRepository.findByInicio(inicio);
        List<RutaBusDTO> rutaBusDTOList = MapperUtils.DtoListFromEntityList(rutaBusList, RutaBusDTO.class);
        return Optional.ofNullable(rutaBusDTOList);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<RutaBusDTO>> findByFin(String fin) {
        List<RutaBus> rutaBusList = rutaBusRepository.findByFin(fin);
        List<RutaBusDTO> rutaBusDTOList = MapperUtils.DtoListFromEntityList(rutaBusList, RutaBusDTO.class);
        return Optional.ofNullable(rutaBusDTOList);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<RutaBusDTO>> findByServicioId(@Param("id")Long id) {
        List<RutaBus> rutaBusList = rutaBusRepository.findByServicioId(id);
        List<RutaBusDTO> rutaBusDTOList = MapperUtils.DtoListFromEntityList(rutaBusList, RutaBusDTO.class);
        return Optional.ofNullable(rutaBusDTOList);
    }

    @Override
    @Transactional
    public Optional<RutaBusDTO> create(RutaBusDTO rutaBusDTO) {
        return Optional.ofNullable(getSavedRutaBusDTO(rutaBusDTO));
    }

    @Override
    @Transactional
    public Optional<RutaBusDTO> update(RutaBusDTO rutaBusDTO, Long id) {
        if (rutaBusRepository.findById(id).isEmpty()) throw new NotFoundInformationException();

        return Optional.ofNullable(getSavedRutaBusDTO(rutaBusDTO));
    }

    @Override
    @Transactional
    public void delete(Long id) {rutaBusRepository.deleteById(id);}

    @Override
    @Transactional
    public void deleteAll() {rutaBusRepository.deleteAll();}

    private RutaBusDTO getSavedRutaBusDTO(RutaBusDTO rutaBusDTO) {
        RutaBus rutaBus = MapperUtils.EntityFromDto(rutaBusDTO, RutaBus.class);
        RutaBus rutaBusCreated = rutaBusRepository.save(rutaBus);
        return MapperUtils.DtoFromEntity(rutaBusCreated, RutaBusDTO.class);
    }
}
