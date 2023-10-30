package co.edu.uniquindio.uniclinica.dto.paciente;

import co.edu.uniquindio.uniclinica.modelo.entidades.Cuenta;
import co.edu.uniquindio.uniclinica.modelo.entidades.Pqrs;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record MensajeDTO(

        @NotNull
        int id,
        @NotNull
        LocalDateTime fechaCreacion,
        @NotEmpty
        String contenido,
        @NotNull
        Cuenta cuenta,
        @NotNull
        Pqrs pqrs
) {
}
