package org.una.municipalidad.services;

import org.springframework.data.repository.query.Param;
import org.una.municipalidad.dto.AuthenticationRequest;
import org.una.municipalidad.dto.AuthenticationResponse;
import org.una.municipalidad.dto.UsuarioDTO;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {

    public Optional<List<UsuarioDTO>> findAll();

    public Optional<UsuarioDTO> findById(Long id);

    public Optional<List<UsuarioDTO>> findByCedulaAproximate(String cedula);

    public Optional<UsuarioDTO> findByCedula(String cedula);

    public Optional<List<UsuarioDTO>> findByNombreCompletoAproximateIgnoreCase(String nombreCompleto);

    public Optional<UsuarioDTO> findNombreCompletoWithLikeSQL(@Param("nombreCompleto")String nombreCompleto);

    public Optional<UsuarioDTO> create(UsuarioDTO usuarioDTO);

    public Optional<UsuarioDTO> update(UsuarioDTO usuarioDTO, Long id);

    public void delete(Long id);

    public void deleteAll();
   
}
