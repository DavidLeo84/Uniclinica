package co.edu.uniquindio.uniclinica.dto.paciente;

import co.edu.uniquindio.uniclinica.modelo.entidades.Cita;
import co.edu.uniquindio.uniclinica.modelo.enums.Especialidad;
import co.edu.uniquindio.uniclinica.modelo.enums.EstadoCita;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ItemCitaDTO(

        int codigo,
        String cedulaPaciente,
        String nombrePaciente,
        String nombreMedico,
        Especialidad especialidad,
        EstadoCita estadoCita,
        LocalDateTime fecha
) {
        public ItemCitaDTO(Cita cita){
                this(
                        cita.getCodigo(),
                        cita.getPaciente().getCedula(),
                        cita.getPaciente().getNombre(),
                        cita.getMedico().getNombre(),
                        cita.getMedico().getEspecialidad(),
                        cita.getEstadoCita(),
                        cita.getFechaCita()
                );
        }
}
