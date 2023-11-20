package co.edu.uniquindio.uniclinica.dto.paciente;

import co.edu.uniquindio.uniclinica.modelo.entidades.Mensaje;
import co.edu.uniquindio.uniclinica.modelo.enums.TipoMensaje;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record RegistroRespuestaPacienteDTO(
        int codigoPqrs,
        @Positive
        int codigoCuenta,
        @NotNull
        TipoMensaje tipoMensaje,
        @Positive
        LocalDateTime fechaRespuesta,
        @NotBlank
        String mensaje
) {

        public RegistroRespuestaPacienteDTO(Mensaje m){

                this(
                        m.getCodigo(),
                        m.getCuenta().getId(),
                        m.getTipoMensaje(),
                        m.getFechaMensaje(),
                        m.getContenido()
                );
        }

}