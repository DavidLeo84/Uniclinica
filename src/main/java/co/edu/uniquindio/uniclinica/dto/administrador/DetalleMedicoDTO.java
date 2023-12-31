package co.edu.uniquindio.uniclinica.dto.administrador;

import co.edu.uniquindio.uniclinica.modelo.entidades.DiaLibreMedico;
import co.edu.uniquindio.uniclinica.modelo.entidades.HorarioMedico;
import co.edu.uniquindio.uniclinica.modelo.enums.Ciudad;
import co.edu.uniquindio.uniclinica.modelo.entidades.Medico;
import co.edu.uniquindio.uniclinica.modelo.enums.Especialidad;
import co.edu.uniquindio.uniclinica.modelo.enums.TipoSangre;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public record DetalleMedicoDTO(

        @NotNull
        int codigo,
        @NotEmpty
        @Length(max = 200)
        String nombre,
        @NotNull

        @Length(max = 10)
        String cedula,
        @NotNull
        Ciudad ciudad,
        @NotNull
        Especialidad especialidad,
        @NotNull
        TipoSangre tipoSangre,
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
        List<HorarioDTO> horarios
        //List<DiaLibreMedico> diaLibre
) {

        public DetalleMedicoDTO(Medico medico, List<HorarioDTO> horarios){
                this(
                        medico.getId(),
                        medico.getNombre(),
                        medico.getCedula(),
                        medico.getCiudad(),
                        medico.getEspecialidad(),
                        medico.getTipoSangre(),
                        medico.getTelefono(),
                        medico.getCorreo(),
                        medico.getUrlFoto(),
                        horarios
                );


        }


}
