package co.edu.uniquindio.uniclinica.dto.medico;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record RegistroAtencionDTO(

        @NotNull
        int codigoCita,
        @NotNull
        int codigoMedico,
        @Lob
        String notasMedicas,
        @Lob
        String tratamiento,
        @Lob
        String diagnostico,
        @Positive
        float precio
) {
}
