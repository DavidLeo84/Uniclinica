package co.edu.uniquindio.uniclinica.dto.paciente;

import co.edu.uniquindio.uniclinica.modelo.entidades.Paciente;
import co.edu.uniquindio.uniclinica.modelo.enums.Ciudad;
import co.edu.uniquindio.uniclinica.modelo.enums.Eps;
import co.edu.uniquindio.uniclinica.modelo.enums.TipoSangre;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

public record PacienteRespuestaDTO(

        @NotNull
        int codigo,
        String cedula,
        String nombre,
        String telefono,
        @Email()
        String correo,
        @Past
        LocalDateTime fechaNacimiento,
        @Lob
        String urlFoto,
        //las enumeraciones se pueden tomar literalmente o se puede hacer uso de
        //int, ya que cada valor que tiene una enumeración tiene un índice (número entero).
        Ciudad ciudad,
        Eps eps,
        TipoSangre tipoSangre,
        String alergias
) {

    /*
    public PacienteRespuestaDTO(Paciente paciente){
        this(
                paciente.getId(),
                paciente.getCedula(),
                paciente.getNombre(),
                paciente.getTelefono(),
                paciente.getCorreo(),
                paciente.getFechaNacimiento(),
                paciente.getUrlFoto(),
                paciente.getCiudad(),//accediendo al índice de la instancia del enum, ;apea ciudad a su índice
                paciente.getEps(),
                paciente.getTipoSangre(),
                paciente.getAlergias()
        );
    }

     */
}
