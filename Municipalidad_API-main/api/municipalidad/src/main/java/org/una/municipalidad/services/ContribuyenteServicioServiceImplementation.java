package org.una.municipalidad.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.una.municipalidad.dto.ContribuyenteServicioDTO;
import org.una.municipalidad.entities.ContribuyenteServicio;
import org.una.municipalidad.exceptions.NotFoundInformationException;
import org.una.municipalidad.repositories.IContribuyenteServicioRepository;
import org.una.municipalidad.utils.MapperUtils;

import java.util.List;
import java.util.Optional;

@Service
public class ContribuyenteServicioServiceImplementation implements IContribuyenteServicioService {

    @Autowired
    private IContribuyenteServicioRepository contribuyenteServicioRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<List<ContribuyenteServicioDTO>> findAll() {
        List<ContribuyenteServicioDTO> contribuyenteServicioDTOList = MapperUtils.DtoListFromEntityList(contribuyenteServicioRepository.findAll(), ContribuyenteServicioDTO.class);
        return Optional.ofNullable(contribuyenteServicioDTOList);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ContribuyenteServicioDTO> findById(Long id) {
        Optional<ContribuyenteServicio> contribuyenteServicio = contribuyenteServicioRepository.findById(id);
        if (contribuyenteServicio.isEmpty()) throw new NotFoundInformationException();

        ContribuyenteServicioDTO contribuyenteServicioDTO = MapperUtils.DtoFromEntity(contribuyenteServicio.get(), ContribuyenteServicioDTO.class);
        return Optional.ofNullable(contribuyenteServicioDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<ContribuyenteServicioDTO>> findByPorcentaje(float porcentaje) {
        List<ContribuyenteServicio> contribuyenteList = contribuyenteServicioRepository.findByPorcentaje(porcentaje);
        List<ContribuyenteServicioDTO> contribuyenteDTOList = MapperUtils.DtoListFromEntityList(contribuyenteList , ContribuyenteServicioDTO.class);
        return Optional.ofNullable(contribuyenteDTOList);
    }

    @Override
    @Transactional
    public ContribuyenteServicioDTO create(ContribuyenteServicioDTO contribuyenteServicioDTO) {
        ContribuyenteServicio contribuyenteServicio = MapperUtils.EntityFromDto(contribuyenteServicioDTO, ContribuyenteServicio.class);
        contribuyenteServicio = contribuyenteServicioRepository.save(contribuyenteServicio);
        return MapperUtils.DtoFromEntity(contribuyenteServicio, ContribuyenteServicioDTO.class);
    }

    private ContribuyenteServicioDTO getSavedContribuyenteServicioDTO(ContribuyenteServicioDTO contribuyenteServicioDTO) {
        ContribuyenteServicio contribuyenteServicio = MapperUtils.EntityFromDto(contribuyenteServicioDTO, ContribuyenteServicio.class);
        ContribuyenteServicio contribuyenteServicioCreated = contribuyenteServicioRepository.save(contribuyenteServicio);
        return MapperUtils.DtoFromEntity(contribuyenteServicioCreated, ContribuyenteServicioDTO.class);
    }

    @Override
    @Transactional
    public Optional<ContribuyenteServicioDTO> update(ContribuyenteServicioDTO contribuyenteServicioDTO, Long id) {
        if (contribuyenteServicioRepository.findById(id).isEmpty()) throw new NotFoundInformationException();
        return Optional.ofNullable(getSavedContribuyenteServicioDTO(contribuyenteServicioDTO));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        contribuyenteServicioRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAll() {
        contribuyenteServicioRepository.deleteAll();
    }
}
