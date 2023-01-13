package org.una.municipalidad.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.una.municipalidad.dto.CementerioDTO;
import org.una.municipalidad.dto.RutaBusDTO;
import org.una.municipalidad.entities.Cementerio;
import org.una.municipalidad.entities.RutaBus;
import org.una.municipalidad.exceptions.NotFoundInformationException;
import org.una.municipalidad.repositories.ICementerioRepository;
import org.una.municipalidad.utils.MapperUtils;

import java.util.List;
import java.util.Optional;

@Service
public class CementerioServiceImplementation implements  ICementerioService{

    @Autowired
    private ICementerioRepository cementerioRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<List<CementerioDTO>> findAll() {
        List<CementerioDTO> cementerioDTOList = MapperUtils.DtoListFromEntityList(cementerioRepository.findAll(), CementerioDTO.class);
        return Optional.ofNullable(cementerioDTOList);
    }
    @Override
    @Transactional(readOnly = true)
    public Optional<CementerioDTO> findById(Long id) {
        Optional<Cementerio> cementerio = cementerioRepository.findById(id);
        if (cementerio.isEmpty()) throw new NotFoundInformationException();

        CementerioDTO cementerioDTO = MapperUtils.DtoFromEntity(cementerio.get(), CementerioDTO.class);
        return Optional.ofNullable(cementerioDTO);
    }
    @Override
    @Transactional(readOnly = true)
    public Optional<List<CementerioDTO>> findBySector(String sector) {
        List<Cementerio> cementerioList = cementerioRepository.findBySector(sector);
        List<CementerioDTO> cementerioDTOList = MapperUtils.DtoListFromEntityList(cementerioList, CementerioDTO.class);
        return Optional.ofNullable(cementerioDTOList);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<CementerioDTO>> findByOcupado(String ocupado) {
        List<Cementerio> cementerioList = cementerioRepository.findByOcupado(ocupado);
        List<CementerioDTO> cementerioDTOList = MapperUtils.DtoListFromEntityList(cementerioList, CementerioDTO.class);
        return Optional.ofNullable(cementerioDTOList);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<CementerioDTO>> findByServicioId(@Param("id")Long id) {
        List<Cementerio> rutaBusList = cementerioRepository.findByServicioId(id);
        List<CementerioDTO> rutaBusDTOList = MapperUtils.DtoListFromEntityList(rutaBusList, CementerioDTO.class);
        return Optional.ofNullable(rutaBusDTOList);
    }

    @Override
    @Transactional
    public Optional<CementerioDTO> create(CementerioDTO cementerioDTO) {
        return Optional.ofNullable(getSavedCementerioDTO(cementerioDTO));
    }

    @Override
    @Transactional
    public Optional<CementerioDTO> update(CementerioDTO cementerioDTO, Long id) {
        if (cementerioRepository.findById(id).isEmpty()) throw new NotFoundInformationException();

        return Optional.ofNullable(getSavedCementerioDTO(cementerioDTO));
    }

    @Override
    @Transactional
    public void delete(Long id) {cementerioRepository.deleteById(id);}

    @Override
    @Transactional
    public void deleteAll() {cementerioRepository.deleteAll();}

    private CementerioDTO getSavedCementerioDTO(CementerioDTO cementerioDTO) {
        Cementerio cementerio = MapperUtils.EntityFromDto(cementerioDTO, Cementerio.class);
        Cementerio cementerioCreated = cementerioRepository.save(cementerio);
        return MapperUtils.DtoFromEntity(cementerioCreated, CementerioDTO.class);
    }
}
