package org.una.municipalidad.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.una.municipalidad.dto.CobroCanceladoDTO;
import org.una.municipalidad.dto.CobroGeneradoDTO;
import org.una.municipalidad.services.ICobroCanceladoService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cobrosCancelados")
@Api(tags = {"CobrosCancelados"})
public class CobroCanceladoController {

    @Autowired
    private ICobroCanceladoService cobroCanceladoService;

    //@PreAuthorize("hasRole('GESTOR') or hasRole('AUDITOR') or hasRole('GERENTE')")
    @ApiOperation(value = "Obtiene una lista de todos los cobros cancelados", response = CobroCanceladoDTO.class, responseContainer = "List", tags = "CobroCancelado")
    @GetMapping()
    public @ResponseBody
    ResponseEntity<?> findAll() {
        try{
        Optional<List<CobroCanceladoDTO>> result = cobroCanceladoService.findAll();
        return new ResponseEntity<>(result, HttpStatus.OK);}
        catch(Exception e){
            return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //@PreAuthorize("hasRole('GESTOR') or hasRole('AUDITOR')")
    @ApiOperation(value = "Obtiene un cobro cancelado a partir de su id", response = CobroCanceladoDTO.class, tags = "CobroCancelado")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable(value = "id") Long id) {
        try {
            Optional<CobroCanceladoDTO> cobroCanceladoFound = cobroCanceladoService.findById(id);
            return new ResponseEntity<>(cobroCanceladoFound, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //@PreAuthorize("hasRole('GESTOR') or hasRole('AUDITOR')")
    @GetMapping("/ByCobroBetweenCedulaAndFecha/{cedula}/{startDate}/{endDate}")
    @ApiOperation(value = "Obtiene una lista de cobros cancelados de acuerdo a la cedula del contribuyente y dos fechas dadas", response = CobroCanceladoDTO.class, responseContainer = "CobroCanceladoDTO", tags = "CobroCancelado")
    public ResponseEntity<?> findByCobroBetweenCedulaAndFecha(@PathVariable(value = "cedula") String cedula, @PathVariable(value = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate, @PathVariable(value = "endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        try {
            Optional<List<CobroCanceladoDTO>> result = cobroCanceladoService.findByCobroBetweenCedulaAndFecha(cedula,startDate,endDate);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }  catch(Exception e){
            return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //@PreAuthorize("hasRole('GESTOR') or hasRole('AUDITOR')")
    @GetMapping("/byFechaCreacion/{startDate}/{endDate}")
    @ApiOperation(value = "Obtiene una lista de cobros cancelados con base en dos fechas dadas", response = CobroCanceladoDTO.class, responseContainer = "CobroCanceladoDto", tags = "CobroCancelado")
    public ResponseEntity<?> findByFechaCreacionBetween(@PathVariable(value = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate, @PathVariable(value = "endDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        try{
        Optional<List<CobroCanceladoDTO>> result = cobroCanceladoService.findByCobroBetweenFecha(startDate,endDate);
        return new ResponseEntity<>(result, HttpStatus.OK);}
        catch(Exception e){
            return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('GESTOR') or hasRole('AUDITOR')")
    @GetMapping("/byFechaCreacion/{startDate}")
    @ApiOperation(value = "Obtiene una lista de cobros cancelados a partir de una fecha de creacion", response = CobroCanceladoDTO.class, responseContainer = "CobroCanceladoDto", tags = "CobroCancelado")
    public ResponseEntity<?> findByFechaCreacion(@PathVariable(value = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate) {
        try {
            Optional<List<CobroCanceladoDTO>> result = cobroCanceladoService.findByFechaCreacion(startDate);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('GESTOR')")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Se crea un cobro cancelado", response = CobroCanceladoDTO.class, tags = "CobroCancelado")
    @PostMapping("/")
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody CobroCanceladoDTO cobroCanceladoDTO) {
        try{
        Optional<CobroCanceladoDTO> cobroCanceladoCreated = cobroCanceladoService.create(cobroCanceladoDTO);
        return new ResponseEntity<>(cobroCanceladoCreated, HttpStatus.CREATED);}
        catch(Exception e){
            return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('GESTOR')")
    @ApiOperation(value = "Se modifica un cobro cancelado a partir de su id", response = CobroCanceladoDTO.class, tags = "CobroCancelado")
    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> update(@PathVariable(value = "id") Long id, @RequestBody CobroCanceladoDTO cobroCanceladoModified) {
        try {
            Optional<CobroCanceladoDTO> cobroCanceladoUpdated = cobroCanceladoService.update(cobroCanceladoModified, id);
            return new ResponseEntity<>(cobroCanceladoModified, HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('GESTOR')")
    @ApiOperation(value = "Se elimina un cobro cancelado a partir de su id", response = CobroCanceladoDTO.class, tags = "CobroCancelado")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) throws Exception { try {
        cobroCanceladoService.delete(id);
        return new ResponseEntity<>("Ok", HttpStatus.OK);
    }  catch(Exception e){
        return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }

    @PreAuthorize("hasRole('GESTOR')")
    @ApiOperation(value = "Se eliminan todos los cobros cancelados", response = CobroCanceladoDTO.class, tags = "CobroCancelado")
    @DeleteMapping("/")
    public ResponseEntity<?> deleteAll() throws Exception {
        try {
            cobroCanceladoService.deleteAll();
            return new ResponseEntity<>("Ok", HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
