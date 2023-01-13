package org.una.municipalidad.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.una.municipalidad.dto.*;
import org.una.municipalidad.util.AppContext;
import org.una.municipalidad.util.Mensaje;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ContribuyenteService {
    private static final HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
    private static final String serviceURL = "http://localhost:8089/contribuyentes";
    private static ObjectMapper mapper = new ObjectMapper();
    private static Mensaje msg = new Mensaje();

    public static void updateCotribuyete(ContribuyenteDTO bean, long id) throws InterruptedException, ExecutionException, IOException
    {
        AuthenticationResponse token = (AuthenticationResponse) AppContext.getInstance().get("Rol");
        String inputJson = mapper.writeValueAsString(bean);

        HttpRequest request = HttpRequest.newBuilder(URI.create(serviceURL+"/"+id))
                .setHeader("Content-Type", "application/json").setHeader("AUTHORIZATION", "Bearer " + token.getJwt())
                .PUT(HttpRequest.BodyPublishers.ofString(inputJson)).build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(request,HttpResponse.BodyHandlers.ofString());

        if(response.get().statusCode() == 500) {
            System.out.println("No se pudo actualizar el contribuyente");
        }else{
            TransaccionDTO transaccionDTO = new TransaccionDTO();
            transaccionDTO.setAccion("Se solicito modificar un contribuyente");
            transaccionDTO.setObjeto("Id: "+String.valueOf(id));
            transaccionDTO.setUsuario(token.getUsuarioDTO());
            TransaccionService.createTransaccion(transaccionDTO);
        }

        response.join();
    }

    public static ContribuyenteDTO getContribuyenteId(long id) throws InterruptedException, ExecutionException, IOException
    {
        ContribuyenteDTO cobro = null;
        AuthenticationResponse token = (AuthenticationResponse) AppContext.getInstance().get("Rol");

        HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL+"/"+id))
                .setHeader("Content-Type", "application/json").setHeader("AUTHORIZATION", "Bearer " + token.getJwt()).GET().build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, HttpResponse.BodyHandlers.ofString());

        if(response.get().statusCode() == 500) {

        }else {
            cobro = mapper.readValue(response.get().body(), new TypeReference<ContribuyenteDTO>() {});
            TransaccionDTO transaccionDTO = new TransaccionDTO();
            transaccionDTO.setAccion("Se obtuvo un contribuyete por medio de un id");
            transaccionDTO.setObjeto("Id: "+String.valueOf(id));
            transaccionDTO.setUsuario(token.getUsuarioDTO());
            TransaccionService.createTransaccion(transaccionDTO);
        }
        return cobro;
    }

    public static void createContribuyente(ContribuyenteDTO contribuyenteDTO) throws InterruptedException, ExecutionException, JsonProcessingException
    {

        AuthenticationResponse token = (AuthenticationResponse) AppContext.getInstance().get("Rol");
        String inputJson = mapper.writeValueAsString(contribuyenteDTO);

        HttpRequest request = HttpRequest.newBuilder(URI.create(serviceURL+"/"))
                .setHeader("Content-Type", "application/json").setHeader("AUTHORIZATION", "Bearer " + token.getJwt())
                .POST(HttpRequest.BodyPublishers.ofString(inputJson)).build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(request,HttpResponse.BodyHandlers.ofString());

        if(response.get().statusCode() == 500)
            System.out.println("No se pudo crear el contribuyente");

        else {
            TransaccionDTO transaccionDTO = new TransaccionDTO();
            transaccionDTO.setAccion("Se creo un nuevo cotribuyete");
            transaccionDTO.setObjeto(" ");
            transaccionDTO.setUsuario(token.getUsuarioDTO());
            TransaccionService.createTransaccion(transaccionDTO);
        }
        response.join();
    }

    public static ContribuyenteDTO getContribuyenteByCedula(String cedula) throws InterruptedException, ExecutionException, IOException
    {
        ContribuyenteDTO contribuyenteDTO = null;
        AuthenticationResponse token = (AuthenticationResponse) AppContext.getInstance().get("Rol");

        HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL+"/findByCedula/"+cedula))
                .setHeader("Content-Type", "application/json").setHeader("AUTHORIZATION", "Bearer " + token.getJwt()).GET().build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, HttpResponse.BodyHandlers.ofString());

        if(response.get().statusCode() == 500) {

        }else {
            contribuyenteDTO = mapper.readValue(response.get().body(), new TypeReference<ContribuyenteDTO>() {});
            TransaccionDTO transaccionDTO = new TransaccionDTO();
            transaccionDTO.setAccion("Se obtuvo un contribuyente por medio de su cedula");
            transaccionDTO.setObjeto("Cedula: "+cedula);
            transaccionDTO.setUsuario(token.getUsuarioDTO());
            TransaccionService.createTransaccion(transaccionDTO);
        }
        return contribuyenteDTO;
    }

    public static void deleteContribuyente(String id) throws InterruptedException, ExecutionException, IOException
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
