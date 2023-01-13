package org.una.municipalidad.controllers;
import java.util.List;
import java.util.Optional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.una.municipalidad.dto.CementerioDTO;
import org.una.municipalidad.dto.RutaBusDTO;
import org.una.municipalidad.services.ICementerioService;

    @RestController
    @RequestMapping("/cementerio")
    @Api(tags = {"Cementerio"})
    public class CementerioController {

        @Autowired
        private ICementerioService cementerioService;

        @PreAuthorize("hasRole('GESTOR') or hasRole('GERENTE') or hasRole('AUDITOR')")
        @ApiOperation(value = "Obtiene una lista de todos los derechos de cementerios", response = CementerioDTO.class, responseContainer = "List", tags = "Cementerio")
        @GetMapping()
        public @ResponseBody
        ResponseEntity<?> findAll() {
            try{
            Optional<List<CementerioDTO>> result = cementerioService.findAll();
            return new ResponseEntity<>(result, HttpStatus.OK);}
            catch(Exception e){
                return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
                }
        }

        @PreAuthorize("hasRole('GESTOR') or hasRole('GERENTE') or hasRole('AUDITOR') or hasRole('ADMINISTRADOR')")
        @ApiOperation(value = "Obtiene un derecho de cementerio a partir de su id", response = CementerioDTO.class, tags = "Cementerio")
        @GetMapping("/{id}")
        public ResponseEntity<?> findById(@PathVariable(value = "id") Long id) {
            try{
                Optional<CementerioDTO> rutaBusFound = cementerioService.findById(id);
                return new ResponseEntity<>(rutaBusFound, HttpStatus.OK);
            }
            catch(Exception e){
                return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        @PreAuthorize("hasRole('GESTOR') or hasRole('GERENTE') or hasRole('AUDITOR')")
        @ApiOperation(value = "Obtiene una lista de derechos de cementerio a partir de su sector", response = CementerioDTO.class, responseContainer = "List", tags = "Cementerio")
        @GetMapping("/sector/{term}")
        public ResponseEntity<?> findBySector(@PathVariable(value = "term") String term) {
            try{
            Optional<List<CementerioDTO>> result = cementerioService.findBySector(term);
            return new ResponseEntity<>(result, HttpStatus.OK);}
            catch(Exception e){
                return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        @PreAuthorize("hasRole('GESTOR') or hasRole('GERENTE')")
        @ApiOperation(value = "Obtiene una lista de derechos de cementerio con base en si esta ocupado o no", response = CementerioDTO.class, responseContainer = "List", tags = "Cementerio")
        @GetMapping("/ByOcupado/{ocupado}")
        public ResponseEntity<?> findByOcupado(@PathVariable(value = "ocupado") String ocupado) {
            try {
                Optional<List<CementerioDTO>> result = cementerioService.findByOcupado(ocupado);
            return new ResponseEntity<>(result, HttpStatus.OK);}
            catch(Exception e){
                return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        @PreAuthorize("hasRole('GESTOR') or hasRole('GERENTE') or hasRole('AUDITOR') or hasRole('ADMINISTRADOR')")
        @ApiOperation(value = "Se obtiene una lista de derechos de cementerio a partir del id del servicio", response = RutaBusDTO.class, responseContainer = "List", tags = "Cementerio")
        @GetMapping("/byServicioId/{id}")
        public ResponseEntity<?> findByServicioId(@PathVariable(value = "id") Long id) {
            try {
                Optional<List<CementerioDTO>> result = cementerioService.findByServicioId(id);
                return new ResponseEntity<>(result, HttpStatus.OK);
            }catch (Exception e){
                return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        @PreAuthorize("hasRole('GESTOR')")
        @ResponseStatus(HttpStatus.OK)
        @ApiOperation(value = "Se crea un derecho de cementerio", response = CementerioDTO.class, tags = "RutaBus")
        @PostMapping("/")
        @ResponseBody
        public ResponseEntity<?> create(@RequestBody CementerioDTO cementerioDTO) {
            try{
            Optional<CementerioDTO> CemeterioCreated = cementerioService.create(cementerioDTO);
            return new ResponseEntity<>(CemeterioCreated, HttpStatus.CREATED);}
            catch(Exception e){
                return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        @PreAuthorize("hasRole('GESTOR')")
        @ApiOperation(value = "Se modifica un derecho de cementerio apartir de su id", response = CementerioDTO.class, tags = "Cementerio")
        @PutMapping("/{id}")
        @ResponseBody
        public ResponseEntity<?> update(@PathVariable(value = "id") Long id, @RequestBody CementerioDTO cementerioModified) {
            try{
            Optional<CementerioDTO> cementerioUpdated = cementerioService.update(cementerioModified, id);
            return new ResponseEntity<>(cementerioModified, HttpStatus.OK);}
            catch(Exception e){
                return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        @PreAuthorize("hasRole('GESTOR') or hasRole('ADMINISTRADOR')")
        @ApiOperation(value = "Se elimina un derecho de cementerio con su id", response = CementerioDTO.class, tags = "Cementerio")
        @DeleteMapping("/{id}")
        public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) throws Exception {
            try {
                cementerioService.delete(id);
                return new ResponseEntity<>("Ok", HttpStatus.OK);
            }
            catch(Exception e){
                return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        @PreAuthorize("hasRole('GESTOR')")
        @ApiOperation(value = "Se eliminan todos los derechos de cementerio", response = CementerioDTO.class, tags = "Cementerio")
        @DeleteMapping("/")
        public ResponseEntity<?> deleteAll() throws Exception {
            try{
            cementerioService.deleteAll();
            return new ResponseEntity<>("Ok", HttpStatus.OK);}
            catch(Exception e){
                return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
}
