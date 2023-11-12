package co.edu.uniquindio.uniclinica.dto.administrador;

import co.edu.uniquindio.uniclinica.dto.RespuestaDTO;
import co.edu.uniquindio.uniclinica.modelo.entidades.Pqrs;
import co.edu.uniquindio.uniclinica.modelo.enums.Especialidad;
import co.edu.uniquindio.uniclinica.modelo.enums.EstadoPqrs;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record DetallePqrsDTO(

        int codigo,
        EstadoPqrs estadoPqrs,
        String motivo,
        String nombrePaciente,
        String nombreMedico,
        Especialidad especialidad,
        LocalDateTime fecha,
        List<RespuestaDTO> mensajes
) {
        public DetallePqrsDTO(Pqrs pqrs, List<RespuestaDTO> mensajes) {
                this(
                        pqrs.getCodigo(),
                        pqrs.getEstadoPqrs(),
                        pqrs.getMotivo(),
                        pqrs.getCita().getPaciente().getNombre(),
                        pqrs.getCita().getMedico().getNombre(),
                        pqrs.getCita().getMedico().getEspecialidad(),
                        pqrs.getFechaCreacion(),
                        mensajes
                );
        }

}
