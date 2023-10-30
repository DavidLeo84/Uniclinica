package co.edu.uniquindio.uniclinica.dto.administrador;

import co.edu.uniquindio.uniclinica.modelo.enums.Especialidad;
import co.edu.uniquindio.uniclinica.modelo.enums.EstadoCita;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ItemCitaAdminDTO(
        @NotEmpty
        int codigoCita,
        @NotNull
        String cedulaPaciente,
        @NotNull
        String nombrePaciente,
        @NotNull
        String nombreMedico,
        @NotNull
        Especialidad especialidad,
        @NotNull
        EstadoCita estadoCita,
        @NotNull
        LocalDateTime fecha
) {
}
