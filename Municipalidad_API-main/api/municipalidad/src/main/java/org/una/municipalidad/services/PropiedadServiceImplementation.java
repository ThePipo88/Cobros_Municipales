package org.una.municipalidad.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.una.municipalidad.dto.PropiedadDTO;
import org.una.municipalidad.dto.UsuarioDTO;
import org.una.municipalidad.entities.Propiedad;
import org.una.municipalidad.entities.Usuario;
import org.una.municipalidad.exceptions.NotFoundInformationException;
import org.una.municipalidad.repositories.IPropiedadRepository;
import org.una.municipalidad.repositories.IUsuarioRepository;
import org.una.municipalidad.utils.MapperUtils;

import java.util.List;
import java.util.Optional;

@Service
public class PropiedadServiceImplementation implements IPropiedadService{

    @Autowired
    private IPropiedadRepository propiedadRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<List<PropiedadDTO>> findAll() {
        List<PropiedadDTO> propiedadDTOList = MapperUtils.DtoListFromEntityList(propiedadRepository.findAll(), PropiedadDTO.class);
        return Optional.ofNullable(propiedadDTOList);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PropiedadDTO> findById(Long id) {
        Optional<Propiedad> propiedad = propiedadRepository.findById(id);
        if (propiedad.isEmpty()) throw new NotFoundInformationException();

        PropiedadDTO propiedadDTO = MapperUtils.DtoFromEntity(propiedad.get(), PropiedadDTO.class);
        return Optional.ofNullable(propiedadDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<PropiedadDTO>> findByProvincia(String provincia) {
        List<Propiedad> propiedadList = propiedadRepository.findByProvincia(provincia);
        List<PropiedadDTO> propiedadDTOList = MapperUtils.DtoListFromEntityList(propiedadList, PropiedadDTO.class);
        return Optional.ofNullable(propiedadDTOList);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<PropiedadDTO>> findByCanton(String canton) {
        List<Propiedad> propiedadList = propiedadRepository.findByCanton(canton);
        List<PropiedadDTO> propiedadDTOList = MapperUtils.DtoListFromEntityList(propiedadList, PropiedadDTO.class);
        return Optional.ofNullable(propiedadDTOList);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<PropiedadDTO>> findByDistrito(String distrito) {
        List<Propiedad> propiedadList = propiedadRepository.findByDistrito(distrito);
        List<PropiedadDTO> propiedadDTOList = MapperUtils.DtoListFromEntityList(propiedadList, PropiedadDTO.class);
        return Optional.ofNullable(propiedadDTOList);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<PropiedadDTO>> findByZona(String zona) {
        List<Propiedad> propiedadList = propiedadRepository.findByZona(zona);
        List<PropiedadDTO> propiedadDTOList = MapperUtils.DtoListFromEntityList(propiedadList, PropiedadDTO.class);
        return Optional.ofNullable(propiedadDTOList);
    }

    @Override
    @Transactional
    public PropiedadDTO create(PropiedadDTO propiedadDTO) {
        Propiedad propiedad = MapperUtils.EntityFromDto(propiedadDTO, Propiedad.class);
        propiedad = propiedadRepository.save(propiedad);
        return MapperUtils.DtoFromEntity(propiedad, PropiedadDTO.class);
    }

    @Override
    @Transactional
    public Optional<PropiedadDTO> update(PropiedadDTO propiedadDTO, Long id) {
        if (propiedadRepository.findById(id).isEmpty()) throw new NotFoundInformationException();
        return Optional.ofNullable(getSavedPropiedadDTO(propiedadDTO));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        propiedadRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAll() {
        propiedadRepository.deleteAll();
    }

    private PropiedadDTO getSavedPropiedadDTO(PropiedadDTO propiedadDTO) {
        Propiedad propiedad = MapperUtils.EntityFromDto(propiedadDTO, Propiedad.class);
        Propiedad propiedadCreated = propiedadRepository.save(propiedad);
        return MapperUtils.DtoFromEntity(propiedadCreated, PropiedadDTO.class);
    }
}
