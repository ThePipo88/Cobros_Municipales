package org.una.municipalidad.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.una.municipalidad.dto.ReciboDTO;
import org.una.municipalidad.dto.RutaBusDTO;
import org.una.municipalidad.dto.ServicioDTO;
import org.una.municipalidad.dto.ServicioPropiedadDTO;
import org.una.municipalidad.services.IServicioPropiedadService;
import org.una.municipalidad.services.IServicioService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/serviciosPropiedades")
@Api(tags = {"ServiciosPropiedades"})
public class ServicioPropiedadController {

    @Autowired
    private IServicioPropiedadService servicioPropiedadService;

    @PreAuthorize("hasRole('GESTOR') or hasRole('GERENTE') or hasRole('AUDITOR')")
    @ApiOperation(value = "Se obtiene una lista de todos los servicios propiedades", response = ServicioPropiedadDTO.class, responseContainer = "List", tags = "ServiciosPropiedades")
    @GetMapping()
    public @ResponseBody
    ResponseEntity<?> findAll() {
        try {
            Optional<List<ServicioPropiedadDTO>> result = servicioPropiedadService.findAll();
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('GESTOR') or hasRole('GERENTE') or hasRole('AUDITOR') or hasRole('ADMINISTRADOR')")
    @ApiOperation(value = "Obtiene un servicio propiedad a partir de su id", response = ServicioPropiedadDTO.class, tags = "ServiciosPropiedades")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable(value = "id") Long id) {
        try{
            Optional<ServicioPropiedadDTO> result = servicioPropiedadService.findById(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('GESTOR') or hasRole('GERENTE') or hasRole('AUDITOR') or hasRole('ADMINISTRADOR')")
    @ApiOperation(value = "Obtiene un servicio a partir de su id", response = ServicioPropiedadDTO.class, tags = "ServiciosPropiedades")
    @GetMapping("/ByServicioId/{id}")
    public ResponseEntity<?> findByServicioId(@PathVariable(value = "id") Long id) {
        try {
            Optional<List<ServicioPropiedadDTO>> result = servicioPropiedadService.findByServicioId(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('GESTOR') or hasRole('GERENTE') or hasRole('AUDITOR') or hasRole('ADMINISTRADOR')")
    @ApiOperation(value = "Obtiene una propiedad a partir de su id", response = ServicioPropiedadDTO.class, tags = "ServiciosPropiedades")
    @GetMapping("/ByPropiedadId/{id}")
    public ResponseEntity<?> findByPropiedadId(@PathVariable(value = "id") Long id) {
        try {
            Optional<List<ServicioPropiedadDTO>> result = servicioPropiedadService.findByPropiedadId(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('GESTOR')")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Se crea un servicio propiedad", response = ServicioPropiedadDTO.class, tags = "ServiciosPropiedades")
    @PostMapping("/")
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody ServicioPropiedadDTO servicioPropiedadDTO) {
        try{
            Optional<ServicioPropiedadDTO> servicioCreated = servicioPropiedadService.create(servicioPropiedadDTO);
            return new ResponseEntity<>(servicioCreated, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('GESTOR')")
    @ApiOperation(value = "Se modifica un servicio propiedad a partir de su id", response = ServicioPropiedadDTO.class, tags = "ServiciosPropiedades")
    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> update(@PathVariable(value = "id") Long id, @RequestBody ServicioPropiedadDTO servicioPropiedadDTO) {
        try{
            Optional<ServicioPropiedadDTO> servicioPropiedadUpdated = servicioPropiedadService.update(servicioPropiedadDTO, id);
            return new ResponseEntity<>(servicioPropiedadUpdated, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('GESTOR') or hasRole('ADMINISTRADOR')")
    @ApiOperation(value = "Se elimina un ervicio a partir de su id", response = ServicioPropiedadDTO.class, tags = "ServiciosPropiedades")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) throws Exception {
        try{
            servicioPropiedadService.delete(id);
            return new ResponseEntity<>("Ok", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('GESTOR')")
    @ApiOperation(value = "Se eliminan todos los servicios", response = ReciboDTO.class, tags = "ServiciosPropiedades")
    @DeleteMapping("/")
    public ResponseEntity<?> deleteAll() throws Exception {
        try{
            servicioPropiedadService.deleteAll();
            return new ResponseEntity<>("Ok", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
