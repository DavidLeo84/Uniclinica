package co.edu.uniquindio.uniclinica.dto.paciente;

import co.edu.uniquindio.uniclinica.dto.RespuestaDTO;
import co.edu.uniquindio.uniclinica.modelo.entidades.Cita;
import co.edu.uniquindio.uniclinica.modelo.entidades.Mensaje;
import co.edu.uniquindio.uniclinica.modelo.enums.EstadoPqrs;
import co.edu.uniquindio.uniclinica.modelo.enums.TipoPqrs;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record RegistroPqrsDTO(

        //@NotNull
        //int codigoPqrs,
        LocalDateTime fechaCreacion,
        @NotNull
        TipoPqrs tipoPqrs,
        @Lob
        String contenido,
        @NotNull
        int codigoCita,
        @NotNull
        int codigoPaciente,
        String motivo,
        EstadoPqrs estadoPqrs

) {
        public RegistroPqrsDTO(Mensaje m, Cita c){
                this(

                        m.getFechaMensaje(),
                        m.getPqrs().getTipoPqrs(),
                        m.getContenido(),
                        c.getCodigo(),
                        m.getCuenta().getId(),
                        m.getPqrs().getMotivo(),
                        m.getPqrs().getEstadoPqrs()

                        );
        }



}
