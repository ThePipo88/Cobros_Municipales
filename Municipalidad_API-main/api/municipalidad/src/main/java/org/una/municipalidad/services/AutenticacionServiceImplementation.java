package org.una.municipalidad.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.una.municipalidad.dto.AuthenticationRequest;
import org.una.municipalidad.dto.AuthenticationResponse;
import org.una.municipalidad.dto.RolDTO;
import org.una.municipalidad.dto.UsuarioDTO;
import org.una.municipalidad.exceptions.InvalidCredentialsException;
import org.una.municipalidad.jwt.JwtProvider;
import org.una.municipalidad.utils.MapperUtils;

import java.util.Optional;

@Repository
public class AutenticacionServiceImplementation implements IAutenticacionService{

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;

    public AutenticacionServiceImplementation() {
    }

    @Override
    @Transactional(readOnly = true)
    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {

        Optional<UsuarioDTO> usuario = usuarioService.findByCedula(authenticationRequest.getCedula());

        if (usuario.isPresent() &&  bCryptPasswordEncoder.matches(authenticationRequest.getPassword(),usuario.get().getPasswordEncriptado())) {
            AuthenticationResponse authenticationResponse = new AuthenticationResponse();
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getCedula(),
                    authenticationRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            authenticationResponse.setJwt(jwtProvider.generateToken(authenticationRequest));
            UsuarioDTO usuarioDto = MapperUtils.DtoFromEntity(usuario.get(), UsuarioDTO.class);
            authenticationResponse.setUsuarioDTO(usuarioDto);
            authenticationResponse.setRolDTO(RolDTO.builder().nombre(usuarioDto.getRol().getNombre()).build());

            return authenticationResponse;
        } else {
            throw new InvalidCredentialsException();
        }
    }
}
