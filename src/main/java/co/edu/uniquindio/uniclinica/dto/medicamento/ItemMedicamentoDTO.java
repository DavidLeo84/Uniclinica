package co.edu.uniquindio.uniclinica.dto.medicamento;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ItemMedicamentoDTO(

        @NotNull
        String nombre,
        @Positive
        int unidades,
        @Positive
        float precio
) {
}
