package org.una.municipalidad.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.una.municipalidad.dto.CementerioDTO;
import org.una.municipalidad.dto.ListaSalidaDTO;
import org.una.municipalidad.entities.Cementerio;
import org.una.municipalidad.entities.ListaSalida;
import org.una.municipalidad.exceptions.NotFoundInformationException;
import org.una.municipalidad.repositories.IListaSalidaRepository;
import org.una.municipalidad.utils.MapperUtils;

import java.util.List;
import java.util.Optional;

@Service
public class ListaSalidaServiceImplementation implements IListaSalidaService{

    @Autowired
    IListaSalidaRepository listaSalidaRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<List<ListaSalidaDTO>> findAll() {
        List<ListaSalidaDTO> listaSalidaDTOList = MapperUtils.DtoListFromEntityList(listaSalidaRepository.findAll(), ListaSalidaDTO.class);
        return Optional.ofNullable(listaSalidaDTOList);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ListaSalidaDTO> findById(Long id) {
        Optional<ListaSalida> listaSalida = listaSalidaRepository.findById(id);
        if (listaSalida.isEmpty()) throw new NotFoundInformationException();

        ListaSalidaDTO listaSalidaDTO = MapperUtils.DtoFromEntity(listaSalida.get(), ListaSalidaDTO.class);
        return Optional.ofNullable(listaSalidaDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<ListaSalidaDTO>> findByDia(String dia) {
        List<ListaSalida> listaSalidaList = listaSalidaRepository.findByDia(dia);
        List<ListaSalidaDTO> listaSalidaDTOList = MapperUtils.DtoListFromEntityList(listaSalidaList, ListaSalidaDTO.class);
        return Optional.ofNullable(listaSalidaDTOList);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<ListaSalidaDTO>> findByCantidad(int cantidad) {
        List<ListaSalida> listaSalidaList = listaSalidaRepository.findByCantidad(cantidad);
        List<ListaSalidaDTO> listaSalidaDTOList = MapperUtils.DtoListFromEntityList(listaSalidaList, ListaSalidaDTO.class);
        return Optional.ofNullable(listaSalidaDTOList);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<ListaSalidaDTO>> findByRutaId(@Param("id")Long id) {
        List<ListaSalida> listaSalidaList = listaSalidaRepository.findByRutaId(id);
        List<ListaSalidaDTO> listaSalidaDTOList = MapperUtils.DtoListFromEntityList(listaSalidaList, ListaSalidaDTO.class);
        return Optional.ofNullable(listaSalidaDTOList);
    }

    @Override
    @Transactional
    public Optional<ListaSalidaDTO> create(ListaSalidaDTO listaSalidaDTO) {
        return Optional.ofNullable(getSavedListaSalidaDTO(listaSalidaDTO));
    }

    @Override
    @Transactional
    public Optional<ListaSalidaDTO> update(ListaSalidaDTO listaSalidaDTO, Long id) {
        if (listaSalidaRepository.findById(id).isEmpty()) throw new NotFoundInformationException();

        return Optional.ofNullable(getSavedListaSalidaDTO(listaSalidaDTO));
    }

    @Override
    @Transactional
    public void delete(Long id) {listaSalidaRepository.deleteById(id);}

    @Override
    @Transactional
    public void deleteAll() {listaSalidaRepository.deleteAll();}

    private ListaSalidaDTO getSavedListaSalidaDTO(ListaSalidaDTO listaSalidaDTO) {
        ListaSalida listaSalida = MapperUtils.EntityFromDto(listaSalidaDTO, ListaSalida.class);
        ListaSalida listaSalidaCreated = listaSalidaRepository.save(listaSalida);
        return MapperUtils.DtoFromEntity(listaSalidaCreated, ListaSalidaDTO.class);
    }
}
