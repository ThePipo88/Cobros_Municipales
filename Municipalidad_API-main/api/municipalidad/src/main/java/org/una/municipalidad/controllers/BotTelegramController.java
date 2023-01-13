package org.una.municipalidad.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.una.municipalidad.dto.CobroCanceladoDTO;
import org.una.municipalidad.dto.CobroGeneradoDTO;
import org.una.municipalidad.dto.ParametroDTO;
import org.una.municipalidad.services.ICobroCanceladoService;
import org.una.municipalidad.services.ICobroGeneradoService;
import org.una.municipalidad.services.IParametroService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/botTelegram")
@Api(tags = {"BotTelegram"})
public class BotTelegramController {

    @Autowired
    private IParametroService parametroService;

    @Autowired
    private ICobroCanceladoService cobroCanceladoService;

    @Autowired
    private ICobroGeneradoService cobroGeneradoService;

    @PreAuthorize("hasRole('TELEGRAM')")
    @ApiOperation(value = "Obtiene un parametro a partir de su id", response = ParametroDTO.class, tags = "Parametro")
    @GetMapping("/{id}")
    public ResponseEntity<?> findByInformacion(@PathVariable(value = "id") Long id) {
        try{
            Optional<ParametroDTO> parametroFound = parametroService.findById(id);
            return new ResponseEntity<>(parametroFound, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('TELEGRAM')")
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

    @PreAuthorize("hasRole('TELEGRAM')")
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

}
