package co.edu.uniquindio.uniclinica.dto.medicamento;

import co.edu.uniquindio.uniclinica.modelo.entidades.Medicamento;
import co.edu.uniquindio.uniclinica.modelo.entidades.TipoMedicamento;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record RegistroMedicamentoDTO(

        @NotNull
        int codigo,
        @NotNull
        TipoMedicamento tipo,
        @NotNull
        String nombre,
        @Positive
        int unidades,
        @Positive
        float precio
) {

}
