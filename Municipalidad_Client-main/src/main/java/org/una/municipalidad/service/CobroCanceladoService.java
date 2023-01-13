package org.una.municipalidad.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.control.Alert;
import lombok.extern.java.Log;
import org.una.municipalidad.dto.AuthenticationResponse;
import org.una.municipalidad.dto.CobroCanceladoDTO;
import org.una.municipalidad.dto.CobroGeneradoDTO;
import org.una.municipalidad.dto.TransaccionDTO;
import org.una.municipalidad.util.AppContext;
import org.una.municipalidad.util.Mensaje;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CobroCanceladoService {

    private static final HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
    private static final String serviceURL = "http://localhost:8089/cobrosCancelados";
    private static ObjectMapper mapper = new ObjectMapper();
    private static Mensaje msg = new Mensaje();

    public static List<CobroCanceladoDTO> getCobrosCancelados(String fechaInicial, String fechaFinal) throws InterruptedException, ExecutionException, IOException
    {
        List<CobroCanceladoDTO> cobros = null;
        AuthenticationResponse token = (AuthenticationResponse) AppContext.getInstance().get("Rol");

        HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL+"/byFechaCreacion/"+fechaInicial+"/"+fechaFinal))
                .setHeader("Content-Type", "application/json").setHeader("AUTHORIZATION", "Bearer " + token.getJwt()).GET().build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, HttpResponse.BodyHandlers.ofString());

        if(response.get().statusCode() == 500){
            msg.show(Alert.AlertType.ERROR, "Error", "No se encontraron cobros cancelados");
        }
        else if(response.get().statusCode() == 403){
            msg.show(Alert.AlertType.ERROR, "Error", "Se requiere un permiso adicional para realizar esta acción");
        }
        else
        {
            cobros = mapper.readValue(response.get().body(), new TypeReference<List<CobroCanceladoDTO>>() {});
            TransaccionDTO transaccionDTO = new TransaccionDTO();
            transaccionDTO.setAccion("Se obtuvo una lista de todos los cobros cancelados");
            transaccionDTO.setObjeto("Fercha inicial:" +fechaInicial+ " Fecha final: "+fechaFinal);
            transaccionDTO.setUsuario(token.getUsuarioDTO());
            TransaccionService.createTransaccion(transaccionDTO);

        }
        return cobros;
    }

    public static void createSolicitud(CobroCanceladoDTO cobroCanceladoDTO) throws InterruptedException, ExecutionException, JsonProcessingException {

        Long id = cobroCanceladoDTO.getId();

        AuthenticationResponse token = (AuthenticationResponse) AppContext.getInstance().get("Rol");
        String inputJson = mapper.writeValueAsString(cobroCanceladoDTO);

        HttpRequest request = HttpRequest.newBuilder(URI.create(serviceURL+"/"))
                .setHeader("Content-Type", "application/json").setHeader("AUTHORIZATION", "Bearer " + token.getJwt())
                .POST(HttpRequest.BodyPublishers.ofString(inputJson)).build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(request,HttpResponse.BodyHandlers.ofString());

        if(response.get().statusCode() == 500) {
            System.out.println("No se pudo crear la solicitud");

        }else {
            TransaccionDTO transaccionDTO = new TransaccionDTO();
            transaccionDTO.setAccion("Se creo un nuevo cobro cacelado");
            transaccionDTO.setObjeto("Id cobro: "+String.valueOf(id));
            transaccionDTO.setUsuario(token.getUsuarioDTO());
            TransaccionService.createTransaccion(transaccionDTO);
        }
        response.join();
    }
}
