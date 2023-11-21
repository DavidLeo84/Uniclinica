package co.edu.uniquindio.uniclinica.servicios.interfaces;

import co.edu.uniquindio.uniclinica.dto.CambiarPasswordDTO;
import co.edu.uniquindio.uniclinica.dto.EmailDTO;
import co.edu.uniquindio.uniclinica.dto.administrador.DetalleMedicoDTO;
import co.edu.uniquindio.uniclinica.dto.administrador.ItemMedicoDTO;
import co.edu.uniquindio.uniclinica.dto.paciente.*;
import co.edu.uniquindio.uniclinica.modelo.entidades.Cita;
import co.edu.uniquindio.uniclinica.modelo.entidades.Paciente;
import co.edu.uniquindio.uniclinica.modelo.entidades.Pqrs;
import co.edu.uniquindio.uniclinica.modelo.enums.Especialidad;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface PacienteServicio {

    Paciente registrarse(RegistroPacienteDTO pacienteDTO);

    Paciente editarPerfil(DetallePacienteDTO pacienteDTO);

    DetallePacienteDTO  verDetallePaciente(int codigo) throws Exception;

    Paciente buscarPaciente(int id) throws Exception;

    DetallePacienteDTO obtenerPaciente(int id) throws Exception;

    String eliminarPaciente(int id) throws Exception;

    EmailDTO enviarLinkRecuperacion(String cedula) throws Exception ;

    String cambiarPassword(int id, String passwordNuevo) throws Exception;


    Cita crearCita(RegistroCitaDTO citaDTO, String cedulaPaciente, LocalDateTime fechaCita,
                  Especialidad especialidad) throws Exception;

    List<ItemMedicoDTO> listarPorEspecialidad(Especialidad especialidad) throws Exception;

    Cita buscarCita(int id) throws Exception;

    int modificarCita(DetalleCitaDTO citaDTO) throws Exception;

    String cancelarCita(int codigoCita) throws Exception;

    Pqrs crearPqrs(RegistroPqrsDTO pqrsDTO) throws Exception;

    List<ItemPqrsPacienteDTO> listarPqrsPaciente(int id) throws Exception;

    int responderPqrsPaciente(RegistroRespuestaPacienteDTO registroRespuestaPacienteDTO) throws Exception;

    List<DetalleCitaDTO> listarCitasPaciente(int id) throws Exception;

    List<ItemCitaDTO> filtrarCitasPorFecha(LocalDateTime fechaInicio, LocalDateTime fechaFin,
                                           int codigoPaciente) throws Exception;
    List<ItemCitaDTO> listarCitas() throws Exception;
    DetalleCitaDTO verDetalleCita(int codigoCita) throws Exception;

    List<ItemCitaDTO> listarHistorialPaciente(int codigoPaciente) throws Exception;

    List<ItemCitaDTO> listarCitasPendientesPaciente(int codigoPaciente) throws Exception;

    List<ItemCitaDTO> filtrarCitasMedico(int idPaciente, int idMedico) throws Exception;


}
