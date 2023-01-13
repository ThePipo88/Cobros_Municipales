package org.una.municipalidad.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.una.municipalidad.dto.CobroCanceladoDTO;
import org.una.municipalidad.dto.CobroGeneradoDTO;
import org.una.municipalidad.entities.CobroCancelado;
import org.una.municipalidad.exceptions.NotFoundInformationException;
import org.una.municipalidad.repositories.ICobroCanceladoRepository;
import org.una.municipalidad.utils.MapperUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CobroCanceladoServiceImplementation implements ICobroCanceladoService{

    @Autowired
    private ICobroCanceladoRepository cobroCanceladoRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<List<CobroCanceladoDTO>> findAll() {
            List<CobroCanceladoDTO> cobroCanceladoDTOList = MapperUtils.DtoListFromEntityList(cobroCanceladoRepository.findAll(), CobroCanceladoDTO.class);
            return Optional.ofNullable(cobroCanceladoDTOList);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CobroCanceladoDTO> findById(Long id) {
            Optional<CobroCancelado> cobroCancelado = cobroCanceladoRepository.findById(id);
            if (cobroCancelado.isEmpty()) throw new NotFoundInformationException();

            CobroCanceladoDTO cobroCanceladoDTO = MapperUtils.DtoFromEntity(cobroCancelado.get(), CobroCanceladoDTO.class);
            return Optional.ofNullable(cobroCanceladoDTO);
    }

    /*
    @Override
    @Transactional(readOnly = true)
    public Optional<List<CobroCanceladoDTO>> findByObjetoAndFechaCreacionBetween(String objetoId, Date startDate) {
        List<CobroCancelado> cobroCanceladoList = cobroCanceladoRepository.findByObjetoAndFechaCreacionBetween(objetoId,startDate);
        List<CobroCanceladoDTO> cobroCanceladoDTOList = MapperUtils.DtoListFromEntityList(cobroCanceladoList, CobroCanceladoDTO.class);
        return Optional.ofNullable(cobroCanceladoDTOList);
    }
*/
    @Override
    @Transactional(readOnly = true)
    public Optional<List<CobroCanceladoDTO>> findByFechaCreacionBetween(@Param("startDate")Date startDate, @Param("endDate")Date endDate) {
        List<CobroCancelado> cobroCanceladoList = cobroCanceladoRepository.findByCobroBetweenFecha(startDate,endDate);
        List<CobroCanceladoDTO> cobroCanceladoDTOList = MapperUtils.DtoListFromEntityList(cobroCanceladoList, CobroCanceladoDTO.class);
        return Optional.ofNullable(cobroCanceladoDTOList);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<CobroCanceladoDTO>> findByCobroBetweenCedulaAndFecha(@Param("cedula")String cedula,@Param("startDate") Date startDate,@Param("endDate") Date endDate) {
        List<CobroCancelado> cobroCanceladoList = cobroCanceladoRepository.findByCobroBetweenCedulaAndFecha(cedula,startDate,endDate);
        List<CobroCanceladoDTO> cobroCanceladoDTOList = MapperUtils.DtoListFromEntityList(cobroCanceladoList,CobroCanceladoDTO.class);
        return Optional.ofNullable(cobroCanceladoDTOList);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<CobroCanceladoDTO>> findByFechaCreacion(Date startDate) {
        List<CobroCancelado> cobroCanceladoList = cobroCanceladoRepository.findByFechaCreacion(startDate);
        List<CobroCanceladoDTO> cobroCanceladoDTOList = MapperUtils.DtoListFromEntityList(cobroCanceladoList, CobroCanceladoDTO.class);
        return Optional.ofNullable(cobroCanceladoDTOList);
    }


    @Override
    @Transactional
    public Optional<CobroCanceladoDTO> create(CobroCanceladoDTO cobroCanceladoDTO) {
        return Optional.ofNullable(getSavedCobroCanceladoDTO(cobroCanceladoDTO));
    }

    @Override
    @Transactional
    public Optional<CobroCanceladoDTO> update(CobroCanceladoDTO cobroCanceladoDTO, Long id) {
        if (cobroCanceladoRepository.findById(id).isEmpty()) throw new NotFoundInformationException();

        return Optional.ofNullable(getSavedCobroCanceladoDTO(cobroCanceladoDTO));
    }

    @Override
    @Transactional
    public void delete(Long id) {cobroCanceladoRepository.findById(id);}

    @Override
    @Transactional
    public void deleteAll() {cobroCanceladoRepository.deleteAll();}

    private CobroCanceladoDTO getSavedCobroCanceladoDTO(CobroCanceladoDTO cobroCanceladoDTO) {
        CobroCancelado cobroCancelado = MapperUtils.EntityFromDto(cobroCanceladoDTO, CobroCancelado.class);
        CobroCancelado cobroCanceladoCreated = cobroCanceladoRepository.save(cobroCancelado);
        return MapperUtils.DtoFromEntity(cobroCanceladoCreated, CobroCanceladoDTO.class);
    }

   @Override
    @Transactional(readOnly = true)
    public Optional<List<CobroCanceladoDTO>> findByCobroBetweenFecha(@Param("startDate")Date startDate,@Param("endDate")Date endDate) {
        List<CobroCancelado> cobroCanceladoList = cobroCanceladoRepository.findByCobroBetweenFecha(startDate,endDate);
        List<CobroCanceladoDTO> cobroCanceladoDTOList = MapperUtils.DtoListFromEntityList(cobroCanceladoList, CobroCanceladoDTO.class);
        return Optional.ofNullable(cobroCanceladoDTOList);
    }
}
