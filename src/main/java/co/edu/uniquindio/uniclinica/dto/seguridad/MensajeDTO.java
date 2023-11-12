package co.edu.uniquindio.uniclinica.dto.seguridad;

public record MensajeDTO<T>(
        boolean error,
        T respuesta

) {
}
