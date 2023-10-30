package co.edu.uniquindio.uniclinica.dto.administrador;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record RegistroRespuestaDTO(

        @Positive
        int codigoCuenta,
        @Positive
        int codigoPQRS,
        @Positive
        int codigoMensaje,
        @NotBlank
        String mensaje
) {
}
