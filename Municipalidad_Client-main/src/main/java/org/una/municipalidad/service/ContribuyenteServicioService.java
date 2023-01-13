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

public class ContribuyenteServicioService {
    private static final HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
    private static final String serviceURL = "http://localhost:8089/contribuyentesServicios";
    private static ObjectMapper mapper = new ObjectMapper();
    private static Mensaje msg = new Mensaje();

    public static List<ContribuyenteServicioDTO> contribuyenteServicio() throws InterruptedException, ExecutionException, IOException
    {
        List<ContribuyenteServicioDTO> cobros = null;
        AuthenticationResponse token = (AuthenticationResponse) AppContext.getInstance().get("Rol");

        HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL))
                .setHeader("Content-Type", "application/json").setHeader("AUTHORIZATION", "Bearer " + token.getJwt()).GET().build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, HttpResponse.BodyHandlers.ofString());

        if(response.get().statusCode() == 500){
            msg.show(Alert.AlertType.ERROR, "Advertencia", "No se encontraron cotribuyentes servicios");
        }
        else if(response.get().statusCode() == 403){
            msg.show(Alert.AlertType.ERROR, "Advertencia", "Se requiere un permiso adicional para realizar esta acción");
        }
        else
        {
            cobros = mapper.readValue(response.get().body(), new TypeReference<List<ContribuyenteServicioDTO>>() {});
            TransaccionDTO transaccionDTO = new TransaccionDTO();
            transaccionDTO.setAccion("Se obtuvo una lista de cotribuyetes servicios");
            transaccionDTO.setObjeto(" ");
            transaccionDTO.setUsuario(token.getUsuarioDTO());
            TransaccionService.createTransaccion(transaccionDTO);
        }
        return cobros;
    }

    public static void updateContribuyenteServicio(ContribuyenteServicioDTO bean, long id) throws InterruptedException, ExecutionException, IOException
    {
        AuthenticationResponse token = (AuthenticationResponse) AppContext.getInstance().get("Rol");
        String inputJson = mapper.writeValueAsString(bean);

        HttpRequest request = HttpRequest.newBuilder(URI.create(serviceURL+"/"+id))
                .setHeader("Content-Type", "application/json").setHeader("AUTHORIZATION", "Bearer " + token.getJwt())
                .PUT(HttpRequest.BodyPublishers.ofString(inputJson)).build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(request,HttpResponse.BodyHandlers.ofString());

        if(response.get().statusCode() == 500) {
            System.out.println("No se pudo actualizar el cobro generado");
        }
        else {
            TransaccionDTO transaccionDTO = new TransaccionDTO();
            transaccionDTO.setAccion("Se modifico un cotribuyente servicio");
            transaccionDTO.setObjeto("Id: "+String.valueOf(id));
            transaccionDTO.setUsuario(token.getUsuarioDTO());
            TransaccionService.createTransaccion(transaccionDTO);
        }

        response.join();
    }

    public static ContribuyenteServicioDTO getContribuyenteServicioId(long id) throws InterruptedException, ExecutionException, IOException
    {
        ContribuyenteServicioDTO cobro = null;
        AuthenticationResponse token = (AuthenticationResponse) AppContext.getInstance().get("Rol");

        HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL+"/"+id))
                .setHeader("Content-Type", "application/json").setHeader("AUTHORIZATION", "Bearer " + token.getJwt()).GET().build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, HttpResponse.BodyHandlers.ofString());

        if(response.get().statusCode() == 500) {

        }else {
            cobro = mapper.readValue(response.get().body(), new TypeReference<ContribuyenteServicioDTO>() {});
            TransaccionDTO transaccionDTO = new TransaccionDTO();
            transaccionDTO.setAccion("Se obtuvo un contribuyente servicio por medio de un id");
            transaccionDTO.setObjeto("Id: "+String.valueOf(id));
            transaccionDTO.setUsuario(token.getUsuarioDTO());
            TransaccionService.createTransaccion(transaccionDTO);
        }
        return cobro;
    }

    public static void createContribuyenteServicio(ContribuyenteServicioDTO contribuyenteServicioDTO) throws InterruptedException, ExecutionException, JsonProcessingException
    {

        AuthenticationResponse token = (AuthenticationResponse) AppContext.getInstance().get("Rol");
        String inputJson = mapper.writeValueAsString(contribuyenteServicioDTO);

        HttpRequest request = HttpRequest.newBuilder(URI.create(serviceURL+"/"))
                .setHeader("Content-Type", "application/json").setHeader("AUTHORIZATION", "Bearer " + token.getJwt())
                .POST(HttpRequest.BodyPublishers.ofString(inputJson)).build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(request,HttpResponse.BodyHandlers.ofString());

        if(response.get().statusCode() == 500)
            System.out.println("No se pudo crear el contribuyente servicio");

        else {
            TransaccionDTO transaccionDTO = new TransaccionDTO();
            transaccionDTO.setAccion("Se creo un contribuyete servicio ");
            transaccionDTO.setObjeto(" ");
            transaccionDTO.setUsuario(token.getUsuarioDTO());
            TransaccionService.createTransaccion(transaccionDTO);
        }
        response.join();
    }

    public static void deleteContribuyenteServicio(String id) throws InterruptedException, ExecutionException, IOException
    {

        AuthenticationResponse token = (AuthenticationResponse) AppContext.getInstance().get("Rol");

        HttpRequest request = HttpRequest.newBuilder(URI.create(serviceURL+"/"+id)).setHeader("Content-Type", "application/json")
                .setHeader("AUTHORIZATION", "Bearer " + token.getJwt()).DELETE().build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(request,HttpResponse.BodyHandlers.ofString());

        if(response.get().statusCode() == 500) {
            System.out.println("Product Not Available to delete");
        }else {

        }
        response.join();
    }
}
