package org.una.municipalidad.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.control.Alert;
import org.una.municipalidad.dto.*;
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

public class DerechoCementerioService {

    private static final HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
    private static final String serviceURL = "http://localhost:8089/cementerio";
    private static ObjectMapper mapper = new ObjectMapper();
    private static Mensaje msg = new Mensaje();

    public static List<CementerioDTO> findByDerechoId(Long id) throws InterruptedException, ExecutionException, IOException
    {
        List<CementerioDTO> cobros = null;
        AuthenticationResponse token = (AuthenticationResponse) AppContext.getInstance().get("Rol");

        HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL+"/byServicioId/"+id))
                .setHeader("Content-Type", "application/json").setHeader("AUTHORIZATION", "Bearer " + token.getJwt()).GET().build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, HttpResponse.BodyHandlers.ofString());

        if(response.get().statusCode() == 500){
            msg.show(Alert.AlertType.ERROR, "Advertencia", "No se encontraron derechos de cementerio");
        }
        else if(response.get().statusCode() == 403){
            msg.show(Alert.AlertType.ERROR, "Advertencia", "Se requiere un permiso adicional para realizar esta acción");
        }
        else
        {
            cobros = mapper.readValue(response.get().body(), new TypeReference<List<CementerioDTO>>() {});

            TransaccionDTO transaccionDTO = new TransaccionDTO();
            transaccionDTO.setAccion("Se obtuvo un derecho cementerio por su id");
            transaccionDTO.setObjeto("Id: "+String.valueOf(id));
            transaccionDTO.setUsuario(token.getUsuarioDTO());
            TransaccionService.createTransaccion(transaccionDTO);
        }
        return cobros;
    }

    public static void updateDerecho(CementerioDTO bean, long id) throws InterruptedException, ExecutionException, IOException
    {
        AuthenticationResponse token = (AuthenticationResponse) AppContext.getInstance().get("Rol");
        String inputJson = mapper.writeValueAsString(bean);

        HttpRequest request = HttpRequest.newBuilder(URI.create(serviceURL+"/"+id))
                .setHeader("Content-Type", "application/json").setHeader("AUTHORIZATION", "Bearer " + token.getJwt())
                .PUT(HttpRequest.BodyPublishers.ofString(inputJson)).build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(request,HttpResponse.BodyHandlers.ofString());

        if(response.get().statusCode() == 500) {
            System.out.println("No se pudo actualizar el cementerio");
        }
        else{
            TransaccionDTO transaccionDTO = new TransaccionDTO();
            transaccionDTO.setAccion("Se modifico u derecho de cementerio");
            transaccionDTO.setObjeto("Id: "+String.valueOf(id));
            transaccionDTO.setUsuario(token.getUsuarioDTO());
            TransaccionService.createTransaccion(transaccionDTO);
        }

        response.join();
    }

    public static CementerioDTO getDerechoId(long id) throws InterruptedException, ExecutionException, IOException
    {
        CementerioDTO cobro = null;
        AuthenticationResponse token = (AuthenticationResponse) AppContext.getInstance().get("Rol");

        HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL+"/"+id))
                .setHeader("Content-Type", "application/json").setHeader("AUTHORIZATION", "Bearer " + token.getJwt()).GET().build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, HttpResponse.BodyHandlers.ofString());

        if(response.get().statusCode() == 500) {

        }else {
            cobro = mapper.readValue(response.get().body(), new TypeReference<CementerioDTO>() {});
        }
        return cobro;
    }

    public static CementerioDTO createCementerio(CementerioDTO cementerioDTO) throws InterruptedException, ExecutionException, JsonProcessingException
    {
        CementerioDTO cementerio = null;
        AuthenticationResponse token = (AuthenticationResponse) AppContext.getInstance().get("Rol");
        String inputJson = mapper.writeValueAsString(cementerioDTO);

        HttpRequest request = HttpRequest.newBuilder(URI.create(serviceURL+"/"))
                .setHeader("Content-Type", "application/json").setHeader("AUTHORIZATION", "Bearer " + token.getJwt())
                .POST(HttpRequest.BodyPublishers.ofString(inputJson)).build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(request,HttpResponse.BodyHandlers.ofString());

        if(response.get().statusCode() == 500)
            System.out.println("No se pudo crear la solicitud");

        else {
            cementerio = mapper.readValue(response.get().body(), new TypeReference<CementerioDTO>() {});
        }
        return cementerio;
    }

    public static void deleteDerechoCementerio(String id) throws InterruptedException, ExecutionException, IOException
    {

        AuthenticationResponse token = (AuthenticationResponse) AppContext.getInstance().get("Rol");

        HttpRequest request = HttpRequest.newBuilder(URI.create(serviceURL+"/"+id)).setHeader("Content-Type", "application/json")
                .setHeader("AUTHORIZATION", "Bearer " + token.getJwt()).DELETE().build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(request,HttpResponse.BodyHandlers.ofString());

        if(response.get().statusCode() == 500) {

        }else {

        }
        response.join();
    }
}
