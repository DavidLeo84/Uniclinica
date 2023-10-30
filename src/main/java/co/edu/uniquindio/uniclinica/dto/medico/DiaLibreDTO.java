package co.edu.uniquindio.uniclinica.dto.medico;

import co.edu.uniquindio.uniclinica.modelo.entidades.DiaLibreMedico;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record DiaLibreDTO(

        int codigo,
        int codigoMedico,
        LocalDateTime fecha
) {
/*
    public DiaLibreDTO(DiaLibreMedico diaLibre) {
        this(
                diaLibre.getCodigo(),
                diaLibre.getMedico().getId(),
                diaLibre.getFechaLibre()
        );
    }

 */
}
