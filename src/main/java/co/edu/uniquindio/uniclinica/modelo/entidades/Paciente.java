package co.edu.uniquindio.uniclinica.modelo.entidades;

import co.edu.uniquindio.uniclinica.dto.paciente.DetallePacienteDTO;
import co.edu.uniquindio.uniclinica.dto.paciente.RegistroPacienteDTO;
import co.edu.uniquindio.uniclinica.modelo.enums.Eps;
import co.edu.uniquindio.uniclinica.modelo.enums.TipoSangre;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
//@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Paciente extends Usuario implements Serializable {

    @Column(nullable = false)
    private LocalDate FechaNacimiento;

    @Column
    private String alergias;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private Eps eps;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private TipoSangre tipoSangre;

    @OneToMany(mappedBy = "paciente")
    private List<Cita> citas;

    @OneToMany(mappedBy = "paciente")
    private List<Transaccion> transacciones;

    /*
    public Paciente(RegistroPacienteDTO pacienteDTO, String password) {
        //datos de la cuenta
        this.setCorreo(pacienteDTO.correo());
        this.setPassword(password);

        //datos del usuario
        this.setNombre(pacienteDTO.nombre());
        this.setCedula(pacienteDTO.cedula());
        this.setTelefono(pacienteDTO.telefono());
        this.setCiudad(pacienteDTO.ciudad());
        this.setUrlFoto(pacienteDTO.urlFoto());

        //datos del paciente
        this.setFechaNacimiento(pacienteDTO.fechaNacimiento());
        this.setEps(pacienteDTO.eps());
        this.setAlergias(pacienteDTO.alergias());
        this.setTipoSangre(pacienteDTO.tipoSangre());
    }
    public void actualizar(DetallePacienteDTO datos) {
        //Datos de la Cuenta
        this.setCorreo( datos.correo() );
        //Datos del Usuario
        this.setNombre( datos.nombre() );
        this.setCedula( datos.cedula() );
        this.setTelefono( datos.telefono() );
        this.setCiudad( datos.ciudad() );
        this.setUrlFoto( datos.urlFoto() );
        //Datos del Paciente
        this.setFechaNacimiento( datos.fechaNacimiento() );
        this.setEps( datos.eps() );
        this.setAlergias( datos.alergias() );
        this.setTipoSangre( datos.tipoSangre() );
    }

     */
}
