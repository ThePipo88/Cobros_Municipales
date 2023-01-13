package org.una.municipalidad.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.una.municipalidad.dto.ContribuyenteServicioDTO;
import org.una.municipalidad.services.IContribuyenteServicioService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/contribuyentesServicios")
@Api(tags = {"ContribuyentesServicios"})
public class ContribuyenteServicioController {

    @Autowired
    private IContribuyenteServicioService contribuyenteServicioService;

    @PreAuthorize("hasRole('GESTOR') or hasRole('GERENTE') or hasRole('AUDITOR') or hasRole('ADMINISTRADOR')")
    @ApiOperation(value = "Obtiene una lista de todos los servicios de los contribuyentes", response = ContribuyenteServicioDTO.class, responseContainer = "List", tags = "ContribuyenteServicio")
    @GetMapping()
    public @ResponseBody
    ResponseEntity<?> findAll() {
        try{
            Optional<List<ContribuyenteServicioDTO>> result = contribuyenteServicioService.findAll();
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('GESTOR') or hasRole('GERENTE') or hasRole('AUDITOR') or hasRole('ADMINISTRADOR')")
    @ApiOperation(value = "Obtiene un servicio contribuyente a partir de su id", response = ContribuyenteServicioDTO.class, tags = "ContribuyenteServicio")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable(value = "id") Long id) {
        try{
            Optional<ContribuyenteServicioDTO> contribuyenteServicioFound = contribuyenteServicioService.findById(id);
            return new ResponseEntity<>(contribuyenteServicioFound, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('GESTOR') or hasRole('GERENTE') or hasRole('AUDITOR')")
    @GetMapping("/ByPorcentaje/{porcentaje}")
    @ApiOperation(value = "Obtiene una lista de servicios contribuyentes de acuerdo al porcentaje de posecion del servicio", response = ContribuyenteServicioDTO.class, responseContainer = "ContribuyenteServicioDTO", tags = "ContribuyenteServicio")
    public ResponseEntity<?> findByPorcentaje(@PathVariable(value = "porcentaje") float porcentaje) {
        try{
            Optional<List<ContribuyenteServicioDTO>> contribuyenteServicioFound = contribuyenteServicioService.findByPorcentaje(porcentaje);
            return new ResponseEntity<>(contribuyenteServicioFound, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PreAuthorize("hasRole('GESTOR')")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Se crea un servicio de contribuyente", response = ContribuyenteServicioDTO.class, tags = "ContribuyenteServicio")
    @PostMapping("/")
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody ContribuyenteServicioDTO contribuyenteServicioDTO) {
        try{
            Optional<ContribuyenteServicioDTO> contribuyenteServicioCreated = Optional.ofNullable(contribuyenteServicioService.create(contribuyenteServicioDTO));
            return new ResponseEntity<>(contribuyenteServicioCreated, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @ApiOperation(value = "Se modifica un servicio de contribuyente a partir de su id", response = ContribuyenteServicioDTO.class, tags = "ContribuyenteServicio")
    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> update(@PathVariable(value = "id") Long id, @RequestBody ContribuyenteServicioDTO contribuyenteServicioDTO) {
        try{
            Optional<ContribuyenteServicioDTO> contribuyenteServicioUpdated = contribuyenteServicioService.update(contribuyenteServicioDTO, id);
            return new ResponseEntity<>(contribuyenteServicioUpdated, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PreAuthorize("hasRole('GESTOR') or hasRole('ADMINISTRADOR')")
    @ApiOperation(value = "Se elimina un servicio de contribuyente a partir de su id", response = ContribuyenteServicioDTO.class, tags = "ContribuyenteServicio")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) throws Exception {
        try{
            contribuyenteServicioService.delete(id);
            return new ResponseEntity<>("Ok", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PreAuthorize("hasRole('GESTOR')")
    @ApiOperation(value = "Se eliminan todos los servicios de contribuyentes", response = ContribuyenteServicioDTO.class, tags = "ContribuyenteServicio")
    @DeleteMapping("/")
    public ResponseEntity<?> deleteAll() throws Exception {
        try{
            contribuyenteServicioService.deleteAll();
            return new ResponseEntity<>("Ok", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
