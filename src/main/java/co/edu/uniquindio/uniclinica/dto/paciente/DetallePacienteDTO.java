package co.edu.uniquindio.uniclinica.dto.paciente;

import co.edu.uniquindio.uniclinica.dto.administrador.HorarioDTO;
import co.edu.uniquindio.uniclinica.modelo.entidades.Medico;
import co.edu.uniquindio.uniclinica.modelo.entidades.Paciente;
import co.edu.uniquindio.uniclinica.modelo.enums.Ciudad;
import co.edu.uniquindio.uniclinica.modelo.enums.Eps;
import co.edu.uniquindio.uniclinica.modelo.enums.TipoSangre;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record DetallePacienteDTO(

        @NotNull
        int codigoPaciente,
        @Length(max = 10, message = "La cédula debe tener máximo 10 caracteres")
        String cedula,
        @NotNull
        Ciudad ciudad,
        @NotBlank
        @Length(max = 200, message = "El nombre debe tener máximo 200 caracteres")
        String nombre,
        @NotBlank
        @Length(max = 20, message = "El teléfono debe tener máximo 20 caracteres")
        String telefono,
        @NotBlank
        @Length(max = 80, message = "El correo debe tener máximo 80 caracteres")
        @Email(message = "Ingrese una dirección de correo electrónico válida")
        String correo,
        @NotNull
        TipoSangre tipoSangre,
        @NotBlank
        String urlFoto,
        @NotNull
        @Past(message = "Seleccione una fecha de nacimiento correcta")
        LocalDate fechaNacimiento,
        @NotBlank
        String alergias,
        @NotNull
        Eps eps



) {
        public DetallePacienteDTO(Paciente paciente){
        this(
                paciente.getId(),
                paciente.getCedula(),
                paciente.getCiudad(),
                paciente.getNombre(),
                paciente.getTelefono(),
                paciente.getCorreo(),
                paciente.getTipoSangre(),
                paciente.getUrlFoto(),
                paciente.getFechaNacimiento(),
                paciente.getAlergias(),
                paciente.getEps()

        );


}

}
