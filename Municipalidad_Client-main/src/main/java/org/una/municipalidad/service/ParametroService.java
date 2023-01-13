package org.una.municipalidad.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.control.Alert;
import org.una.municipalidad.dto.AuthenticationResponse;
import org.una.municipalidad.dto.CementerioDTO;
import org.una.municipalidad.dto.ContribuyenteServicioDTO;
import org.una.municipalidad.dto.ParametroDTO;
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

public class ParametroService {

    private static final HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
    private static final String serviceURL = "http://localhost:8089/parametros";
    private static ObjectMapper mapper = new ObjectMapper();
    private static Mensaje msg = new Mensaje();

    public static List<ParametroDTO> getParametros() throws InterruptedException, ExecutionException, IOException {
        List<ParametroDTO> parametros = null;
        AuthenticationResponse token = (AuthenticationResponse) AppContext.getInstance().get("Rol");

        HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL + "/"))
                .setHeader("Content-Type", "application/json").setHeader("AUTHORIZATION", "Bearer " + token.getJwt()).GET().build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, HttpResponse.BodyHandlers.ofString());

        if (response.get().statusCode() == 500) {
            msg.show(Alert.AlertType.ERROR, "Error", "No se encontraron parametros ");
        } else if (response.get().statusCode() == 403) {
            msg.show(Alert.AlertType.ERROR, "Error", "Se requiere un permiso adicional para realizar esta acción");
        } else {
            parametros = mapper.readValue(response.get().body(), new TypeReference<List<ParametroDTO>>() {
            });

        }
        return parametros;
    }

    public static void createParametro(ParametroDTO parametroDTO) throws InterruptedException, ExecutionException, JsonProcessingException {

        AuthenticationResponse token = (AuthenticationResponse) AppContext.getInstance().get("Rol");
        String inputJson = mapper.writeValueAsString(parametroDTO);

        HttpRequest request = HttpRequest.newBuilder(URI.create(serviceURL + "/"))
                .setHeader("Content-Type", "application/json").setHeader("AUTHORIZATION", "Bearer " + token.getJwt())
                .POST(HttpRequest.BodyPublishers.ofString(inputJson)).build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        msg.show(Alert.AlertType.INFORMATION, "EXITO ", "Datos ingresados con éxito");


        if (response.get().statusCode() == 500)
            System.out.println("No se pudo crear la solicitud");

        else {

        }
        response.join();
    }

    public static ParametroDTO getParametroById(long id) throws InterruptedException, ExecutionException, IOException
    {
        ParametroDTO parametro = null;
        AuthenticationResponse token = (AuthenticationResponse) AppContext.getInstance().get("Rol");

        HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL+"/"+id))
                .setHeader("Content-Type", "application/json").setHeader("AUTHORIZATION", "Bearer " + token.getJwt()).GET().build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, HttpResponse.BodyHandlers.ofString());

        if(response.get().statusCode() == 500) {

        }else {
            parametro = mapper.readValue(response.get().body(), new TypeReference<ParametroDTO>() {});
        }
        return parametro;
    }

    public static void updateParametro(ParametroDTO bean, long id) throws InterruptedException, ExecutionException, IOException
    {
        AuthenticationResponse token = (AuthenticationResponse) AppContext.getInstance().get("Rol");
        String inputJson = mapper.writeValueAsString(bean);

        HttpRequest request = HttpRequest.newBuilder(URI.create(serviceURL+"/"+id))
                .setHeader("Content-Type", "application/json").setHeader("AUTHORIZATION", "Bearer " + token.getJwt())
                .PUT(HttpRequest.BodyPublishers.ofString(inputJson)).build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(request,HttpResponse.BodyHandlers.ofString());

        if(response.get().statusCode() == 500) {
            System.out.println("No se pudo actualizar el parametro");
        }

        response.join();
    }

    public static void deleteParametro(String id) throws InterruptedException, ExecutionException, IOException
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