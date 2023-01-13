package org.una.municipalidad.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.control.Alert;
import org.una.municipalidad.dto.AuthenticationResponse;
import org.una.municipalidad.dto.CobroGeneradoDTO;
import org.una.municipalidad.dto.ContribuyenteDTO;
import org.una.municipalidad.dto.TransaccionDTO;
import org.una.municipalidad.util.AppContext;
import org.una.municipalidad.util.Mensaje;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CobroGeneradoService {
    private static final HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
    private static final String serviceURL = "http://localhost:8089/cobrosGenerados";
    private static ObjectMapper mapper = new ObjectMapper();
    private static Mensaje msg = new Mensaje();

    public static List<CobroGeneradoDTO> findCobroByCedula(String cedula) throws InterruptedException, ExecutionException, IOException{

        List<CobroGeneradoDTO> cobros = null;
        AuthenticationResponse token = (AuthenticationResponse) AppContext.getInstance().get("Rol");

        HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL + "/ByCedula/" + cedula))
                .setHeader("Content-Type", "application/json").setHeader("AUTHORIZATION", "Bearer " + token.getJwt()).GET().build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, HttpResponse.BodyHandlers.ofString());

        if(response.get().statusCode() == 500){
            //msg.show(Alert.AlertType.ERROR, "Error", "No se encontraron cobros generados");
        }
        else if(response.get().statusCode() == 403){
            msg.show(Alert.AlertType.ERROR, "Error", "Se requiere un permiso adicional para realizar esta acción");
        }
        else
        {
            cobros = mapper.readValue(response.get().body(), new TypeReference<List<CobroGeneradoDTO>>() {});
            TransaccionDTO transaccionDTO = new TransaccionDTO();
            transaccionDTO.setAccion("Se obtuvo un cobro generado por medio de una cedula");
            transaccionDTO.setObjeto("Cedula:" +cedula);
            transaccionDTO.setUsuario(token.getUsuarioDTO());
            TransaccionService.createTransaccion(transaccionDTO);
        }
        return cobros;
    }

    public static List<CobroGeneradoDTO> getCobrosGenerados() throws InterruptedException, ExecutionException, IOException
    {
        List<CobroGeneradoDTO> cobros = null;
        AuthenticationResponse token = (AuthenticationResponse) AppContext.getInstance().get("Rol");

        HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL))
                .setHeader("Content-Type", "application/json").setHeader("AUTHORIZATION", "Bearer " + token.getJwt()).GET().build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, HttpResponse.BodyHandlers.ofString());

        if(response.get().statusCode() == 500){
            //msg.show(Alert.AlertType.ERROR, "Error", "No se encontraron cobros generados");
        }
        else if(response.get().statusCode() == 403){
            msg.show(Alert.AlertType.ERROR, "Error", "Se requiere un permiso adicional para realizar esta acción");
        }
        else
        {
            cobros = mapper.readValue(response.get().body(), new TypeReference<List<CobroGeneradoDTO>>() {});
            TransaccionDTO transaccionDTO = new TransaccionDTO();
            transaccionDTO.setAccion("Se obtienen todos los cobros generados");
            transaccionDTO.setObjeto(" ");
            transaccionDTO.setUsuario(token.getUsuarioDTO());
            TransaccionService.createTransaccion(transaccionDTO);
        }
        return cobros;
    }


    public static CobroGeneradoDTO getCobroGeneradoId(long id) throws InterruptedException, ExecutionException, IOException
    {
        CobroGeneradoDTO cobro = null;
        AuthenticationResponse token = (AuthenticationResponse) AppContext.getInstance().get("Rol");

        HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL+"/"+id))
                .setHeader("Content-Type", "application/json").setHeader("AUTHORIZATION", "Bearer " + token.getJwt()).GET().build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, HttpResponse.BodyHandlers.ofString());
        response.thenAccept(res -> System.out.println(res));

        if(response.get().statusCode() == 500) {

        }else {
            cobro = mapper.readValue(response.get().body(), new TypeReference<CobroGeneradoDTO>() {});
            TransaccionDTO transaccionDTO = new TransaccionDTO();
            transaccionDTO.setAccion("Se obtuvo cobro generado por medio de un id");
            transaccionDTO.setObjeto("Id: "+String.valueOf(id));
            transaccionDTO.setUsuario(token.getUsuarioDTO());
            TransaccionService.createTransaccion(transaccionDTO);
        }
        return cobro;
    }

    public static void updateCobro(CobroGeneradoDTO bean, long id) throws InterruptedException, ExecutionException, IOException
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
        else{
            TransaccionDTO transaccionDTO = new TransaccionDTO();
            transaccionDTO.setAccion("Se modifico un cobro generado por medio de un id");
            transaccionDTO.setObjeto("Id: "+String.valueOf(id));
            transaccionDTO.setUsuario(token.getUsuarioDTO());
            TransaccionService.createTransaccion(transaccionDTO);
        }
        response.join();
    }

    public static List<CobroGeneradoDTO> getDatosGeneracionCobros(String idCobro) throws InterruptedException, ExecutionException, IOException {
        List<CobroGeneradoDTO> datosGeneracionCobros = null;

        AuthenticationResponse token = (AuthenticationResponse) AppContext.getInstance().get("Rol");

        HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL+"/busqueda/"+idCobro))
                .setHeader("Content-Type", "application/json").setHeader("AUTHORIZATION", "Bearer " + token.getJwt()).GET().build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, HttpResponse.BodyHandlers.ofString());

        if(response.get().statusCode() == 500){
            msg.show(Alert.AlertType.ERROR, "Error", "Fecha mal ingresada");
        }
        else if(response.get().statusCode() == 403){
            msg.show(Alert.AlertType.ERROR, "Error", "Se requiere un permiso adicional para realizar esta acción");
        }
        else
        {
            datosGeneracionCobros = mapper.readValue(response.get().body(), new TypeReference<List<CobroGeneradoDTO>>() {});

        }
        return datosGeneracionCobros;
    }

    public static CobroGeneradoDTO getFechaCobroRutaBus(String fechaInicio, String fechaFinal) throws InterruptedException, ExecutionException, IOException {
        CobroGeneradoDTO fechaCobroRutaBus = null;
        AuthenticationResponse token = (AuthenticationResponse) AppContext.getInstance().get("Rol");

        HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL+"/findByCobroRutaBus/"+fechaInicio+"/"+fechaFinal))
                .setHeader("Content-Type", "application/json").setHeader("AUTHORIZATION", "Bearer " + token.getJwt()).GET().build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, HttpResponse.BodyHandlers.ofString());

        if(response.get().statusCode() == 500){

        }
        else if(response.get().statusCode() == 403){
            msg.show(Alert.AlertType.ERROR, "Error", "Se requiere un permiso adicional para realizar esta acción");
        }
        else {
            fechaCobroRutaBus = mapper.readValue(response.get().body(), new TypeReference<CobroGeneradoDTO>() {});
            TransaccionDTO transaccionDTO = new TransaccionDTO();
            transaccionDTO.setAccion("Se obtuvo un cobro generado por medio de una fecha");
            transaccionDTO.setObjeto("Fecha: "+fechaInicio+"/"+fechaFinal);
            transaccionDTO.setUsuario(token.getUsuarioDTO());
            TransaccionService.createTransaccion(transaccionDTO);
        }
        return fechaCobroRutaBus;
    }

    public static CobroGeneradoDTO getFechaCobroLimpieza(String fechaInicio, String fechaFinal) throws InterruptedException, ExecutionException, IOException {
        CobroGeneradoDTO fechaCobroLimpieza = null;
        AuthenticationResponse token = (AuthenticationResponse) AppContext.getInstance().get("Rol");

        HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL+"/findByCobroLimpiezaParque/"+fechaInicio+"/"+fechaFinal))
                .setHeader("Content-Type", "application/json").setHeader("AUTHORIZATION", "Bearer " + token.getJwt()).GET().build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, HttpResponse.BodyHandlers.ofString());

        if(response.get().statusCode() == 500){

        }
        else if(response.get().statusCode() == 403){
            msg.show(Alert.AlertType.ERROR, "Error", "Se requiere un permiso adicional para realizar esta acción");
        }
        else {
            fechaCobroLimpieza = mapper.readValue(response.get().body(), new TypeReference<CobroGeneradoDTO>() {});
            TransaccionDTO transaccionDTO = new TransaccionDTO();
            transaccionDTO.setAccion("Se obtuvo un cobro generado por medio de una fecha");
            transaccionDTO.setObjeto("Fecha: "+fechaInicio+"/"+fechaFinal);
            transaccionDTO.setUsuario(token.getUsuarioDTO());
            TransaccionService.createTransaccion(transaccionDTO);
        }
        return fechaCobroLimpieza;
    }

    public static CobroGeneradoDTO getFechaCobroCementerio(String fechaInicio, String fechaFinal) throws InterruptedException, ExecutionException, IOException {
        CobroGeneradoDTO fechaCobroCementerio = null;
        AuthenticationResponse token = (AuthenticationResponse) AppContext.getInstance().get("Rol");

        HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL+"/findByCobroCementerio/"+fechaInicio+"/"+fechaFinal))
                .setHeader("Content-Type", "application/json").setHeader("AUTHORIZATION", "Bearer " + token.getJwt()).GET().build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, HttpResponse.BodyHandlers.ofString());

        if(response.get().statusCode() == 500){

        }
        else if(response.get().statusCode() == 403){
            msg.show(Alert.AlertType.ERROR, "Error", "Se requiere un permiso adicional para realizar esta acción");
        }
        else {
            fechaCobroCementerio = mapper.readValue(response.get().body(), new TypeReference<CobroGeneradoDTO>() {});
            TransaccionDTO transaccionDTO = new TransaccionDTO();
            transaccionDTO.setAccion("Se obtuvo un cobro generado por medio de una fecha");
            transaccionDTO.setObjeto("Fecha: "+fechaInicio+"/"+fechaFinal);
            transaccionDTO.setUsuario(token.getUsuarioDTO());
            TransaccionService.createTransaccion(transaccionDTO);
        }
        return fechaCobroCementerio;
    }
}
