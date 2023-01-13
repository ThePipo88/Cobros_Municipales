package org.una.municipalidad.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.una.municipalidad.dto.ExcepcionDTO;
import org.una.municipalidad.dto.ListaSalidaDTO;
import org.una.municipalidad.dto.PropiedadDTO;
import org.una.municipalidad.entities.Excepcion;
import org.una.municipalidad.exceptions.NotFoundInformationException;
import org.una.municipalidad.services.IExcepcionService;
import org.una.municipalidad.services.IListaSalidaService;
import org.una.municipalidad.utils.MapperUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/excepciones")
@Api(tags = {"Excepciones"})
public class ExcepcionController {

    @Autowired
    private IExcepcionService excepcionService;

    @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('AUDITOR')")
    @ApiOperation(value = "Obtiene una lista de todas las excepciones", response = ExcepcionDTO.class, responseContainer = "List", tags = "Excepciones")
    @GetMapping()
    public @ResponseBody
    ResponseEntity<?> findAll() {
        try{
            Optional<List<ExcepcionDTO>> result = excepcionService.findAll();
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('AUDITOR')")
    @ApiOperation(value = "Obtiene una excepcion a partir de su id", response = ExcepcionDTO.class, tags = "Excepciones")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable(value = "id") Long id) {
        try{
            Optional<ExcepcionDTO> excepcionFound = excepcionService.findById(id);
            return new ResponseEntity<>(excepcionFound, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('AUDITOR')")
    @ApiOperation(value = "Obtiene una lista de excepciones a partir del id de un usuario", response = ExcepcionDTO.class, tags = "Excepciones")
    @GetMapping("/usuario/{id}")
    public ResponseEntity<?> findByUsuario(@PathVariable(value = "id") Long id) {
        try{
            Optional<List<ExcepcionDTO>> result = excepcionService.findByUsuario(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('AUDITOR')")
    @ApiOperation(value = "Obtiene una lista de excepciones a partir de un estado", response = ExcepcionDTO.class, tags = "Excepciones")
    @GetMapping("/estado/{estado}")
    public ResponseEntity<?> findByEstado(@PathVariable(value = "estado") boolean estado) {
        try{
            Optional<List<ExcepcionDTO>> result = excepcionService.findByEstado(estado);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('AUDITOR')")
    @ApiOperation(value = "Obtiene una lista de excepciones a partir de la fecha de creacion", response = ExcepcionDTO.class, tags = "Excepciones")
    @GetMapping("/fechaCreacion/{fecha}")
    public ResponseEntity<?> findByFechaCreacion(@PathVariable(value = "fecha") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha) {
        try{
            Optional<List<ExcepcionDTO>> result = excepcionService.findByFechaCreacion(fecha);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PreAuthorize("hasRole('GESTOR') or hasRole('GERENTE')")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Se crea una excepcion", response = ExcepcionDTO.class, tags = "Excepciones")
    @PostMapping("/")
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody ExcepcionDTO excepcionDTO) {
        try{
            Optional<ExcepcionDTO> excepcionCreated = Optional.ofNullable(excepcionService.create(excepcionDTO));
            return new ResponseEntity<>(excepcionCreated, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @PreAuthorize("hasRole('GERENTE')")
    @ApiOperation(value = "Se modifica una excepcion a partir de su id", response = ExcepcionDTO.class, tags = "Excepciones")
    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> update(@PathVariable(value = "id") Long id, @RequestBody ExcepcionDTO excepcionModified) {
        try{
            Optional<ExcepcionDTO> excepcionesUpdated = excepcionService.update(excepcionModified, id);
            return new ResponseEntity<>(excepcionesUpdated, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PreAuthorize("hasRole('GERENTE')")
    @ApiOperation(value = "Se elimina una excepcion a partir de su id", response = ExcepcionDTO.class, tags = "Excepciones")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) throws Exception {
        try{
            excepcionService.delete(id);
            return new ResponseEntity<>("Ok", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PreAuthorize("hasRole('GERENTE')")
    @ApiOperation(value = "Se eliminan todos las excepciones", response = ExcepcionDTO.class, tags = "Excepciones")
    @DeleteMapping("/")
    public ResponseEntity<?> deleteAll() throws Exception {
        try {
            excepcionService.deleteAll();
            return new ResponseEntity<>("Ok", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
