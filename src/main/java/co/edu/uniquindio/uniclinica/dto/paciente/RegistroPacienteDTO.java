package co.edu.uniquindio.uniclinica.dto.paciente;

import co.edu.uniquindio.uniclinica.modelo.enums.Ciudad;
import co.edu.uniquindio.uniclinica.modelo.enums.Eps;
import co.edu.uniquindio.uniclinica.modelo.enums.TipoSangre;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record RegistroPacienteDTO(

        @NotBlank
        @Length(max = 10, message = "La cédula debe tener máximo 10 caracteres")
        String cedula,
        @NotBlank
        @Length(max = 200, message = "El nombre debe tener máximo 200 caracteres")
        String nombre,
        @NotBlank
        @Length(max = 20, message = "El teléfono debe tener máximo 20 caracteres")
        String telefono,
        @NotBlank String urlFoto,
        @NotNull
        Ciudad ciudad,
        @NotNull
        @Past(message = "Seleccione una fecha de nacimiento correcta")
        LocalDate fechaNacimiento,
        //@NotEmpty List<Alergia> alergias, //para guardar en una bd si hablo de una lista de alergias debo crear una E Alergias, no puede ser una lista de enums si no una relación de uno a muchos
        @NotBlank
        String alergias,
        @NotNull
        Eps eps,
        @NotNull
        TipoSangre tipoSangre,

        @NotBlank
        @Length(max = 80, message = "El correo debe tener máximo 80 caracteres")
        @Email(message = "Ingrese una dirección de correo electrónico válida")
        String correo,
        @NotBlank
        String password
) {
}
