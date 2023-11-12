package co.edu.uniquindio.uniclinica.dto.seguridad;

import jakarta.validation.constraints.NotBlank;

public record TokenDTO(
        @NotBlank
        String token
) {
}
