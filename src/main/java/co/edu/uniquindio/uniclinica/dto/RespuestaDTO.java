package co.edu.uniquindio.uniclinica.dto;

import co.edu.uniquindio.uniclinica.modelo.entidades.Mensaje;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record RespuestaDTO(

        @NotNull
        int codigoMensaje,
        @Lob
        String contenido,
        String nombreUsuario,
        LocalDateTime fecha
) {
    public RespuestaDTO(Mensaje mensaje){
        this(
                mensaje.getCodigo(),
                mensaje.getContenido(),
                mensaje.getCuenta().getCorreo(),
                mensaje.getFechaMensaje()

        );
    }
}
