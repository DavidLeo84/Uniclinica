package co.edu.uniquindio.uniclinica.dto.paciente;
import co.edu.uniquindio.uniclinica.modelo.enums.Especialidad;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AgendarCitaDTO(
        @NotNull
        int idPaciente,
        int idMedico,
        LocalDateTime fechaCreacion,
        @NotNull
        @Future
        LocalDateTime fecha,
        String motivo,
        Especialidad especialidad
) {
}
