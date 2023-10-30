package co.edu.uniquindio.uniclinica.dto.paciente;

import co.edu.uniquindio.uniclinica.modelo.enums.Especialidad;
import co.edu.uniquindio.uniclinica.modelo.enums.EstadoCita;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DetalleCitaDTO(
        @NotEmpty
        @NotNull
        @NotBlank
        int codigo,
        @NotEmpty
        LocalDateTime fechaCita,
        @NotEmpty
        String motivo,
        @NotNull
        EstadoCita EstadoCita,
        @NotEmpty
        @NotNull
        String nombreMedico,
        @NotNull
        Especialidad especialidad
) {
}
