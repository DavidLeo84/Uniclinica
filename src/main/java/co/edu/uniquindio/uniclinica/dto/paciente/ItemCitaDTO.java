package co.edu.uniquindio.uniclinica.dto.paciente;

import co.edu.uniquindio.uniclinica.modelo.enums.Especialidad;
import co.edu.uniquindio.uniclinica.modelo.enums.EstadoCita;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ItemCitaDTO(

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
