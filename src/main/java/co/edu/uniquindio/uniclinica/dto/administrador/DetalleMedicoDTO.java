package co.edu.uniquindio.uniclinica.dto.administrador;

import co.edu.uniquindio.uniclinica.modelo.entidades.DiaLibreMedico;
import co.edu.uniquindio.uniclinica.modelo.entidades.HorarioMedico;
import co.edu.uniquindio.uniclinica.modelo.enums.Ciudad;
import co.edu.uniquindio.uniclinica.modelo.entidades.Medico;
import co.edu.uniquindio.uniclinica.modelo.enums.Especialidad;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.List;

public record DetalleMedicoDTO(

        @Positive
        int codigo,
        @NotEmpty
        @Length(max = 200)
        String nombre,
        @NotEmpty
        @Length(max = 10)
        String cedula,
        @NotNull
        Ciudad ciudad,
        @NotNull
        Especialidad especialidad,
        @NotEmpty
        @Length(max = 20)
        String telefono,
        @NotEmpty
        @Email
        @Length(max = 80)
        String correo,
        @NotEmpty
        String urlFoto,
        @NotEmpty
        List<HorarioMedico> horarios,
        List<DiaLibreMedico> diaLibre
) {
        /*
        public DetalleMedicoDTO(Medico medico, List<HorarioDTO> horarios){
                this(
                        medico.getId(),
                        medico.getNombre(),
                        medico.getCedula(),
                        medico.getCiudad(),
                        medico.getEspecialidad(),
                        medico.getTelefono(),
                        medico.getCorreo(),
                        medico.getUrlFoto(),
                        medico.getHorarioMedico(),
                        medico.getDiaLibre()
                );
        }

         */
}
