package org.una.municipalidad.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.una.municipalidad.dto.TipoDerechoDTO;
import org.una.municipalidad.dto.TransaccionDTO;
import org.una.municipalidad.dto.UsuarioDTO;
import org.una.municipalidad.entities.TipoDerecho;
import org.una.municipalidad.entities.Transaccion;
import org.una.municipalidad.exceptions.NotFoundInformationException;
import org.una.municipalidad.repositories.ITransaccionRepository;
import org.una.municipalidad.utils.MapperUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TransaccionServiceImplemetation implements ITransaccionService{

    @Autowired
    private ITransaccionRepository transaccionRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<TransaccionDTO> findById(Long id) {
        Optional<Transaccion> transaccion = transaccionRepository.findById(id);
        if (transaccion.isEmpty()) throw new NotFoundInformationException();

        TransaccionDTO transaccionDTO = MapperUtils.DtoFromEntity(transaccion.get(), TransaccionDTO.class);
        return Optional.ofNullable(transaccionDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<TransaccionDTO>> findAll() {
        List<TransaccionDTO> usuarioDTOList = MapperUtils.DtoListFromEntityList(transaccionRepository.findAll(), TransaccionDTO.class);
        return Optional.ofNullable(usuarioDTOList);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<TransaccionDTO>> findByUsuarioIdAndFechaCreacionBetween(Long usuarioId, Date startDate, Date endDate) {
        List<Transaccion> transaccionList = transaccionRepository.findByUsuarioIdAndFechaCreacionBetween(usuarioId,startDate,endDate);
        List<TransaccionDTO> transaccionDTOList = MapperUtils.DtoListFromEntityList(transaccionList, TransaccionDTO.class);
        return Optional.ofNullable(transaccionDTOList);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<TransaccionDTO>> findByObjetoAndFechaCreacionBetween(String objetoId, Date startDate, Date endDate) {
        List<Transaccion> transaccionList = transaccionRepository.findByObjetoAndFechaCreacionBetween(objetoId,startDate,endDate);
        List<TransaccionDTO> transaccionDTOList = MapperUtils.DtoListFromEntityList(transaccionList, TransaccionDTO.class);
        return Optional.ofNullable(transaccionDTOList);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<TransaccionDTO>> findByFechaCreacionBetween(Date startDate, Date endDate) {
        List<Transaccion> transaccionList = transaccionRepository.findByFechaCreacionBetween(startDate,endDate);
        List<TransaccionDTO> transaccionDTOList = MapperUtils.DtoListFromEntityList(transaccionList, TransaccionDTO.class);
        return Optional.ofNullable(transaccionDTOList);
    }

    @Override
    @Transactional
    public TransaccionDTO create(TransaccionDTO transaccionDTO) {
        Transaccion transaccion = MapperUtils.EntityFromDto(transaccionDTO, Transaccion.class);
        transaccion = transaccionRepository.save(transaccion);
        return MapperUtils.DtoFromEntity(transaccion, TransaccionDTO.class);
    }

    @Override
    @Transactional
    public Optional<TransaccionDTO> update(TransaccionDTO transaccionDTO, Long id) {
        if (transaccionRepository.findById(id).isEmpty()) throw new NotFoundInformationException();

        return Optional.ofNullable(getSavedTransaccionDTO(transaccionDTO));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        transaccionRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAll() {
        transaccionRepository.deleteAll();
    }

    private TransaccionDTO getSavedTransaccionDTO(TransaccionDTO transaccionDTO) {
        Transaccion transaccion = MapperUtils.EntityFromDto(transaccionDTO, Transaccion.class);
        Transaccion transaccionCreated = transaccionRepository.save(transaccion);
        return MapperUtils.DtoFromEntity(transaccionCreated, TransaccionDTO.class);
    }
}
