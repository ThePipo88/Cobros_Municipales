package org.una.municipalidad.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.una.municipalidad.dto.RolDTO;
import org.una.municipalidad.dto.UsuarioDTO;
import org.una.municipalidad.services.IRolService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/roles")
@Api(tags = {"Roles"})
public class RolController {

    @Autowired
    private IRolService rolService;

    @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('AUDITOR')")
    @ApiOperation(value = "Obtiene un rol a partir de su id", response = RolDTO.class, tags = "Roles")
    @GetMapping("/byId/{id}")
    public ResponseEntity<?> findById(@PathVariable(value = "id") Long id) {
        try {
            Optional<RolDTO> rolFound = rolService.findById(id);
            return new ResponseEntity<>(rolFound, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('AUDITOR')")
    @ApiOperation(value = "Obtiene un rol a partir de su estado", response = RolDTO.class, tags = "Roles")
    @GetMapping("/byEstado/{estado}")
    public ResponseEntity<?> findByEstado(@PathVariable(value = "estado") boolean estado) {
        try {
            Optional<List<RolDTO>> result = rolService.findByEstado(estado);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/byFechaCreacion/{startDate}/{endDate}")
    @ApiOperation(value = "Obtiene una lista de roles entre dos fechas dadas", response = RolDTO.class, responseContainer = "RolesDTO", tags = "Roles")
    public ResponseEntity<?> findByFechaCreacionBetween(@PathVariable(value = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate, @PathVariable(value = "endDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        try {
            Optional<List<RolDTO>> result = rolService.findByFechaCreacionBetween(startDate, endDate);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('AUDITOR')")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Se crea un nuevo rol", response = RolDTO.class, tags = "Rol")
    @PostMapping("/")
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody RolDTO rolDTO) {
        try{
            RolDTO rolCreated = rolService.create(rolDTO);
            return new ResponseEntity<>(rolCreated, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @ApiOperation(value = "Se modifica un rol a partir de su id", response = RolDTO.class, tags = "Rol")
    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> update(@PathVariable(value = "id") Long id, @RequestBody RolDTO rolModified) {
        try {
            Optional<RolDTO> rolUpdated = rolService.update(rolModified, id);
            return new ResponseEntity<>(rolUpdated, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @ApiOperation(value = "Se elimina un rol a partir de su id", response = RolDTO.class, tags = "Rol")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) throws Exception {
        try {
            rolService.delete(id);
            return new ResponseEntity<>("Ok", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @ApiOperation(value = "Se eliminan todos los roles", response = RolDTO.class, tags = "Rol")
    @DeleteMapping("/")
    public ResponseEntity<?> deleteAll() throws Exception {
        try {
            rolService.deleteAll();
            return new ResponseEntity<>("Ok", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
