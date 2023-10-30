package co.edu.uniquindio.uniclinica.dto.paciente;

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

public record DetallePacienteDTO(

        @NotNull
        int codigo,
        @NotBlank
        @Length(max = 10, message = "La cédula debe tener máximo 10 caracteres")
        String cedula,
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
        @Past(message = "Seleccione una fecha de nacimiento correcta")
        LocalDate fechaNacimiento,
        @NotBlank
        String urlFoto,
        @NotNull
        Ciudad ciudad,
        @NotNull
        Eps eps,
        @NotNull
        TipoSangre tipoSangre,
        @NotBlank
        String alergias
) {
}
