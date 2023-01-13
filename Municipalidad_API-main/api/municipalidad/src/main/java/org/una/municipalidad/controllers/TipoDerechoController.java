package org.una.municipalidad.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.una.municipalidad.dto.RutaBusDTO;
import org.una.municipalidad.dto.TipoDerechoDTO;
import org.una.municipalidad.services.ITipoDerechoService;

import java.util.List;
import java.util.Optional;

  @RestController
  @RequestMapping("/tiposderechos")
  @Api(tags = {"TipoDerecho"})
 public class TipoDerechoController {

      @Autowired
      private ITipoDerechoService tipoDerechoService;

      @PreAuthorize("hasRole('GESTOR') or hasRole('GERENTE') or hasRole('AUDITOR')")
      @ApiOperation(value = "Obtiene una lista de todos los tipos de derechos  cementerio ", response = TipoDerechoDTO.class, responseContainer = "List", tags = "TipoDerecho")
      @GetMapping()
      public @ResponseBody
      ResponseEntity<?> findAll() {
          try{
              Optional<List<TipoDerechoDTO>> result = tipoDerechoService.findAll();
              return new ResponseEntity<>(result, HttpStatus.OK);
          }catch (Exception e){
              return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
          }
      }

      @PreAuthorize("hasRole('GESTOR') or hasRole('GERENTE') or hasRole('AUDITOR') or hasRole('ADMINISTRADOR')")
      @ApiOperation(value = "Obtiene un tipo de derecho  a partir de su id", response = TipoDerechoDTO.class, tags = "TipoDerecho")
      @GetMapping("/{id}")
      public ResponseEntity<?> findById(@PathVariable(value = "id") Long id) {
          try{
              Optional<TipoDerechoDTO> result = tipoDerechoService.findById(id);
              return new ResponseEntity<>(result, HttpStatus.OK);
          }catch (Exception e){
              return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
          }
      }

      @PreAuthorize("hasRole('GESTOR') or hasRole('GERENTE') or hasRole('AUDITOR')")
      @ApiOperation(value = "Obtiene una lista de derechos de cementerio a partir de su tipo de derecho", response = TipoDerechoDTO.class, tags = "TipoDerecho")
      @GetMapping("/tipo/{term}")
      public ResponseEntity<?> findByTipo(@PathVariable(value = "term") String term) {
          try{
              Optional<List<TipoDerechoDTO>> result = tipoDerechoService.findByTipo(term);
              return new ResponseEntity<>(result, HttpStatus.OK);
          }catch (Exception e){
              return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
          }
      }

      @PreAuthorize("hasRole('GESTOR') or hasRole('GERENTE') or hasRole('AUDITOR')")
      @ApiOperation(value = "Obtiene una lista de derechos de cementerio a partir de su monto fijo", response = TipoDerechoDTO.class, tags = "TipoDerecho")
      @GetMapping("/monto/{term}")
      public ResponseEntity<?> findByMonto(@PathVariable(value = "term") int term) {
          try{
              Optional<List<TipoDerechoDTO>> result = tipoDerechoService.findByMonto(term);
              return new ResponseEntity<>(result, HttpStatus.OK);
          }catch (Exception e){
              return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
          }
      }

      @PreAuthorize("hasRole('GESTOR') or hasRole('GERENTE') or hasRole('AUDITOR') or hasRole('ADMINISTRADOR')")
      @ApiOperation(value = "Se obtiene una lista de derechos de cementerio a partir del id del servicio", response = TipoDerechoDTO.class, tags = "TipoDerecho")
      @GetMapping("/byServicioId/{id}")
      public ResponseEntity<?> findByServicioId(@PathVariable(value = "id") Long id) {
          try {
              Optional<List<TipoDerechoDTO>> result = tipoDerechoService.findByServicioId(id);
              return new ResponseEntity<>(result, HttpStatus.OK);
          }catch (Exception e){
              return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
          }
      }

      @PreAuthorize("hasRole('GESTOR')")
      @ResponseStatus(HttpStatus.OK)
      @ApiOperation(value = "Se crea una lista de tipos de derechos", response = TipoDerechoDTO.class, tags = "TipoDerecho")
      @PostMapping("/")
      @ResponseBody
      public ResponseEntity<?> create(@RequestBody TipoDerechoDTO tipoDerechoDTO) {
          try{
              Optional<TipoDerechoDTO> TipoDerechoCreated = tipoDerechoService.create(tipoDerechoDTO);
              return new ResponseEntity<>(TipoDerechoCreated, HttpStatus.CREATED);
          }catch (Exception e){
              return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
          }
      }

      @PreAuthorize("hasRole('GESTOR')")
      @ApiOperation(value = "Se modifica una lista de tipos de derechos a partir de su id", response = TipoDerechoDTO.class, tags = "TipoDerecho")
      @PutMapping("/{id}")
      @ResponseBody
      public ResponseEntity<?> update(@PathVariable(value = "id") Long id, @RequestBody TipoDerechoDTO tipoDerechoModified) {
          try{
              Optional<TipoDerechoDTO> listaSalidaUpdated = tipoDerechoService.update(tipoDerechoModified, id);
              return new ResponseEntity<>(tipoDerechoModified, HttpStatus.OK);
          }catch (Exception e){
              return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
          }
      }

      @PreAuthorize("hasRole('GESTOR') or hasRole('ADMINISTRADOR')")
      @ApiOperation(value = "Se elimina una lista tipos de derechos a partir de su id", response = TipoDerechoDTO.class, tags = "TipoDerecho")
      @DeleteMapping("/{id}")
      public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) throws Exception {
          try{
              tipoDerechoService.delete(id);
              return new ResponseEntity<>("Ok", HttpStatus.OK);
          }catch (Exception e){
              return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
          }
      }

      @PreAuthorize("hasRole('GESTOR')")
      @ApiOperation(value = "Se eliminan todos las listas de tipos de derechos a partir de su id", response = TipoDerechoDTO.class, tags = "TipoDerecho")
      @DeleteMapping("/")
      public ResponseEntity<?> deleteAll() throws Exception {
          try{
              tipoDerechoService.deleteAll();
              return new ResponseEntity<>("Ok", HttpStatus.OK);
          }catch (Exception e){
              return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
          }
      }
}
