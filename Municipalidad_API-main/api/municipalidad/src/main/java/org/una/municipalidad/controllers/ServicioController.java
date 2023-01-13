package org.una.municipalidad.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.una.municipalidad.dto.ReciboDTO;
import org.una.municipalidad.dto.ServicioDTO;
import org.una.municipalidad.services.IServicioService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/servicios")
@Api(tags = {"Servicios"})
public class ServicioController {

    @Autowired
    private IServicioService servicioService;

    @PreAuthorize("hasRole('GESTOR') or hasRole('GERENTE') or hasRole('AUDITOR') or hasRole('ADMINISTRADOR')")
    @ApiOperation(value = "Obtiene una lista de todos los servicios", response = ServicioDTO.class, responseContainer = "List", tags = "Servicios")
    @GetMapping()
    public @ResponseBody
    ResponseEntity<?> findAll() {
        try{
            Optional<List<ServicioDTO>> result = servicioService.findAll();
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('GESTOR') or hasRole('GERENTE') or hasRole('AUDITOR') or hasRole('ADMINISTRADOR')")
    @ApiOperation(value = "Obtiene un servicio partir de su id", response = ServicioDTO.class, tags = "Servicios")
    @GetMapping("/byId/{id}")
    public ResponseEntity<?> findById(@PathVariable(value = "id") Long id) {
        try{
            Optional<ServicioDTO> result = servicioService.findById(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('GESTOR') or hasRole('GERENTE') or hasRole('AUDITOR')")
    @ApiOperation(value = "Obtiene una lista de servicios a de bus a partir de su tipo", response = ServicioDTO.class, tags = "Servicios")
    @GetMapping("/servicio/{term}")
    public ResponseEntity<?> findBytTipoServicio(@PathVariable(value = "term") String term) {
        try{
            Optional<List<ServicioDTO>> result = servicioService.findByTipoServicio(term);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('GESTOR') or hasRole('GERENTE') or hasRole('AUDITOR')")
    @ApiOperation(value = "Obtiene una lista de servicios a partir de un estado", response = ServicioDTO.class, tags = "Servicios")
    @GetMapping("/estado/{estado}")
    public ResponseEntity<?> findByEstado(@PathVariable(value = "estado") boolean estado) {
        try{
            Optional<List<ServicioDTO>> result = servicioService.findByEstado(estado);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
    @PreAuthorize("hasRole('GESTOR') or hasRole('GERENTE') or hasRole('AUDITOR')")
    @ApiOperation(value = "Obtiene una lista de servicios a partir de una propiedad", response = ServicioDTO.class, tags = "Servicios")
    @GetMapping("/propiedad/{id}")
    public ResponseEntity<?> findByPropiedad(@PathVariable(value = "id") Long id) {
        try{
            Optional<List<ServicioDTO>> result = servicioService.findByPropiedad(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    */
    @PreAuthorize("hasRole('GESTOR') or hasRole('GERENTE') or hasRole('AUDITOR')")
    @ApiOperation(value = "Obtiene una lista de servicios a partir de una fecha de registro", response = ServicioDTO.class, tags = "Servicios")
    @GetMapping("/fechaRegistro/{fechaRegistro}")
    public ResponseEntity<?> findByFechaRegitro(@PathVariable(value = "fechaRegistro") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaRegistro) {
        try{
            Optional<List<ServicioDTO>> result = servicioService.findByFechaRegistro(fechaRegistro);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('GESTOR') or hasRole('GERENTE') or hasRole('AUDITOR')")
    @ApiOperation(value = "Obtiene una lista de servicios a partir de la ultima actualizacion", response = ServicioDTO.class, tags = "Servicios")
    @GetMapping("/fechaActualizacion/{fecha}")
    public ResponseEntity<?> findByUltimaActualizacion(@PathVariable(value = "fecha") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha) {
        try{
            Optional<List<ServicioDTO>> result = servicioService.findByUltimaActualizacion(fecha);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('GESTOR') or hasRole('ADMINISTRADOR')")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Se crea un servicio", response = ServicioDTO.class, tags = "Servicios")
    @PostMapping("/")
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody ServicioDTO servicioDTO) {
        try{
            Optional<ServicioDTO> servicioCreated = Optional.ofNullable(servicioService.create(servicioDTO));
            return new ResponseEntity<>(servicioCreated, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Se modifica un servicio a partir de su id", response = ServicioDTO.class, tags = "Servicios")
    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> update(@PathVariable(value = "id") Long id, @RequestBody ServicioDTO servicioDTO) {
        try{
            Optional<ServicioDTO> servicioUpdated = servicioService.update(servicioDTO, id);
            return new ResponseEntity<>(servicioUpdated, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('GESTOR') or hasRole('ADMINISTRADOR')")
    @ApiOperation(value = "Se elimina un ervicio a partir de su id", response = ServicioDTO.class, tags = "Servicios")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) throws Exception {
        try{
            servicioService.delete(id);
            return new ResponseEntity<>("Ok", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('GESTOR')")
    @ApiOperation(value = "Se eliminan todos los servicios", response = ServicioDTO.class, tags = "Servicios")
    @DeleteMapping("/")
    public ResponseEntity<?> deleteAll() throws Exception {
        try{
            servicioService.deleteAll();
            return new ResponseEntity<>("Ok", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
