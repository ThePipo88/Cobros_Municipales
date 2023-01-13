package org.una.municipalidad.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.una.municipalidad.dto.ContribuyenteDTO;
import org.una.municipalidad.entities.Contribuyente;
import org.una.municipalidad.exceptions.NotFoundInformationException;
import org.una.municipalidad.repositories.IContribuyenteRepository;
import org.una.municipalidad.utils.MapperUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ContribuyenteServiceImplementation implements IContribuyenteService{

    @Autowired
    private IContribuyenteRepository contribuyenteRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<List<ContribuyenteDTO>> findAll() {
        List<ContribuyenteDTO> contribuyenteDTOList = MapperUtils.DtoListFromEntityList(contribuyenteRepository.findAll(), ContribuyenteDTO.class);
        return Optional.ofNullable(contribuyenteDTOList);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ContribuyenteDTO> findById(Long id) {
        Optional<Contribuyente> contribuyente = contribuyenteRepository.findById(id);
        if (contribuyente.isEmpty()) throw new NotFoundInformationException();

        ContribuyenteDTO contribuyenteDTO = MapperUtils.DtoFromEntity(contribuyente.get(), ContribuyenteDTO.class);
        return Optional.ofNullable(contribuyenteDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<ContribuyenteDTO>> findByNombreCompletoAproximateIgnoreCase(String nombre) {
        List<Contribuyente> contribuyenteList = contribuyenteRepository.findByNombreContainingIgnoreCase(nombre);
        List<ContribuyenteDTO> contribuyenteDTOList = MapperUtils.DtoListFromEntityList(contribuyenteList, ContribuyenteDTO.class);
        return Optional.ofNullable(contribuyenteDTOList);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<ContribuyenteDTO>> findByFechaNacimiento(Date fechaNacimiento) {
        List<Contribuyente> contribuyenteList = contribuyenteRepository.findByFechaNacimiento(fechaNacimiento);
        List<ContribuyenteDTO> contribuyenteDTOList = MapperUtils.DtoListFromEntityList(contribuyenteList, ContribuyenteDTO.class);
        return Optional.ofNullable(contribuyenteDTOList);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<List<ContribuyenteDTO>> findByCorreo(String correoElectronico) {
        List<Contribuyente> contribuyenteList = contribuyenteRepository.findByCorreoElectronicoContainingIgnoreCase(correoElectronico);
        List<ContribuyenteDTO> contribuyenteDTOList = MapperUtils.DtoListFromEntityList(contribuyenteList, ContribuyenteDTO.class);
        return Optional.ofNullable(contribuyenteDTOList);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<ContribuyenteDTO>> findByDireccion(String direccion) {
        List<Contribuyente> contribuyenteList = contribuyenteRepository.findByDireccionContainingIgnoreCase(direccion);
        List<ContribuyenteDTO> contribuyenteDTOList = MapperUtils.DtoListFromEntityList(contribuyenteList, ContribuyenteDTO.class);
        return Optional.ofNullable(contribuyenteDTOList);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<ContribuyenteDTO>> findByTelefono(String telefono) {
        List<Contribuyente> contribuyenteList = contribuyenteRepository.findByTelefonoContainingIgnoreCase(telefono);
        List<ContribuyenteDTO> contribuyenteDTOList = MapperUtils.DtoListFromEntityList(contribuyenteList, ContribuyenteDTO.class);
        return Optional.ofNullable(contribuyenteDTOList);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ContribuyenteDTO> findByCedula(String cedula) {
        Optional<Contribuyente> contribuyente = Optional.ofNullable(contribuyenteRepository.findByCedula(cedula));
        ContribuyenteDTO contribuyenteDTO = MapperUtils.DtoFromEntity(contribuyente.get(), ContribuyenteDTO.class);
        return Optional.ofNullable(contribuyenteDTO);
    }

    @Override
    @Transactional
    public ContribuyenteDTO create(ContribuyenteDTO contribuyenteDTO) {
        Contribuyente contribuyente = MapperUtils.EntityFromDto(contribuyenteDTO, Contribuyente.class);
        contribuyente = contribuyenteRepository.save(contribuyente);
        return MapperUtils.DtoFromEntity(contribuyente, ContribuyenteDTO.class);
    }

    private ContribuyenteDTO getSavedContribuyenteDTO(ContribuyenteDTO contribuyenteDTO) {
        Contribuyente contribuyente = MapperUtils.EntityFromDto(contribuyenteDTO, Contribuyente.class);
        Contribuyente contribuyenteCreated = contribuyenteRepository.save(contribuyente);
        return MapperUtils.DtoFromEntity(contribuyenteCreated, ContribuyenteDTO.class);
    }

    @Override
    @Transactional
    public Optional<ContribuyenteDTO> update(ContribuyenteDTO contribuyenteDTO, Long id) {
        if (contribuyenteRepository.findById(id).isEmpty()) throw new NotFoundInformationException();
        return Optional.ofNullable(getSavedContribuyenteDTO(contribuyenteDTO));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        contribuyenteRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAll() {
        contribuyenteRepository.deleteAll();
    }
}
