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
import org.una.municipalidad.dto.PropiedadDTO;
import org.una.municipalidad.dto.ReciboDTO;
import org.una.municipalidad.entities.Recibo;
import org.una.municipalidad.exceptions.NotFoundInformationException;
import org.una.municipalidad.services.IReciboService;
import org.una.municipalidad.services.IRutaBusService;
import org.una.municipalidad.utils.MapperUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/recibos")
@Api(tags = {"Recibos"})
public class ReciboController {

    @Autowired
    private IReciboService reciboService;

    @PreAuthorize("hasRole('GESTOR') or hasRole('GERENTE') or hasRole('AUDITOR')")
    @ApiOperation(value = "Obtiene una lista de todos los recibos", response = ReciboDTO.class, responseContainer = "List", tags = "Recibos")
    @GetMapping()
    public @ResponseBody
    ResponseEntity<?> findAll() {
        try{
            Optional<List<ReciboDTO>> result = reciboService.findAll();
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('GESTOR') or hasRole('GERENTE') or hasRole('AUDITOR')")
    @ApiOperation(value = "Obtiene un recibo a partir de su id", response = ReciboDTO.class, tags = "Recibos")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable(value = "id") Long id) {
        try{
            Optional<ReciboDTO> reciboFound = reciboService.findById(id);
            return new ResponseEntity<>(reciboFound, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('GESTOR') or hasRole('GERENTE') or hasRole('AUDITOR')")
    @ApiOperation(value = "Obtiene una lista de recibos a partir del id de un contribuyente", response = ReciboDTO.class, responseContainer = "List", tags = "Recibos")
    @GetMapping("/contribuyente/{term}")
    public ResponseEntity<?> findByContribuyente(@PathVariable(value = "term") String term) {
        try{
            Optional<List<ReciboDTO>> result = reciboService.findByContribuyente(term);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('GESTOR') or hasRole('GERENTE') or hasRole('AUDITOR')")
    @ApiOperation(value = "Obtiene una lista de recibos a partir de una fecha de emision", response = ReciboDTO.class, responseContainer = "List", tags = "Recibos")
    @GetMapping("/fechaEmision/{fecha}")
    public ResponseEntity<?> findByFechaEmision(@PathVariable(value = "fecha") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha) {
        try{
            Optional<List<ReciboDTO>> result = reciboService.findByFechaEmision(fecha);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiOperation(value = "Obtiene una lista de recibos en fechas dadas", response = ReciboDTO.class, responseContainer = "List", tags = "Recibos")
    @GetMapping("/findByReciboBetweenFecha/{startDate}/{endDate}")
    public ResponseEntity<?> findByReciboBetweenFecha(@PathVariable(value = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate, @PathVariable(value = "endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        try {
            Optional<List<ReciboDTO>> result = reciboService.findByReciboBetweenFecha(startDate,endDate);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }  catch(Exception e){
            return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('GESTOR') ")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Se crea un recibo", response = ReciboDTO.class, tags = "Recibos")
    @PostMapping("/")
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody ReciboDTO reciboDTO) {
        try{
            Optional<ReciboDTO> reciboCreated = Optional.ofNullable(reciboService.create(reciboDTO));
            return new ResponseEntity<>(reciboCreated, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('GESTOR')")
    @ApiOperation(value = "Se modifica un recibo a partir de su id", response = ReciboDTO.class, tags = "Recibos")
    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> update(@PathVariable(value = "id") Long id, @RequestBody ReciboDTO reciboDTO) {
        try{
            Optional<ReciboDTO> propiedadUpdated = reciboService.update(reciboDTO, id);
            return new ResponseEntity<>(propiedadUpdated, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('GESTOR')")
    @ApiOperation(value = "Se elimina un recibo a partir de su id", response = ReciboDTO.class, tags = "Recibos")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) throws Exception {
        try{
            reciboService.delete(id);
            return new ResponseEntity<>("Ok", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('GESTOR')")
    @ApiOperation(value = "Se eliminan todos los recibos", response = ReciboDTO.class, tags = "Recibos")
    @DeleteMapping("/")
    public ResponseEntity<?> deleteAll() throws Exception {
        try {
            reciboService.deleteAll();
            return new ResponseEntity<>("Ok", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
