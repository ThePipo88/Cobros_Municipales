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
import org.una.municipalidad.dto.ContribuyenteDTO;
import org.una.municipalidad.dto.ParametroDTO;
import org.una.municipalidad.services.IParametroService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/parametros")
@Api(tags = {"Parametros"})
public class ParametroController {

    @Autowired
    private IParametroService parametroService;

    @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('AUDITOR')")
    @ApiOperation(value = "Obtiene una lista de todos los parametros", response = ParametroDTO.class, responseContainer = "List", tags = "Parametro")
    @GetMapping()
    public @ResponseBody
    ResponseEntity<?> findAll() {
        try{
            Optional<List<ParametroDTO>> result = parametroService.findAll();
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('GESTOR') or hasRole('AUDITOR')")
    @ApiOperation(value = "Obtiene un parametro a partir de su id", response = ParametroDTO.class, tags = "Parametro")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable(value = "id") Long id) {
        try{
            Optional<ParametroDTO> parametroFound = parametroService.findById(id);
            return new ResponseEntity<>(parametroFound, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('GESTOR') or hasRole('AUDITOR')")
    @GetMapping("/findByFormulaAproximate/{formula}")
    @ApiOperation(value = "Obtiene un parametro de acuerdo a su formula", response = ParametroDTO.class, responseContainer = "ParametroDTO", tags = "Parametro")
    public ResponseEntity<?> findByFormulaAproximate(@PathVariable(value = "formula") String formula) {
        try{
            Optional<List<ParametroDTO>> result = parametroService.findByFormulaAproximate(formula);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('GESTOR') or hasRole('AUDITOR')")
    @GetMapping("/findByNombreAproximate/{nombre}")
    @ApiOperation(value = "Obtiene un parametro de acuerdo a su nombre", response = ParametroDTO.class, responseContainer = "ParametroDTO", tags = "Parametro")
    public ResponseEntity<?> findByNombreAproximate(@PathVariable(value = "nombre") String nombre) {
        try{
            Optional<List<ParametroDTO>> result = parametroService.findByNombreAproximate(nombre);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('GESTOR') or hasRole('AUDITOR')")
    @GetMapping("/findByestadoAproximate/{estado}")
    @ApiOperation(value = "Obtiene un parametro de acuerdo a su estado", response = ParametroDTO.class, responseContainer = "ParametroDTO", tags = "Parametro")
    public ResponseEntity<?> findByEstadoAproximate(@PathVariable(value = "estado") boolean estado) {
        try{
            Optional<List<ParametroDTO>> result = parametroService.findByEstadoAproximate(estado);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('GESTOR') or hasRole('AUDITOR')")
    @GetMapping("/findByFechaCreacionBetween/{startDate}/{endDate}")
    @ApiOperation(value = "Obtiene una lista de parametros entre dos fechas dadas", response = ParametroDTO.class, responseContainer = "ParametroDTO", tags = "Parametro")
    public ResponseEntity<?> findByFechaCreacionBetween(@PathVariable(value = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate, @PathVariable(value = "endDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        try{
            Optional<List<ParametroDTO>> result = parametroService.findByFechaCreacionBetween(startDate,endDate);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('GESTOR') or hasRole('AUDITOR')")
    @GetMapping("/findByFechaModificacionBetween/{startDate}/{endDate}")
    @ApiOperation(value = "Obtiene una lista de parametros de acuerdo a su fecha de modificacion", response = ParametroDTO.class, responseContainer = "List", tags = "Parametros")
    public ResponseEntity<?> findByFechaModificacionBetween(@PathVariable(value = "startDate") Date startDate, @PathVariable(value = "endDate") Date endDate) {
        try{
            Optional<List<ParametroDTO>> result = parametroService.findByFechaModificacionBetween(startDate,endDate);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('GESTOR')")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Se crea un parametro", response = ParametroDTO.class, tags = "Parametro")
    @PostMapping("/")
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody ParametroDTO parametroDTO) {
        try{
            Optional<ParametroDTO> parametroCreated = Optional.ofNullable(parametroService.create(parametroDTO));
            return new ResponseEntity<>(parametroCreated, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @ApiOperation(value = "Se modifica un parametro a partir de su id", response = ParametroDTO.class, tags = "Parametro")
    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> update(@PathVariable(value = "id") Long id, @RequestBody ParametroDTO parametroDTO) {
        try{
            Optional<ParametroDTO> parametroUpdated = parametroService.update(parametroDTO, id);
            return new ResponseEntity<>(parametroUpdated, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @ApiOperation(value = "Se elimina un parametro a partir de su id", response = ParametroDTO.class, tags = "Parametro")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) throws Exception {
        try{
            parametroService.delete(id);
            return new ResponseEntity<>("Ok", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @ApiOperation(value = "Se eliminan todos los parametros", response = ParametroDTO.class, tags = "Parametro")
    @DeleteMapping("/")
    public ResponseEntity<?> deleteAll() throws Exception {
        try{
            parametroService.deleteAll();
            return new ResponseEntity<>("Ok", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
