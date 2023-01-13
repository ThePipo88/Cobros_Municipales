package org.una.municipalidad.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.una.municipalidad.dto.RutaBusDTO;
import org.una.municipalidad.services.IRutaBusService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rutasBus")
@Api(tags = {"RutaBuses"})
public class RutaBusController {

    @Autowired
    private IRutaBusService rutaBusService;

    @PreAuthorize("hasRole('GESTOR') or hasRole('GERENTE') or hasRole('AUDITOR')")
    @ApiOperation(value = "Se obtiene una lista de todas las rutas de bus", response = RutaBusDTO.class, responseContainer = "List", tags = "RutaBus")
    @GetMapping()
    public @ResponseBody
    ResponseEntity<?> findAll() {
        try {
            Optional<List<RutaBusDTO>> result = rutaBusService.findAll();
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('GESTOR') or hasRole('GERENTE') or hasRole('AUDITOR') or hasRole('ADMINISTRADOR')")
    @ApiOperation(value = "Se obtiene una ruta de bus a partir de su id", response = RutaBusDTO.class, tags = "RutaBus")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable(value = "id") Long id) {
        try {
            Optional<RutaBusDTO> rutaBusFound = rutaBusService.findById(id);
            return new ResponseEntity<>(rutaBusFound, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('GESTOR') or hasRole('GERENTE') or hasRole('AUDITOR')")
    @ApiOperation(value = "Se obtiene una ruta de bus a partir de su nombre", response = RutaBusDTO.class, tags = "RutaBus")
    @GetMapping("/nombre/{term}")
    public ResponseEntity<?> findByNombre(@PathVariable(value = "term") String term) {
        try {
            Optional<List<RutaBusDTO>> result = rutaBusService.findByNombre(term);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('GESTOR') or hasRole('GERENTE') or hasRole('AUDITOR')")
    @ApiOperation(value = "Se obtiene una lista de rutas de buses a partir del lugar de inicio", response = RutaBusDTO.class, tags = "RutaBus")
    @GetMapping("/inicio/{term}")
    public ResponseEntity<?> findByInicio(@PathVariable(value = "term") String term) {
        try{
            Optional<List<RutaBusDTO>> result = rutaBusService.findByInicio(term);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('GESTOR') or hasRole('GERENTE') or hasRole('AUDITOR')")
    @ApiOperation(value = "Se obtiene una lista de rutas de buses a partir del lugar final", response = RutaBusDTO.class, tags = "RutaBus")
    @GetMapping("/fin/{term}")
    public ResponseEntity<?> findByFin(@PathVariable(value = "term") String term) {
        try {
            Optional<List<RutaBusDTO>> result = rutaBusService.findByFin(term);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('GESTOR') or hasRole('GERENTE') or hasRole('AUDITOR') or hasRole('ADMINISTRADOR')")
    @ApiOperation(value = "Se obtiene una lista de rutas de buses a partir del id del servicio", response = RutaBusDTO.class, tags = "RutaBus")
    @GetMapping("/byServicioId/{id}")
    public ResponseEntity<?> findByServicioId(@PathVariable(value = "id") Long id) {
        try {
            Optional<List<RutaBusDTO>> result = rutaBusService.findByServicioId(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('GESTOR')")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Se crea una ruta de bus", response = RutaBusDTO.class, tags = "RutaBus")
    @PostMapping("/")
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody RutaBusDTO rutaBusDTO) {
        try {
            Optional<RutaBusDTO> rutaBusCreated = rutaBusService.create(rutaBusDTO);
            return new ResponseEntity<>(rutaBusCreated, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('GESTOR') or hasRole('ADMINISTRADOR')")
    @ApiOperation(value = "Se modifica una ruta de bus a partir de su id", response = RutaBusDTO.class, tags = "RutaBus")
    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> update(@PathVariable(value = "id") Long id, @RequestBody RutaBusDTO rutaBusModified) {
        try {
            Optional<RutaBusDTO> rutaBusUpdated = rutaBusService.update(rutaBusModified, id);
            return new ResponseEntity<>(rutaBusModified, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('GESTOR') or hasRole('ADMINISTRADOR')")
    @ApiOperation(value = "Se elimina una ruta de bus a partir de su id", response = RutaBusDTO.class, tags = "RutaBus")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) throws Exception {
        try {
            rutaBusService.delete(id);
            return new ResponseEntity<>("Ok", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PreAuthorize("hasRole('GESTOR')")
    @ApiOperation(value = "Se eliminan todas las rutas de bus", response = RutaBusDTO.class, tags = "RutaBus")
    @DeleteMapping("/")
    public ResponseEntity<?> deleteAll() throws Exception {
        try {
            rutaBusService.deleteAll();
            return new ResponseEntity<>("Ok", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}