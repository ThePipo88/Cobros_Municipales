package org.una.municipalidad.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.una.municipalidad.dto.ListaSalidaDTO;
import org.una.municipalidad.dto.RutaBusDTO;
import org.una.municipalidad.dto.TipoDerechoDTO;
import org.una.municipalidad.entities.ListaSalida;
import org.una.municipalidad.entities.RutaBus;
import org.una.municipalidad.entities.TipoDerecho;
import org.una.municipalidad.exceptions.NotFoundInformationException;
import org.una.municipalidad.repositories.ITipoDerechoRepository;
import org.una.municipalidad.utils.MapperUtils;

import java.util.List;
import java.util.Optional;

@Service
public class TipoDerechoServiceImplementation implements ITipoDerechoService{

    @Autowired
    ITipoDerechoRepository tipoDerechoRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<List<TipoDerechoDTO>> findAll() {
        List<TipoDerechoDTO> tipoDerechoDTOList = MapperUtils.DtoListFromEntityList(tipoDerechoRepository.findAll(), TipoDerechoDTO.class);
        return Optional.ofNullable(tipoDerechoDTOList);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TipoDerechoDTO> findById(Long id) {
        Optional<TipoDerecho> tipoDerecho = tipoDerechoRepository.findById(id);
        if (tipoDerecho.isEmpty()) throw new NotFoundInformationException();

        TipoDerechoDTO tipoDerechoDTO = MapperUtils.DtoFromEntity(tipoDerecho.get(), TipoDerechoDTO.class);
        return Optional.ofNullable(tipoDerechoDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<TipoDerechoDTO>> findByTipo(String tipo) {
        List<TipoDerecho> tipoDerechoslist = tipoDerechoRepository.findByTipo(tipo);
        List<TipoDerechoDTO> tipoDerechoDTOList = MapperUtils.DtoListFromEntityList(tipoDerechoslist, TipoDerechoDTO.class);
        return Optional.ofNullable(tipoDerechoDTOList);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<TipoDerechoDTO>> findByMonto(int monto) {
        List<TipoDerecho> tipoDerechoslist = tipoDerechoRepository.findByMonto(monto);
        List<TipoDerechoDTO> tipoDerechoDTOList = MapperUtils.DtoListFromEntityList(tipoDerechoslist, TipoDerechoDTO.class);
        return Optional.ofNullable(tipoDerechoDTOList);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<TipoDerechoDTO>> findByServicioId(@Param("id")Long id) {
        List<TipoDerecho> rutaBusList = tipoDerechoRepository.findByServicioId(id);
        List<TipoDerechoDTO> rutaBusDTOList = MapperUtils.DtoListFromEntityList(rutaBusList, TipoDerechoDTO.class);
        return Optional.ofNullable(rutaBusDTOList);
    }

    @Override
    @Transactional
    public Optional<TipoDerechoDTO> create(TipoDerechoDTO tipoDerechoDTO) {
        return Optional.ofNullable(getSavedTipoDerechoDTO(tipoDerechoDTO));
    }

    @Override
    @Transactional
    public Optional<TipoDerechoDTO> update(TipoDerechoDTO tipoDerechoDTO, Long id) {
        if (tipoDerechoRepository.findById(id).isEmpty()) throw new NotFoundInformationException();

        return Optional.ofNullable(getSavedTipoDerechoDTO(tipoDerechoDTO));
    }

    @Override
    @Transactional
    public void delete(Long id) {tipoDerechoRepository.deleteById(id);}

    @Override
    @Transactional
    public void deleteAll() {tipoDerechoRepository.deleteAll();}

    private TipoDerechoDTO getSavedTipoDerechoDTO(TipoDerechoDTO tipoDerechoDTO) {
        TipoDerecho tipoDerecho = MapperUtils.EntityFromDto(tipoDerechoDTO, TipoDerecho.class);
        TipoDerecho tipoDerechoCreated = tipoDerechoRepository.save(tipoDerecho);
        return MapperUtils.DtoFromEntity(tipoDerechoCreated, TipoDerechoDTO.class);
    }
}
