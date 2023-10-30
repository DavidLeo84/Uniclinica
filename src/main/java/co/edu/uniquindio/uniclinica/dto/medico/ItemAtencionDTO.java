package co.edu.uniquindio.uniclinica.dto.medico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ItemAtencionDTO(

        @NotNull
        int codigo,
        @NotBlank
        String diagnostico,
        String tratamiento,
        String notasMedicas,
        @NotNull
        int codigoCita
) {
}
