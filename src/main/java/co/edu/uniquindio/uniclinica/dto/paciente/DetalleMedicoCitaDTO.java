package co.edu.uniquindio.uniclinica.dto.paciente;

import co.edu.uniquindio.uniclinica.dto.administrador.HorarioDTO;
import co.edu.uniquindio.uniclinica.modelo.enums.Especialidad;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.List;

public record DetalleMedicoCitaDTO(

        @Positive
        int codigo,
        @NotEmpty
        @Length(max = 200)
        String nombre,
        @NotNull
        Especialidad especialidad,
        @NotEmpty
        String urlFoto,
        @NotEmpty
        LocalDateTime diaLibre,
        @NotEmpty
        List<HorarioDTO> horarios
) {
}
