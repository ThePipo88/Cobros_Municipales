package org.una.municipalidad.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.una.municipalidad.dto.UsuarioDTO;
import org.una.municipalidad.services.IUsuarioService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
@Api(tags = {"Usuarios"})
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('AUDITOR')")
    @ApiOperation(value = "Obtiene una lista de todos los usuarios", response = UsuarioDTO.class, responseContainer = "List", tags = "Usuarios")
    @GetMapping()
    public @ResponseBody
    ResponseEntity<?> findAll() {
        try{
            Optional<List<UsuarioDTO>> result = usuarioService.findAll();
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('AUDITOR')")
    @ApiOperation(value = "Obtiene un usuario a partir de su id", response = UsuarioDTO.class, tags = "Usuarios")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable(value = "id") Long id) {
        try{
            Optional<UsuarioDTO> usuarioFound = usuarioService.findById(id);
            return new ResponseEntity<>(usuarioFound, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('AUDITOR')")
    @ApiOperation(value = "Obtiene un usuario a partir de su cedula", response = UsuarioDTO.class, responseContainer = "List", tags = "Usuarios")
    @GetMapping("/cedula/{term}")
    public ResponseEntity<?> findByCedulaAproximate(@PathVariable(value = "term") String term) {
        try{
            Optional<List<UsuarioDTO>> result = usuarioService.findByCedulaAproximate(term);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('AUDITOR')")
    @ApiOperation(value = "Obtiene un usuario a partir de su nombre", response = UsuarioDTO.class, responseContainer = "List", tags = "Usuario")
    @GetMapping("/nombre/{term}")
    public ResponseEntity<?> findByNombreCompletoAproximateIgnoreCase(@PathVariable(value = "term") String term) {
        try{
            Optional<List<UsuarioDTO>> result = usuarioService.findByNombreCompletoAproximateIgnoreCase(term);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Se crea un usuario", response = UsuarioDTO.class, tags = "Usuario")
    @PostMapping("/")
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody UsuarioDTO usuarioDTO) {
        try{
            Optional<UsuarioDTO> usuarioCreated = usuarioService.create(usuarioDTO);
            return new ResponseEntity<>(usuarioCreated, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @ApiOperation(value = "Se modifica un usuario a partir de su id", response = UsuarioDTO.class, tags = "Usuario")
    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> update(@PathVariable(value = "id") Long id, @RequestBody UsuarioDTO usuarioModified) {
        try{
            Optional<UsuarioDTO> usuarioUpdated = usuarioService.update(usuarioModified, id);
            return new ResponseEntity<>(usuarioUpdated, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @ApiOperation(value = "Se elimina un usuario a partir de su id", response = UsuarioDTO.class, tags = "Usuario")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) throws Exception {
        try{
            usuarioService.delete(id);
            return new ResponseEntity<>("Ok", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @ApiOperation(value = "Se eliminan todos los usuarios", response = UsuarioDTO.class, tags = "Usuarios")
    @DeleteMapping("/")
    public ResponseEntity<?> deleteAll() throws Exception {
        try{
            usuarioService.deleteAll();
            return new ResponseEntity<>("Ok", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
