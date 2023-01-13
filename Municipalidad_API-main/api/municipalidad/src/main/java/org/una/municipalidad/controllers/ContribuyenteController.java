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
import org.una.municipalidad.dto.CobroCanceladoDTO;
import org.una.municipalidad.dto.ContribuyenteDTO;
import org.una.municipalidad.entities.Contribuyente;
import org.una.municipalidad.exceptions.NotFoundInformationException;
import org.una.municipalidad.services.IContribuyenteService;
import org.una.municipalidad.services.IExcepcionService;
import org.una.municipalidad.utils.MapperUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/contribuyentes")
@Api(tags = {"Contribuyentes"})
public class ContribuyenteController {

    @Autowired
    private IContribuyenteService contribuyenteService;



    @PreAuthorize("hasRole('GESTOR') or hasRole('GERENTE') or hasRole('AUDITOR')")
    @ApiOperation(value = "Obtiene una lista de todos los contribuyentes", response = ContribuyenteDTO.class, responseContainer = "List", tags = "Contribuyentes")
    @GetMapping()
    public @ResponseBody
    ResponseEntity<?> findAll() {
        try {
            Optional<List<ContribuyenteDTO>> result = contribuyenteService.findAll();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch(Exception e){
                return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
            }
    }

    @PreAuthorize("hasRole('GESTOR') or hasRole('GERENTE') or hasRole('AUDITOR') or hasRole('ADMINISTRADOR')")
    @ApiOperation(value = "Obtiene un contribuyente a partir de su id", response = ContribuyenteDTO.class, tags = "Contribuyentes")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable(value = "id") Long id) {
        try {
            Optional<ContribuyenteDTO> contribuyenteFound = contribuyenteService.findById(id);
            return new ResponseEntity<>(contribuyenteFound, HttpStatus.OK);
        }  catch(Exception e){
            return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('GESTOR') or hasRole('GERENTE') or hasRole('AUDITOR')")
    @GetMapping("/findByNombre/{nombre}")
    @ApiOperation(value = "Obtiene un contribuyente de acuerdo a su nombre", response = ContribuyenteDTO.class, responseContainer = "List", tags = "Contribuyentes")
    public ResponseEntity<?> findByNombreCompletoAproximateIgnoreCase(@PathVariable(value = "nombre") String nombre) {
        try {
            Optional<List<ContribuyenteDTO>> result = contribuyenteService.findByNombreCompletoAproximateIgnoreCase(nombre);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('GESTOR') or hasRole('GERENTE') or hasRole('AUDITOR')")
    @GetMapping("/findByFecha/{fechaNacimiento}")
    @ApiOperation(value = "Obtiene una lista de cotribuyetes de acuerdo a su fecha de nacimiento", response = ContribuyenteDTO.class, responseContainer = "List", tags = "Contribuyentes")
    public ResponseEntity<?> findByFechaNacimiento(@PathVariable(value = "fechaNacimiento") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaNacimiento) {
        try{
        Optional<List<ContribuyenteDTO>> result = contribuyenteService.findByFechaNacimiento(fechaNacimiento);
        return new ResponseEntity<>(result, HttpStatus.OK);}
        catch(Exception e){
            return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('GESTOR') or hasRole('GERENTE') or hasRole('AUDITOR')")
    @GetMapping("/findByCorreo/{correo}")
    @ApiOperation(value = "Obtiene un contribuyente a partir de un correo", response = ContribuyenteDTO.class, responseContainer = "ContribuyenteDTO", tags = "Contribuyentes")
    public ResponseEntity<?> findByCorreo(@PathVariable(value = "correo") String correo) {
        try{
        Optional<List<ContribuyenteDTO>> result = contribuyenteService.findByCorreo(correo);
        return new ResponseEntity<>(result, HttpStatus.OK);}
        catch(Exception e){
            return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('GESTOR') or hasRole('GERENTE') or hasRole('AUDITOR')")
    @GetMapping("/findByDireccion/{direccion}")
    @ApiOperation(value = "Obtiene una lista de contribuyentes a partir de una direccion", response = ContribuyenteDTO.class, responseContainer = "ContribuyenteDTO", tags = "Contribuyentes")
    public ResponseEntity<?> findByDireccion(@PathVariable(value = "direccion") String direccion) {
        try{
        Optional<List<ContribuyenteDTO>> result = contribuyenteService.findByDireccion(direccion);
        return new ResponseEntity<>(result, HttpStatus.OK);}
        catch(Exception e){
            return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('GESTOR') or hasRole('GERENTE') or hasRole('AUDITOR')")
    @GetMapping("/findByTelefono/{telefono}")
    @ApiOperation(value = "Obtiene un contribuyente a partir de un telefono", response = ContribuyenteDTO.class, responseContainer = "ContribuyenteDTO", tags = "Contribuyentes")
    public ResponseEntity<?> findByTelefono(@PathVariable(value = "telefono") String telefono) {
        try{
        Optional<List<ContribuyenteDTO>> result = contribuyenteService.findByTelefono(telefono);
        return new ResponseEntity<>(result, HttpStatus.OK);}
        catch(Exception e){
            return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('GESTOR') or hasRole('GERENTE') or hasRole('AUDITOR') or hasRole('ADMINISTRADOR')")
    @GetMapping("/findByCedula/{cedula}")
    @ApiOperation(value = "Obtiene un contribuyente a partir de su cedula", response = ContribuyenteDTO.class, responseContainer = "ContribuyenteDTO", tags = "Contribuyentes")
    public ResponseEntity<?> findByCedula(@PathVariable(value = "cedula") String cedula) {
        try{
            Optional<ContribuyenteDTO> result = contribuyenteService.findByCedula(cedula);
            return new ResponseEntity<>(result, HttpStatus.OK);}
        catch(Exception e){
            return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('GESTOR')")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Se crea un contribuyente", response = ContribuyenteDTO.class, tags = "Contribuyentes")
    @PostMapping("/")
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody ContribuyenteDTO contribuyenteDTO) {
        try{
        Optional<ContribuyenteDTO> contribuyenteCreated = Optional.ofNullable(contribuyenteService.create(contribuyenteDTO));
        return new ResponseEntity<>(contribuyenteCreated, HttpStatus.CREATED);}
        catch(Exception e){
            return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Se modifica un contribuyente a partir de su id", response = ContribuyenteDTO.class, tags = "Contribuyentes")
    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> update(@PathVariable(value = "id") Long id, @RequestBody ContribuyenteDTO contribuyenteDTO) {
       try{
        Optional<ContribuyenteDTO> contribuyenteUpdated = contribuyenteService.update(contribuyenteDTO, id);
        return new ResponseEntity<>(contribuyenteUpdated, HttpStatus.OK);}
       catch(Exception e){
           return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }

    @PreAuthorize("hasRole('GESTOR') or hasRole('ADMINISTRADOR')")
    @ApiOperation(value = "Se elimina un contribuyente a partir de su id", response = ContribuyenteDTO.class, tags = "Contribuyentes")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) throws Exception {
        try {
            contribuyenteService.delete(id);
            return new ResponseEntity<>("Ok", HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('GESTOR')")
    @ApiOperation(value = "Se eliminan todos los contribuyentes", response = ContribuyenteDTO.class, tags = "Contribuyentes")
    @DeleteMapping("/")
    public ResponseEntity<?> deleteAll() throws Exception {
        try{
        contribuyenteService.deleteAll();
        return new ResponseEntity<>("Ok", HttpStatus.OK);}
        catch(Exception e){
            return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
