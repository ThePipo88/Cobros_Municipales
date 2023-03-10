package org.una.municipalidad.service;

import javafx.scene.control.Alert;
import org.una.municipalidad.dto.AuthenticationRequest;
import org.una.municipalidad.dto.AuthenticationResponse;
import org.una.municipalidad.util.AppContext;
import org.una.municipalidad.util.Mensaje;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class LoginService {

    private static final HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
    private static final String serviceURL = "http://localhost:8089/autenticacion";

    private static Mensaje msg = new Mensaje();
    private static ObjectMapper mapper = new ObjectMapper();

    public static AuthenticationResponse login(String cedula, String password) throws InterruptedException, ExecutionException, IOException
    {
        AuthenticationRequest bean = new AuthenticationRequest();

        bean.setCedula(cedula);
        bean.setPassword(password);

        String inputJson = mapper.writeValueAsString(bean);

        HttpRequest request = HttpRequest.newBuilder(URI.create(serviceURL)).header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(inputJson)).build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(request,HttpResponse.BodyHandlers.ofString());
        System.out.println(response.get().body());

        if(response.get().statusCode() == 500){
            //msg.show(Alert.AlertType.ERROR, "Error", "Usuario o contraeña incorrectos, vuelva a ingresarlo nuevamente");
        }
        else if (response.get().statusCode() == 401) {
            msg.show(Alert.AlertType.ERROR, "Error", "Contraseña incorrecta, vuelva a intentarlo");
        }
        else {
            AuthenticationResponse authenticationResponse = mapper.readValue(response.get().body(), AuthenticationResponse.class);//Convert Json into object of Specific Type
            AppContext.getInstance().set("Rol",authenticationResponse);
            return authenticationResponse;
        }
        return null;
    }
}