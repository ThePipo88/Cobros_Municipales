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
import org.una.municipalidad.dto.ContribuyenteDTO;
import org.una.municipalidad.dto.RolDTO;
import org.una.municipalidad.services.ICobroGeneradoService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cobrosGenerados")
@Api(tags = {"CobrosGenerados"})
public class CobroGeneradoController {
    @Autowired
    private ICobroGeneradoService cobroGeneradoService;

    //@PreAuthorize("hasRole('GESTOR') or hasRole('GERENTE') or hasRole('AUDITOR')")
    @ApiOperation(value = "Obtiene una lista de todos los cobros generados", response = CobroGeneradoDTO.class, responseContainer = "List", tags = "CobroGenerado")
    @GetMapping()
    public @ResponseBody
    ResponseEntity<?> findAll() {
        try {
            Optional<List<CobroGeneradoDTO>> result = cobroGeneradoService.findAll();
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //@PreAuthorize("hasRole('GESTOR') or hasRole('GERENTE') or hasRole('AUDITOR')")
    @ApiOperation(value = "Obtiene un cobro generado a partir de su id", response = CobroGeneradoDTO.class, tags = "CobroGenerado")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable(value = "id") Long id) {
        try{
        Optional<CobroGeneradoDTO> cobroGeneradoFound = cobroGeneradoService.findById(id);
        return new ResponseEntity<>(cobroGeneradoFound, HttpStatus.OK);}
        catch(Exception e){
            return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //@PreAuthorize("hasRole('GESTOR') or hasRole('GERENTE') or hasRole('AUDITOR')")
    @ApiOperation(value = "Obtiene una lista de cobros generadoss a partir de un Id Busqueda", response = CobroGeneradoDTO.class, responseContainer = "List", tags = "CobroGenerado")
    @GetMapping("/busqueda/{id}")
    public ResponseEntity<?> findByBusquedaId(@PathVariable(value = "id") Long id) {
        try {
            Optional<List<CobroGeneradoDTO>> result = cobroGeneradoService.findByBusquedaId(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('GESTOR') or hasRole('GERENTE') or hasRole('AUDITOR')")
    @ApiOperation(value = "Obtiene una lista de cobros generadoss a partir de un monto", response = CobroGeneradoDTO.class, responseContainer = "List", tags = "CobroGenerado")
    @GetMapping("/monto/{term}")
    public ResponseEntity<?> findByMonto(@PathVariable(value = "term") Double term) {
        try {
            Optional<List<CobroGeneradoDTO>> result = cobroGeneradoService.findByMonto(term);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

     /*
    @GetMapping("/ByObjetoAndFechaCobroBetween/{id}/{startDate}/{endDate}")
    @ApiOperation(value = "Obtiene una lista de cobros generados de acuerdo al objeto y fecha de Cobro", response = CobroGeneradoDTO.class, responseContainer = "CobroGeneradoDTO", tags = "CobroGenerado")
    public ResponseEntity<?> findByObjetoAndFechaCobroBetween(@PathVariable(value = "id") String id, @PathVariable(value = "startDate") Date startDate, @PathVariable(value = "endDate") Date endDate) {
        Optional<List<CobroGeneradoDTO>> result = cobroGeneradoService.findByObjetoAndFechaCobroBetween(id,startDate,endDate);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
     */

    @PreAuthorize("hasRole('GESTOR') or hasRole('GERENTE') or hasRole('AUDITOR')")
    @GetMapping("/ByFechaCobroBetween/{startDate}/{endDate}")
    @ApiOperation(value = "Obtiene una lista de cobros generados de acuerdo a un rango de fechas", response = CobroGeneradoDTO.class, responseContainer = "CobroGeneradoDTO", tags = "CobroGenerado")
    public ResponseEntity<?> findByFechaCobroBetween(@PathVariable(value = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd")  Date startDate, @PathVariable(value = "endDate")
     @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        try {
            Optional<List<CobroGeneradoDTO>> result = cobroGeneradoService.findByFechaCobroBetween(startDate, endDate);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('GESTOR') or hasRole('GERENTE') or hasRole('AUDITOR')")
    @GetMapping("/findByCobroRutaBus/{startDate}/{endDate}")
    @ApiOperation(value = "Genera cobros mensuales", response = CobroGeneradoDTO.class, responseContainer = "List", tags = "CobroGenerado")
    public ResponseEntity<?> findByCobroRutaBus(@PathVariable(value = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd")  Date startDate, @PathVariable(value = "endDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate){
        try{
            Optional<CobroGeneradoDTO> result = cobroGeneradoService.findByCobroRutaBus(startDate,endDate);
            return new ResponseEntity<>(result, HttpStatus.OK);}
        catch(Exception e){
            return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('GESTOR') or hasRole('GERENTE') or hasRole('AUDITOR')")
    @GetMapping("/findByCobroLimpiezaParque/{startDate}/{endDate}")
    @ApiOperation(value = "Genera cobros bimestrales", response = CobroGeneradoDTO.class, responseContainer = "List", tags = "CobroGenerado")
    public ResponseEntity<?> findByCobroLimpiezaParque(@PathVariable(value = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd")  Date startDate, @PathVariable(value = "endDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate){
        try{
            Optional<CobroGeneradoDTO> result = cobroGeneradoService.findByCobroLimpiezaParque(startDate,endDate);
            return new ResponseEntity<>(result, HttpStatus.OK);}
        catch(Exception e){
            return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('GESTOR') or hasRole('GERENTE') or hasRole('AUDITOR')")
    @GetMapping("/findByCobroCementerio/{startDate}/{endDate}")
    @ApiOperation(value = "Genera cobros anuales", response = CobroGeneradoDTO.class, responseContainer = "List", tags = "CobroGenerado")
    public ResponseEntity<?> findByCobroCementerio(@PathVariable(value = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd")  Date startDate, @PathVariable(value = "endDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate){
        try{
            Optional<CobroGeneradoDTO> result = cobroGeneradoService.findByCobroCementerio(startDate,endDate);
            return new ResponseEntity<>(result, HttpStatus.OK);}
        catch(Exception e){
            return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
    @PreAuthorize("hasRole('GESTOR') or hasRole('GERENTE') or hasRole('AUDITOR')")
    @GetMapping("/findByFecha/{fechaCobro}")
    @ApiOperation(value = "Obtiene una fecha cobro", response = CobroGeneradoDTO.class, responseContainer = "List", tags = "CobroGenerado")
    public ResponseEntity<?> findByFechaCobro(@PathVariable(value = "fechaCobro") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaCobro) {
        try{
            Optional<CobroGeneradoDTO> result = cobroGeneradoService.findByFechaCobro(fechaCobro);
            return new ResponseEntity<>(result, HttpStatus.OK);}
        catch(Exception e){
            return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
     */


    @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('AUDITOR')")
    @ApiOperation(value = "Obtiene un cobro generado a partir de su estado", response = CobroGeneradoDTO.class, tags = "CobroGenerado")
    @GetMapping("/byEstado/{estado}")
    public ResponseEntity<?> findByEstado(@PathVariable(value = "estado") boolean estado) {
        try {
            Optional<List<CobroGeneradoDTO>> result = cobroGeneradoService.findByEstado(estado);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('GESTOR') or hasRole('GERENTE') or hasRole('AUDITOR')")
    @GetMapping("/ByCedulaAndTipo/{cedula}/{tipo}")
    @ApiOperation(value = "Obtiene una lista de cobros generados de acuerdo a la cedula del contribuyente", response = CobroGeneradoDTO.class, responseContainer = "CobroGeneradoDTO", tags = "CobroGenerado")
    public ResponseEntity<?> findCobroByCedulaAndTipo(@PathVariable(value = "cedula") String cedula, @PathVariable(value = "tipo") String tipo) {
        try {
            Optional<List<CobroGeneradoDTO>> result = cobroGeneradoService.findCobroByCedulaAndTipo(cedula,tipo);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }  catch(Exception e){
            return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('GESTOR') or hasRole('GERENTE') or hasRole('AUDITOR')")
    @GetMapping("/ByCedula/{cedula}")
    @ApiOperation(value = "Obtiene una lista de cobros generados de acuerdo a la cedula del contribuyente", response = CobroGeneradoDTO.class, responseContainer = "CobroGeneradoDTO", tags = "CobroGenerado")
    public ResponseEntity<?> findCobroByCedula(@PathVariable(value = "cedula") String cedula) {
        try {
            Optional<List<CobroGeneradoDTO>> result = cobroGeneradoService.findCobroByCedula(cedula);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }  catch(Exception e){
            return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('GESTOR')")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Se crea un cobro generado", response = CobroGeneradoDTO.class, tags = "CobroGenerado")
    @PostMapping("/")
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody CobroGeneradoDTO cobroGeneradoDTO) {
        try{
        Optional<CobroGeneradoDTO> cobroGeneradoCreated = Optional.ofNullable(cobroGeneradoService.create(cobroGeneradoDTO));
        return new ResponseEntity<>(cobroGeneradoCreated, HttpStatus.CREATED);}
        catch(Exception e){
            return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('GESTOR')")
    @ApiOperation(value = "Se modifica un cobro generado a partir de su id", response = CobroGeneradoDTO.class, tags = "CobroGenerado")
    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> update(@PathVariable(value = "id") Long id, @RequestBody CobroGeneradoDTO cobroGeneradoModified) {
        try{
        Optional<CobroGeneradoDTO> cobroGeneradoUpdated = cobroGeneradoService.update(cobroGeneradoModified, id);
        return new ResponseEntity<>(cobroGeneradoModified, HttpStatus.OK);}
        catch(Exception e){
            return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('GESTOR')")
    @ApiOperation(value = "Se elimina un cobro generado a partir de su id", response = CobroGeneradoDTO.class, tags = "CobroGenerado")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) throws Exception {
        try {
            cobroGeneradoService.delete(id);
            return new ResponseEntity<>("Ok", HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('GESTOR')")
    @ApiOperation(value = "Se eliminan todos los cobros generados", response = CobroGeneradoDTO.class, tags = "CobroGenerado")
    @DeleteMapping("/")
    public ResponseEntity<?> deleteAll() throws Exception {
        try {
            cobroGeneradoService.deleteAll();
            return new ResponseEntity<>("Ok", HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
