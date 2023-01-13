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
public class PasswordIsBlankException extends RuntimeException{

    private static final long serialVersionUID = 1L;


    private final HttpStatus errorCode= HttpStatus.NOT_IMPLEMENTED;

    private final  String errorMessage= "Esta contraseña posee ";
//=======
//>>>>>>> 9fac373797791a19650efca6cac174b5cd262430

   // private final HttpStatus errorCode = HttpStatus.NOT_FOUND;

   // private final String errorMessage = "No se encontro información en su solicitud, revise su petición";
}
