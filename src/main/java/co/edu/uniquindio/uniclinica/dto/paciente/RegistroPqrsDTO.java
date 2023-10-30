package co.edu.uniquindio.uniclinica.dto.paciente;

import co.edu.uniquindio.uniclinica.dto.RespuestaDTO;
import co.edu.uniquindio.uniclinica.modelo.enums.EstadoPqrs;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record RegistroPqrsDTO(

        @NotNull
        int codigoPqrs,
        @NotNull
        int codigoCita,
        @Lob
        String motivo,
        @NotNull
        //int codigoPaciente, // cambiar a nombre del paciente
        String nombrePaciente,
        @NotNull
        EstadoPqrs estadoPqrs,
        LocalDateTime fechaCreacion,
        List<RespuestaDTO> mensajes
) {

}
