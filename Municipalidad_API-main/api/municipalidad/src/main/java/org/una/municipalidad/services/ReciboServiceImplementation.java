package org.una.municipalidad.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.una.municipalidad.dto.PropiedadDTO;
import org.una.municipalidad.dto.ReciboDTO;
import org.una.municipalidad.entities.Propiedad;
import org.una.municipalidad.entities.Recibo;
import org.una.municipalidad.exceptions.NotFoundInformationException;
import org.una.municipalidad.repositories.IPropiedadRepository;
import org.una.municipalidad.repositories.IReciboRepository;
import org.una.municipalidad.utils.MapperUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReciboServiceImplementation implements IReciboService{

    @Autowired
    private IReciboRepository reciboRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<List<ReciboDTO>> findAll() {
        List<ReciboDTO> reciboDTOList = MapperUtils.DtoListFromEntityList(reciboRepository.findAll(), ReciboDTO.class);
        return Optional.ofNullable(reciboDTOList);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ReciboDTO> findById(Long id) {
        Optional<Recibo> recibo = reciboRepository.findById(id);
        if (recibo.isEmpty()) throw new NotFoundInformationException();

        ReciboDTO reciboDTO = MapperUtils.DtoFromEntity(recibo.get(), ReciboDTO.class);
        return Optional.ofNullable(reciboDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<ReciboDTO>> findByContribuyente(String contribuyente) {
        List<Recibo> reciboList = reciboRepository.findByContribuyente(contribuyente);
        List<ReciboDTO> reciboDTOList = MapperUtils.DtoListFromEntityList(reciboList, ReciboDTO.class);
        return Optional.ofNullable(reciboDTOList);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<ReciboDTO>> findByFechaEmision(Date fechaEmision) {
        List<Recibo> reciboList = reciboRepository.findByFechaEmision(fechaEmision);
        List<ReciboDTO> reciboDTOList = MapperUtils.DtoListFromEntityList(reciboList, ReciboDTO.class);
        return Optional.ofNullable(reciboDTOList);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<ReciboDTO>> findByReciboBetweenFecha(Date startDate, Date endDate) {
        List<Recibo> reciboList = reciboRepository.findByReciboBetweenFecha(startDate,endDate);
        List<ReciboDTO> reciboDTOList = MapperUtils.DtoListFromEntityList(reciboList, ReciboDTO.class);
        return Optional.ofNullable(reciboDTOList);
    }

    @Override
    @Transactional
    public ReciboDTO create(ReciboDTO reciboDTO) {
        Recibo recibo = MapperUtils.EntityFromDto(reciboDTO, Recibo.class);
        recibo = reciboRepository.save(recibo);
        return MapperUtils.DtoFromEntity(recibo, ReciboDTO.class);
    }

    @Override
    @Transactional
    public Optional<ReciboDTO> update(ReciboDTO reciboDTO, Long id) {
        if (reciboRepository.findById(id).isEmpty()) throw new NotFoundInformationException();
        return Optional.ofNullable(getSavedReciboDTO(reciboDTO));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        reciboRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAll() {
        reciboRepository.deleteAll();
    }



    private ReciboDTO getSavedReciboDTO(ReciboDTO reciboDTO) {
        Recibo recibo = MapperUtils.EntityFromDto(reciboDTO, Recibo.class);
        Recibo reciboCreated = reciboRepository.save(recibo);
        return MapperUtils.DtoFromEntity(reciboCreated, ReciboDTO.class);
    }

}
