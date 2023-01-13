package org.una.municipalidad.services;


import org.springframework.data.repository.query.Param;
import org.una.municipalidad.dto.CementerioDTO;
import org.una.municipalidad.dto.RutaBusDTO;


import java.util.List;
import java.util.Optional;

public interface ICementerioService {

    public Optional<List<CementerioDTO>> findAll();

    public Optional<CementerioDTO> findById(Long id);

    public Optional<List<CementerioDTO>> findBySector(String sector);

    public Optional<List<CementerioDTO>> findByOcupado(String ocupado);

    public Optional<List<CementerioDTO>> findByServicioId(@Param("id")Long id);

    public Optional<CementerioDTO> create(CementerioDTO cementerioDTO);

    public Optional<CementerioDTO> update(CementerioDTO cementerioDTO, Long id);

    public void delete(Long id);

    public void deleteAll();
}
