package co.edu.uniquindio.uniclinica.dto.seguridad;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginDTO(

        @NotBlank
        @Email
        String user,
        @NotBlank
        String password
) {
}
