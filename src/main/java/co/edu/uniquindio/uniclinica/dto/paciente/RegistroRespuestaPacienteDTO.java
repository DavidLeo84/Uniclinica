package co.edu.uniquindio.uniclinica.dto.paciente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record RegistroRespuestaPacienteDTO(

        @Positive
        int codigoCuenta,
        @Positive
        int codigoPqrs,
        @Positive
        int codigoMensaje,
        @NotBlank
        String mensaje
) {
}
