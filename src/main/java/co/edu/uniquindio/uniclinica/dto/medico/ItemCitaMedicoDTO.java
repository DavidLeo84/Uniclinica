package co.edu.uniquindio.uniclinica.dto.medico;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ItemCitaMedicoDTO(

        @NotEmpty
        int codigo,
        @NotNull
        String cedulaPaciente,
        @NotNull
        String nombrePaciente,
        @NotNull
        String motivo,
        @NotNull
        LocalDateTime fecha
) {
}
