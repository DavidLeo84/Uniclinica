package co.edu.uniquindio.uniclinica.dto.paciente;

import co.edu.uniquindio.uniclinica.modelo.entidades.Cuenta;
import co.edu.uniquindio.uniclinica.modelo.entidades.Mensaje;
import co.edu.uniquindio.uniclinica.modelo.entidades.Pqrs;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record MensajeDTO(

        @NotNull
        int id,
        @NotNull
        LocalDateTime fechaCreacion,
        @NotEmpty
        String contenido,
        @NotNull
        int cuenta,
        @NotNull
        int pqrs
) {
        public MensajeDTO (Mensaje mensaje) {

                this(
                        mensaje.getCodigo(),
                        mensaje.getFechaMensaje(),
                        mensaje.getContenido(),
                        mensaje.getCuenta().getId(),
                        mensaje.getPqrs().getCodigo()


                        );
        }
}
