package co.edu.uniquindio.uniclinica.servicios.interfaces;

import co.edu.uniquindio.uniclinica.dto.administrador.ItemMedicoDTO;
import co.edu.uniquindio.uniclinica.dto.paciente.*;
import co.edu.uniquindio.uniclinica.modelo.enums.Especialidad;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface PacienteServicio {

    int registrarse(RegistroPacienteDTO pacienteDTO) throws Exception;

    int editarPerfil(DetallePacienteDTO pacienteDTO) throws Exception;

    DetallePacienteDTO  verDetallePaciente(int pacienteDTO) throws Exception;

    void enviarLinkRecuperacion() throws Exception;

    String cambiarPassword(String passwordActualDTO, String passwordNewDTO,
                           DetallePacienteDTO pacienteDTO) throws Exception;


    int crearCita(DetalleMedicoCitaDTO medicoDTO, RegistroCitaDTO citaDTO,
                  DetallePacienteDTO pacienteDTO, LocalDateTime fechaCita,
                  Especialidad especialidad) throws Exception;

    List<ItemMedicoDTO> listarPorEspecialidad(Especialidad especialidad) throws Exception;

    int modificarCita(DetalleCitaDTO citaDTO) throws Exception;

    void cancelarCita(DetalleCitaDTO citaDTO) throws Exception;

    void crearPqrs(RegistroPqrsDTO pqrsDTO, DetallePacienteDTO pacienteDTO) throws Exception;

    List<ItemPqrsPacienteDTO> listarPqrsPaciente(int id) throws Exception;

    int responderPqrsPaciente(RegistroRespuestaPacienteDTO registroRespuestaPacienteDTO) throws Exception;

    List<DetalleCitaDTO> listarCitasPaciente(int id) throws Exception;

    List<ItemPacienteDTO> listarPacientes() throws Exception;

    List<ItemCitaDTO> filtrarCitasPorFecha(LocalDateTime fechaInicio,
                                           LocalDateTime fechaFin,
                                           DetallePacienteDTO pacienteDTO) throws Exception;

    List<ItemCitaDTO> filtrarCitasPorMedico(String nombreMedico) throws Exception;

    DetalleCitaDTO verDetalleCita(int id) throws Exception;

    void inactivarCuenta(int id) throws Exception;
}
