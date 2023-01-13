package org.una.municipalidad.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.una.municipalidad.dto.ListaSalidaDTO;
import org.una.municipalidad.dto.RutaBusDTO;
import org.una.municipalidad.services.IListaSalidaService;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/listaSalidas")
@Api(tags = {"ListaSalidas"})
public class ListaSalidaController {

    @Autowired
    private IListaSalidaService listaSalidaService;

    @PreAuthorize("hasRole('GESTOR') or hasRole('GERENTE') or hasRole('AUDITOR')")
    @ApiOperation(value = "Obtiene una lista de todos las salidas de bus", response = ListaSalidaDTO.class, responseContainer = "List", tags = "ListaSalida")
    @GetMapping()
    public @ResponseBody
    ResponseEntity<?> findAll() {
        try{
            Optional<List<ListaSalidaDTO>> result = listaSalidaService.findAll();
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PreAuthorize("hasRole('GESTOR') or hasRole('GERENTE') or hasRole('AUDITOR') or hasRole('ADMINISTRADOR')")
    @ApiOperation(value = "Obtiene una salida de bus a partir de su id", response = ListaSalidaDTO.class, tags = "ListaSalida")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable(value = "id") Long id) {
        try{
            Optional<ListaSalidaDTO> listaSalidaFound = listaSalidaService.findById(id);
            return new ResponseEntity<>(listaSalidaFound, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PreAuthorize("hasRole('GESTOR') or hasRole('GERENTE') or hasRole('AUDITOR')")
    @ApiOperation(value = "Obtiene una lista de salida de bus a partir de su dia", response = ListaSalidaDTO.class, tags = "ListaSalida")
    @GetMapping("/dia/{term}")
    public ResponseEntity<?> findByDia(@PathVariable(value = "term") String term) {
        try {
            Optional<List<ListaSalidaDTO>> result = listaSalidaService.findByDia(term);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PreAuthorize("hasRole('GESTOR') or hasRole('GERENTE') or hasRole('AUDITOR')")
    @ApiOperation(value = "Obtiene una lista de salida de bus a partir de la cantidad de salidas", response = ListaSalidaDTO.class, tags = "ListaSalida")
    @GetMapping("/cantidad/{term}")
    public ResponseEntity<?> findByCantidad(@PathVariable(value = "term") int term) {
        try{
            Optional<List<ListaSalidaDTO>> result = listaSalidaService.findByCantidad(term);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PreAuthorize("hasRole('GESTOR') or hasRole('GERENTE') or hasRole('AUDITOR') or hasRole('ADMINISTRADOR')")
    @ApiOperation(value = "Se obtiene una lista de salidas de buses a partir del id del servicio", response = ListaSalidaDTO.class, tags = "ListaSalida")
    @GetMapping("/byRutaId/{id}")
    public ResponseEntity<?> findByRutaId(@PathVariable(value = "id") Long id) {
        try {
            Optional<List<ListaSalidaDTO>> result = listaSalidaService.findByRutaId(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('GESTOR')")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Se crea una lista de salidas de bus", response = ListaSalidaDTO.class, tags = "ListaSalida")
    @PostMapping("/")
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody ListaSalidaDTO listaSalidaDTO) {
        try{
            Optional<ListaSalidaDTO> ListaSalidaCreated = listaSalidaService.create(listaSalidaDTO);
            return new ResponseEntity<>(ListaSalidaCreated, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PreAuthorize("hasRole('GESTOR')")
    @ApiOperation(value = "Se modifica una lista de salidas de bus a partir de su id", response = ListaSalidaDTO.class, tags = "ListaSalida")
    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> update(@PathVariable(value = "id") Long id, @RequestBody ListaSalidaDTO listaSalidaModified) {
        try{
            Optional<ListaSalidaDTO> listaSalidaUpdated = listaSalidaService.update(listaSalidaModified, id);
            return new ResponseEntity<>(listaSalidaModified, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PreAuthorize("hasRole('GESTOR') or hasRole('ADMINISTRADOR')")
    @ApiOperation(value = "Se elimina una lista de salidas de bus a partir de su id", response = ListaSalidaDTO.class, tags = "ListaSalida")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) throws Exception {
        try{
            listaSalidaService.delete(id);
            return new ResponseEntity<>("Ok", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PreAuthorize("hasRole('GESTOR')")
    @ApiOperation(value = "Se eliminan todas las listas de salidas de bus", response = ListaSalidaDTO.class, tags = "RutaBus")
    @DeleteMapping("/")
    public ResponseEntity<?> deleteAll() throws Exception {
        try{
            listaSalidaService.deleteAll();
            return new ResponseEntity<>("Ok", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
