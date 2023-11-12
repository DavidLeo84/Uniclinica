package co.edu.uniquindio.uniclinica.modelo.entidades;

import co.edu.uniquindio.uniclinica.dto.administrador.DetalleMedicoDTO;
import co.edu.uniquindio.uniclinica.dto.administrador.RegistroMedicoDTO;
import co.edu.uniquindio.uniclinica.modelo.enums.Especialidad;
import co.edu.uniquindio.uniclinica.modelo.enums.EstadoUsuario;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
//@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Medico extends Usuario implements Serializable {

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private Especialidad especialidad;

    @Column(name = "horario_medico")
    @OneToMany(mappedBy = "medico")
    private List<HorarioMedico> horarioMedico;

    @OneToMany(mappedBy = "medico")
    private List<Cita> citas;

    @OneToMany(mappedBy = "medico")
    private List<DiaLibreMedico> diaLibre;

    @OneToMany(mappedBy = "medico")
    private List<Transaccion> transacciones;


    public Medico(RegistroMedicoDTO medicoDTO, String password){
        this.setCedula(medicoDTO.cedula());
        this.setTelefono(medicoDTO.telefono());
        this.setNombre(medicoDTO.nombre());
        this.setEspecialidad(medicoDTO.especialidad());
        this.setCiudad(medicoDTO.ciudad());
        this.setCorreo(medicoDTO.correo());
        this.setPassword(password);
        this.setUrlFoto(medicoDTO.urlFoto());
        this.setEstado(EstadoUsuario.ACTIVO);
    }

    public void actualizar(DetalleMedicoDTO medicoDTO){
        this.setCedula(medicoDTO.cedula());
        this.setTelefono(medicoDTO.telefono());
        this.setNombre(medicoDTO.nombre());
        this.setEspecialidad(medicoDTO.especialidad());
        this.setCiudad(medicoDTO.ciudad());
        this.setCorreo(medicoDTO.correo());
        this.setUrlFoto(medicoDTO.urlFoto());
    }


}
