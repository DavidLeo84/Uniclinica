package co.edu.uniquindio.uniclinica.dto.administrador;

import co.edu.uniquindio.uniclinica.dto.RespuestaDTO;
import co.edu.uniquindio.uniclinica.modelo.enums.EstadoPqrs;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record DetallePqrsDTO(

        @NotEmpty
        int codigoPqrs,
        @NotNull
        LocalDateTime fechaCreacion,
        @Lob
        String motivo,
        @NotNull
        int codigoCita,
        @NotNull
        String nombrePaciente,
        @NotNull
        EstadoPqrs estado,
        List<RespuestaDTO> mensajes
) {
}
