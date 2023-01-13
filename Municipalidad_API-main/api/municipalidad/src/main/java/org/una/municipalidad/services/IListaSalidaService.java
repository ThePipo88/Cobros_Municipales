package org.una.municipalidad.services;

import org.springframework.data.repository.query.Param;
import org.una.municipalidad.dto.CementerioDTO;
import org.una.municipalidad.dto.ListaSalidaDTO;

import java.util.List;
import java.util.Optional;

public interface IListaSalidaService {

    public Optional<List<ListaSalidaDTO>> findAll();

    public Optional<ListaSalidaDTO> findById(Long id);

    public Optional<List<ListaSalidaDTO>> findByDia(String dia);

    public Optional<List<ListaSalidaDTO>> findByCantidad(int cantidad);

    public Optional<List<ListaSalidaDTO>> findByRutaId(@Param("id")Long id);

    public Optional<ListaSalidaDTO> create(ListaSalidaDTO listaSalidaDTO);

    public Optional<ListaSalidaDTO> update(ListaSalidaDTO listaSalidaDTO, Long id);

    public void delete(Long id);

    public void deleteAll();

}
