package org.una.municipalidad.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.una.municipalidad.dto.ParametroDTO;
import org.una.municipalidad.entities.Parametro;
import org.una.municipalidad.exceptions.NotFoundInformationException;
import org.una.municipalidad.repositories.IParametroRepository;
import org.una.municipalidad.utils.MapperUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ParametroServiceImplementation implements IParametroService{

    @Autowired
    private IParametroRepository parametroRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<List<ParametroDTO>> findAll() {
        List<ParametroDTO> parametroDTOList = MapperUtils.DtoListFromEntityList(parametroRepository.findAll(), ParametroDTO.class);
        return Optional.ofNullable(parametroDTOList);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ParametroDTO> findById(Long id) {
        Optional<Parametro> parametro = parametroRepository.findById(id);
        if (parametro.isEmpty()) throw new NotFoundInformationException();

        ParametroDTO parametroDTO = MapperUtils.DtoFromEntity(parametro.get(), ParametroDTO.class);
        return Optional.ofNullable(parametroDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<ParametroDTO>> findByFormulaAproximate(String formula) {
        List<Parametro> parametroList = parametroRepository.findByFormula(formula);
        List<ParametroDTO> parametroDTOList = MapperUtils.DtoListFromEntityList(parametroList, ParametroDTO.class);
        return Optional.ofNullable(parametroDTOList);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<ParametroDTO>> findByNombreAproximate(String nombre) {
        List<Parametro> parametroList = parametroRepository.findByNombre(nombre);
        List<ParametroDTO> parametroDTOList = MapperUtils.DtoListFromEntityList(parametroList, ParametroDTO.class);
        return Optional.ofNullable(parametroDTOList);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<ParametroDTO>> findByEstadoAproximate(boolean estado) {
        List<Parametro> parametroList = parametroRepository.findByEstado(estado);
        List<ParametroDTO> parametroDTOList = MapperUtils.DtoListFromEntityList(parametroList, ParametroDTO.class);
        return Optional.ofNullable(parametroDTOList);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<ParametroDTO>> findByFechaCreacionBetween(Date startDate, Date endDate) {
        List<Parametro> parametroList = parametroRepository.findByFechaCreacionBetween(startDate,endDate);
        List<ParametroDTO> parametroDTOList = MapperUtils.DtoListFromEntityList(parametroList, ParametroDTO.class);
        return Optional.ofNullable(parametroDTOList);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<ParametroDTO>> findByFechaModificacionBetween(Date startDate, Date endDate) {
        List<Parametro> parametroList = parametroRepository.findByFechaModificacionBetween(startDate,endDate);
        List<ParametroDTO> parametroDTOList = MapperUtils.DtoListFromEntityList(parametroList, ParametroDTO.class);
        return Optional.ofNullable(parametroDTOList);
    }


    private ParametroDTO getSavedParametroDTO(ParametroDTO parametroDTO) {
        Parametro parametro = MapperUtils.EntityFromDto(parametroDTO, Parametro.class);
        Parametro parametroCreated = parametroRepository.save(parametro);
        return MapperUtils.DtoFromEntity(parametroCreated, ParametroDTO.class);
    }

    @Override
    @Transactional
    public ParametroDTO create(ParametroDTO parametroDTO) {
        Parametro parametro = MapperUtils.EntityFromDto(parametroDTO, Parametro.class);
        parametro = parametroRepository.save(parametro);
        return MapperUtils.DtoFromEntity(parametro, ParametroDTO.class);
    }

    @Override
    @Transactional
    public Optional<ParametroDTO> update(ParametroDTO parametroDTO, Long id) {
        if (parametroRepository.findById(id).isEmpty()) throw new NotFoundInformationException();
        return Optional.ofNullable(getSavedParametroDTO(parametroDTO));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        parametroRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAll() {
        parametroRepository.deleteAll();
    }
}
