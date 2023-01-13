package org.una.municipalidad.controllers;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.una.municipalidad.dto.RolDTO;
import org.una.municipalidad.dto.SolicitudPermisoDTO;
import org.una.municipalidad.dto.TransaccionDTO;
import org.una.municipalidad.services.ISolicitudPermisoService;
import org.una.municipalidad.utils.MapperUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/SolicitudPermiso")
@Api(tags = {"SolicitudPermiso"})
public class SolicitudPermisoController {

    @Autowired
    private ISolicitudPermisoService solicitudPermisoService;

    @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('AUDITOR')")
    @ApiOperation(value = "Obtiene un permiso a partir de su id", response = SolicitudPermisoDTO.class, tags = "SolicitudPermiso")
    @GetMapping("/findById/{id}")
    public ResponseEntity<?> findById(@PathVariable(value = "id") Long id) {
        try{
            Optional<SolicitudPermisoDTO> usuarioFound = solicitudPermisoService.findById(id);
            return new ResponseEntity<>(usuarioFound, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('AUDITOR')")
    @ApiOperation(value = "Obtiene una lista de todos los permisos", response = SolicitudPermisoDTO.class, responseContainer = "List", tags = "SolicitudPermiso")
    @GetMapping()
    public @ResponseBody ResponseEntity<?> findAll() {
        try{
            Optional<List<SolicitudPermisoDTO>> result = solicitudPermisoService.findAll();
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/byFechaCreacion/{startDate}/{endDate}")
    @ApiOperation(value = "Obtiene una lista de permisos entre dos fechas dadas", response = SolicitudPermisoDTO.class, responseContainer = "SolicitudPermisoDTO", tags = "SolicitudPermiso")
    public ResponseEntity<?> findByFechaCreacionBetween(@PathVariable(value = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate, @PathVariable(value = "endDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        try{
            Optional<List<SolicitudPermisoDTO>> result = solicitudPermisoService.findByFechaCreacionBetween(startDate,endDate);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Se crea un nuevo permiso", response = SolicitudPermisoDTO.class, tags = "SolicitudPermiso")
    @PostMapping("/")
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody SolicitudPermisoDTO solicitudPermisoDTO) {
        try{
            SolicitudPermisoDTO permisoCreated  = solicitudPermisoService.create(solicitudPermisoDTO);
            return new ResponseEntity<>(permisoCreated, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @ApiOperation(value = "Se modifica un permiso a partir de su id", response = SolicitudPermisoDTO.class, tags = "SolicitudPermiso")
    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> update(@PathVariable(value = "id") Long id, @RequestBody SolicitudPermisoDTO solicitudPermisoModified) {
        try {
            Optional<SolicitudPermisoDTO> solicitudPermisoUpdated = solicitudPermisoService.update(solicitudPermisoModified, id);
            return new ResponseEntity<>(solicitudPermisoModified, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @ApiOperation(value = "Se elimina un permiso a partir de su id", response = SolicitudPermisoDTO.class, tags = "SolicitudPermiso")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) throws Exception {
        try {
            solicitudPermisoService.delete(id);
            return new ResponseEntity<>("Ok", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @ApiOperation(value = "Se eliminan todos los permisos", response = SolicitudPermisoDTO.class, tags = "SolicitudPermiso")
    @DeleteMapping("/")
    public ResponseEntity<?> deleteAll() throws Exception {
        try {
            solicitudPermisoService.deleteAll();
            return new ResponseEntity<>("Ok", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
