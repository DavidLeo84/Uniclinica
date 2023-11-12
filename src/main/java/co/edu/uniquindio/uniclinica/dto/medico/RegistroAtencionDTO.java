package co.edu.uniquindio.uniclinica.dto.medico;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record RegistroAtencionDTO(

        @NotNull
        int codigoCita,
        @Lob
        String diagnostico,
        @Lob
        String tratamiento,
        @Lob
        String notasMedicas,

        @Positive
        float precio
) {
}
