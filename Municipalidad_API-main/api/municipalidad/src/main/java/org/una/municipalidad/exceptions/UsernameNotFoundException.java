package org.una.municipalidad.exceptions;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString
@Component

public class UsernameNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final HttpStatus notFound = HttpStatus.NOT_FOUND;

    private final String errorMessage = "No se encontro el usuario para esta peticion";


}