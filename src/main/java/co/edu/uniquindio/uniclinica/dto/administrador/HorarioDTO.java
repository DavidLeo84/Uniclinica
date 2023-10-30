package co.edu.uniquindio.uniclinica.dto.administrador;

import co.edu.uniquindio.uniclinica.modelo.entidades.HorarioMedico;
import co.edu.uniquindio.uniclinica.modelo.entidades.Medico;

import java.time.LocalDateTime;

public record HorarioDTO(

        String dia,
        LocalDateTime horaInicio,
        LocalDateTime horaSalida,
        Medico medico
) {
/*
    public HorarioDTO(HorarioMedico h){
        this(
                h.getDia(),
                h.getHoraInicio(),
                h.getHoraSalida(),
                h.getMedico()
        );
    }

 */
}
