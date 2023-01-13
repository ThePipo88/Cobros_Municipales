package org.una.municipalidad.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.control.Alert;
import org.una.municipalidad.dto.AuthenticationResponse;
import org.una.municipalidad.dto.ReciboDTO;
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

public class ReciboService {

    private static final HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
    private static final String serviceURL = "http://localhost:8089/recibo";
    private static ObjectMapper mapper = new ObjectMapper();
    private static Mensaje msg = new Mensaje();

    public static List<ReciboDTO> getBetweenFecha(String fechaInicial, String fechaFinal) throws InterruptedException, ExecutionException, IOException {
        List<ReciboDTO> recibos = null;
        AuthenticationResponse token = (AuthenticationResponse) AppContext.getInstance().get("Rol");

        HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL+"/findByReciboBetweenFecha/"+fechaInicial+"/"+fechaFinal))
                .setHeader("Content-Type", "application/json").setHeader("AUTHORIZATION", "Bearer " + token.getJwt()).GET().build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, HttpResponse.BodyHandlers.ofString());

        if(response.get().statusCode() == 500){
            msg.show(Alert.AlertType.ERROR, "Error", "Datos no encontrados entre el rango de fechas especificadas");
        }
        else if(response.get().statusCode() == 403){
            msg.show(Alert.AlertType.ERROR, "Error", "Se requiere un permiso adicional para realizar esta acción");
        }
        else
        {
            recibos = mapper.readValue(response.get().body(), new TypeReference<List<ReciboDTO>>() {});
            //List<HoraMarcajeDTO> beans = JSONUtils.convertFromJsonToList(response.get().body(), new TypeReference<List<HoraMarcajeDTO>>() {});
            //beans.forEach(System.out::println);
        }
        return recibos;
    }

}
