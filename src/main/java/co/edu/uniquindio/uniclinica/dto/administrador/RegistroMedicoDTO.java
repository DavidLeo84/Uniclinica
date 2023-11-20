package co.edu.uniquindio.uniclinica.dto.administrador;

import co.edu.uniquindio.uniclinica.modelo.entidades.Medico;
import co.edu.uniquindio.uniclinica.modelo.enums.Ciudad;
import co.edu.uniquindio.uniclinica.modelo.enums.Especialidad;
import co.edu.uniquindio.uniclinica.modelo.enums.TipoSangre;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.List;

public record RegistroMedicoDTO(

        @NotBlank
        @Length(max = 200)
        String nombre,

        @NotNull

        String cedula,
        @NotNull
        Ciudad ciudad,
        @NotNull
        Especialidad especialidad,
        @NotNull
        TipoSangre tipoSangre,
        @NotBlank
        @Length(max = 20)
        String telefono,
        @NotBlank
        @Email
        @Length(max = 80)
        String correo,
        @NotBlank
        String password,
        @NotBlank
        String urlFoto,
        @NotEmpty
        List<HorarioDTO> horarios
) {

        public RegistroMedicoDTO(Medico medico, List<HorarioDTO>horarios) {
                this(
                        medico.getNombre(),
                        medico.getCedula(),
                        medico.getCiudad(),
                        medico.getEspecialidad(),
                        medico.getTipoSangre(),
                        medico.getTelefono(),
                        medico.getCorreo(),
                        medico.getPassword(),
                        medico.getUrlFoto(),
                        horarios
                        );

        }
}
