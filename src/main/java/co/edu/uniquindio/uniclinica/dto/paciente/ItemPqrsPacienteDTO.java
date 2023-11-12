package co.edu.uniquindio.uniclinica.dto.paciente;

import co.edu.uniquindio.uniclinica.modelo.enums.EstadoPqrs;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ItemPqrsPacienteDTO(
        @NotEmpty
        int codigo,
        @NotNull
        EstadoPqrs estado,
        @NotNull
        String motivo,
        @NotNull
        LocalDateTime fecha
) {
}
