package co.edu.uniquindio.uniclinica.dto.paciente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record RegistroRespuestaPacienteDTO(
        int codigoPqrs,
        @Positive
        int codigoCuenta,
        @Positive
        LocalDateTime fechaRespuesta,
        @NotBlank
        String mensaje
) {

}