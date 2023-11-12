package co.edu.uniquindio.uniclinica.dto.paciente;

import co.edu.uniquindio.uniclinica.modelo.entidades.Cita;
import co.edu.uniquindio.uniclinica.modelo.enums.Especialidad;

import java.time.LocalDateTime;

public record DetalleAtencionDTO(
        int codigo,
        int codigoCita,
        String nombrePaciente,
        String nombreMedico,
        Especialidad especialidad,
        LocalDateTime fechaAtencion,
        String tratamiento,
        String notasMedicas,
        String diagnostico
) {
    public DetalleAtencionDTO(Cita cita){
        this(
                cita.getAtencion().getCodigo(),
                cita.getCodigo(),
                cita.getPaciente().getNombre(),
                cita.getMedico().getNombre(),
                cita.getMedico().getEspecialidad(),
                cita.getFechaCita(),
                cita.getAtencion().getTratamiento(),
                cita.getAtencion().getNotasMedicas(),
                cita.getAtencion().getDiagnostico()
        );
    }
}
