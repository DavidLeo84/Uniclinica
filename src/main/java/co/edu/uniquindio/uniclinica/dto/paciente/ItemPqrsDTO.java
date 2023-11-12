package co.edu.uniquindio.uniclinica.dto.paciente;

import co.edu.uniquindio.uniclinica.modelo.entidades.Pqrs;
import co.edu.uniquindio.uniclinica.modelo.enums.EstadoPqrs;

import java.time.LocalDateTime;

public record ItemPqrsDTO(
        int codigo,
        EstadoPqrs estadoPqrs,
        LocalDateTime fechaCreacion,
        String nombrePaciente
) {

    public ItemPqrsDTO(Pqrs p) {
        this(
                p.getCodigo(),
                p.getEstadoPqrs(),
                p.getFechaCreacion(),
                p.getCita().getPaciente().getNombre()
        );
    }
}
