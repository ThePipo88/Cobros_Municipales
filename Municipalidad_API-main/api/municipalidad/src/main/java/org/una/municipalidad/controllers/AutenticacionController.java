package org.una.municipalidad.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.una.municipalidad.dto.AuthenticationRequest;
import org.una.municipalidad.dto.AuthenticationResponse;
import org.una.municipalidad.dto.UsuarioDTO;
import org.una.municipalidad.exceptions.InvalidCredentialsException;
import org.una.municipalidad.exceptions.MissingInputsException;
import org.una.municipalidad.services.IAutenticacionService;

import javax.validation.Valid;

@RestController
@RequestMapping("/autenticacion")
@Api(tags = {"Autenticacion"})
public class AutenticacionController {

    @Autowired
    private IAutenticacionService autenticacionService;

    @ApiOperation(value = "Inicio de sesión para conseguir un token de acceso", response = UsuarioDTO.class, tags = "Seguridad")
    @PostMapping("")
    @ResponseBody
    public ResponseEntity<?> login(@Valid @RequestBody AuthenticationRequest authenticationRequest, BindingResult bindingResult) {
        try{
            if (bindingResult.hasErrors()) { throw new MissingInputsException();  }
            AuthenticationResponse authenticationResponse = new AuthenticationResponse();
            AuthenticationResponse token = autenticacionService.login(authenticationRequest);
            if (token.getJwt() != null) {
                return new ResponseEntity(autenticacionService.login(authenticationRequest), HttpStatus.OK);
            } else {
                throw new InvalidCredentialsException();
            }
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
