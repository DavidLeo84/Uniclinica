package co.edu.uniquindio.uniclinica.dto.paciente;

import co.edu.uniquindio.uniclinica.dto.RespuestaDTO;
import co.edu.uniquindio.uniclinica.modelo.entidades.Mensaje;
import co.edu.uniquindio.uniclinica.modelo.enums.EstadoPqrs;
import co.edu.uniquindio.uniclinica.modelo.enums.TipoPqrs;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record RegistroPqrsDTO(

        @NotNull
        int codigoPqrs,
        LocalDateTime fechaCreacion,
        @NotNull
        TipoPqrs tipoPqrs,
        @Lob
        String contenido,
        @NotNull
        int codigoCita,
        @NotNull
        int codigoPaciente,
        String motivo,
        EstadoPqrs estadoPqrs

) {
     /*   public RegistroPqrsDTO(Mensaje m){
                this(
                        m.getPqrs().getCodigo(),
                        m.getFechaMensaje(),
                        m.getContenido(),
                        m.getCuenta().getId(),
                        m.getCuenta().getId(),
                        m.getPqrs().getMotivo(),
                        m.getPqrs().getEstadoPqrs()

                        );
        }

      */

}
