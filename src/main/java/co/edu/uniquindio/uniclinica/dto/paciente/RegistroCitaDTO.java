package co.edu.uniquindio.uniclinica.dto.paciente;

import co.edu.uniquindio.uniclinica.modelo.enums.Especialidad;
import co.edu.uniquindio.uniclinica.modelo.enums.EstadoCita;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;

public record RegistroCitaDTO(

        @NotEmpty
        LocalDateTime fechaCreacion,
        @NotEmpty
        LocalDateTime fechaCita,
        @NotEmpty
        String motivo,
        @NotEmpty
        String cedulaPaciente,
        @NotEmpty
        String nombrePaciente,
        @NotEmpty
        String nombreMedico,
        @NotEmpty
        Especialidad especialidad,
        @NotEmpty
        EstadoCita estadoCita
) {
}
