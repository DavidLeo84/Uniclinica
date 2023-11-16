package co.edu.uniquindio.uniclinica.servicios.validaciones;

import co.edu.uniquindio.uniclinica.dto.administrador.DetalleMedicoDTO;
import co.edu.uniquindio.uniclinica.dto.paciente.RegistroPacienteDTO;
import co.edu.uniquindio.uniclinica.dto.administrador.RegistroMedicoDTO;
import co.edu.uniquindio.uniclinica.modelo.entidades.*;
import co.edu.uniquindio.uniclinica.repositorios.*;
import co.edu.uniquindio.uniclinica.servicios.validaciones.excepciones.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class Validacion {

    private  final MedicoRepo medicoRepo;
    private  final PacienteRepo pacienteRepo;
    private final CitaRepo citaRepo;
    private final AtencionRepo atencionRepo;
    private final PqrsRepo pqrsRepo;
    private final CuentaRepo cuentaRepo;
    private final DiaLibreRepo diaLibreRepo;
    private final HorarioRepo horarioRepo;
    private final MensajeRepo mensajeRepo;


    public Medico existenciaMedico(int id) {

        Medico medico = medicoRepo.findById(id).orElse(null);

        if (medico == null)
            throw new ResourceNotFoundException("El medico con " + id + " no se encuentra registrado");

        return medico;
    }

    public void existenciaPaciente(int id, String cedula) {

        Paciente paciente = pacienteRepo.findById(id).orElse(null);

        if (paciente == null && paciente.getCedula().equals(cedula))
            throw new ResourceNotFoundException("El paciente con " + id + " no se encuentra registrado");
    }

    public void existenciaCita(int codigo) {

        Cita cita = citaRepo.findById(codigo).orElse(null);

        if (cita == null) {
            throw new ResourceNotFoundException("La cita con " + codigo + " no se encuentra registrada");
        }
    }
    
    public void existenciaPqrs(int codigo) {
        
        Pqrs pqrs = pqrsRepo.findById(codigo).orElse(null);

        if (pqrs == null) {
            throw new ResourceNotFoundException("El pqrs con " + codigo + " no se encuentra registrado");
        }
    }

    public  void validarMedico(RegistroMedicoDTO medicoDTO) {
        if (estaRepetidaCedulaMedico(medicoDTO.cedula())){
            throw new ResourceNotFoundException("La cédula "+medicoDTO.cedula() + " está registrada con otro usuario");
        }
        if (estaRepetidoCorreoMedico(medicoDTO.correo())){
            throw new ResourceNotFoundException("El correo "+medicoDTO.correo() + " está registrado con otro usuario");
        }
    }

    public  void validarMedicoModificado(DetalleMedicoDTO medicoDTO) {
        if (estaRepetidaCedulaMedico(medicoDTO.cedula())){
            throw new ResourceNotFoundException("La cédula "+medicoDTO.cedula() + " está registrada con otro usuario");
        }
        if (estaRepetidoCorreoMedico(medicoDTO.correo())){
            throw new ResourceNotFoundException("El correo "+medicoDTO.correo() + " está registrado con otro usuario");
        }
    }

    public  void validarPaciente(RegistroPacienteDTO pacienteDTO) {
        if (estaRepetidaCedulaPaciente(pacienteDTO.cedula())){
            throw new ResourceNotFoundException("La cédula "+pacienteDTO.cedula() + " está registrada con otro usuario");
        }
        if (estaRepetidoCorreoPaciente(pacienteDTO.correo())){
            throw new ResourceNotFoundException("El correo "+pacienteDTO.correo() + " está registrado con otro usuario");
        }
    }


    private boolean estaRepetidaCedulaMedico(String cedula) {
        return medicoRepo.findByCedula(cedula)!=null;
    }

    private boolean estaRepetidoCorreoMedico(String correo) {

        return medicoRepo.findByCorreo(correo)!=null;

    }

    private boolean estaRepetidaCedulaPaciente(String cedula) {
        return pacienteRepo.findByCedula(cedula)!=null;
    }

    private boolean estaRepetidoCorreoPaciente(String correo) {

        return pacienteRepo.findByCorreo(correo)!=null;

    }

}
