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

public class RutaBusService {

    private static final HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
    private static final String serviceURL = "http://localhost:8089/rutasBus";
    private static ObjectMapper mapper = new ObjectMapper();
    private static Mensaje msg = new Mensaje();

    public static List<RutaBusDTO> findByServicioId(Long id) throws InterruptedException, ExecutionException, IOException
    {
        List<RutaBusDTO> ruta = null;
        AuthenticationResponse token = (AuthenticationResponse) AppContext.getInstance().get("Rol");

        HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL+"/byServicioId/"+id))
                .setHeader("Content-Type", "application/json").setHeader("AUTHORIZATION", "Bearer " + token.getJwt()).GET().build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, HttpResponse.BodyHandlers.ofString());

        if(response.get().statusCode() == 500){
            msg.show(Alert.AlertType.ERROR, "Advertencia", "No se encontraron rutas de buses");
        }
        else if(response.get().statusCode() == 403){
            msg.show(Alert.AlertType.ERROR, "Advertencia", "Se requiere un permiso adicional para realizar esta acción");
        }
        else
        {
            ruta = mapper.readValue(response.get().body(), new TypeReference<List<RutaBusDTO>>() {});
        }
        return ruta;
    }

    public static void updateRuta(RutaBusDTO bean, long id) throws InterruptedException, ExecutionException, IOException
    {
        AuthenticationResponse token = (AuthenticationResponse) AppContext.getInstance().get("Rol");
        String inputJson = mapper.writeValueAsString(bean);

        HttpRequest request = HttpRequest.newBuilder(URI.create(serviceURL+"/"+id))
                .setHeader("Content-Type", "application/json").setHeader("AUTHORIZATION", "Bearer " + token.getJwt())
                .PUT(HttpRequest.BodyPublishers.ofString(inputJson)).build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(request,HttpResponse.BodyHandlers.ofString());

        if(response.get().statusCode() == 500) {
            System.out.println("No se pudo actualizar la ruta de bus");
        }

        response.join();
    }

    public static RutaBusDTO getRutaId(long id) throws InterruptedException, ExecutionException, IOException
    {
        RutaBusDTO ruta = null;
        AuthenticationResponse token = (AuthenticationResponse) AppContext.getInstance().get("Rol");

        HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL+"/"+id))
                .setHeader("Content-Type", "application/json").setHeader("AUTHORIZATION", "Bearer " + token.getJwt()).GET().build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, HttpResponse.BodyHandlers.ofString());

        if(response.get().statusCode() == 500) {

        }else {
            ruta = mapper.readValue(response.get().body(), new TypeReference<RutaBusDTO>() {});
        }
        return ruta;
    }

    public static RutaBusDTO createRutaBus(RutaBusDTO rutaBusDTO) throws InterruptedException, ExecutionException, JsonProcessingException
    {

        RutaBusDTO ruta = null;
        AuthenticationResponse token = (AuthenticationResponse) AppContext.getInstance().get("Rol");
        String inputJson = mapper.writeValueAsString(rutaBusDTO);

        HttpRequest request = HttpRequest.newBuilder(URI.create(serviceURL+"/"))
                .setHeader("Content-Type", "application/json").setHeader("AUTHORIZATION", "Bearer " + token.getJwt())
                .POST(HttpRequest.BodyPublishers.ofString(inputJson)).build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(request,HttpResponse.BodyHandlers.ofString());

        if(response.get().statusCode() == 500) {
            System.out.println("No se pudo crear la solicitud");

        }else {
            ruta = mapper.readValue(response.get().body(), new TypeReference<RutaBusDTO>() {});
        }
        return ruta;
    }

    public static void deleteRutaBus(String id) throws InterruptedException, ExecutionException, IOException
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
