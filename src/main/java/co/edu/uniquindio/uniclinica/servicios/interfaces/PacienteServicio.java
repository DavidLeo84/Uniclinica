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

    DetallePacienteDTO  verDetallePaciente(int codigo) throws Exception;

    void inactivarCuenta(int id) throws Exception;

    void enviarLinkRecuperacion() throws Exception;

    String cambiarPassword(String passwordActualDTO, String passwordNewDTO,
                           DetallePacienteDTO pacienteDTO) throws Exception;


    int crearCita(RegistroCitaDTO citaDTO, String cedulaPaciente, LocalDateTime fechaCita,
                  Especialidad especialidad) throws Exception;

    List<ItemMedicoDTO> listarPorEspecialidad(Especialidad especialidad) throws Exception;

    DetalleCitaDTO modificarCita(int codigoCita) throws Exception;

    String cancelarCita(int codigoCita) throws Exception;

    int crearPqrs(RegistroPqrsDTO pqrsDTO) throws Exception;

    List<ItemPqrsPacienteDTO> listarPqrsPaciente(int id) throws Exception;

    int responderPqrsPaciente(RegistroRespuestaPacienteDTO registroRespuestaPacienteDTO) throws Exception;

    List<DetalleCitaDTO> listarCitasPaciente(int id) throws Exception;

    List<ItemCitaDTO> filtrarCitasPorFecha(LocalDateTime fechaInicio, LocalDateTime fechaFin,
                                           int codigoPaciente) throws Exception;
    List<ItemCitaDTO> listarCitas() throws Exception;
    DetalleCitaDTO verDetalleCita(int codigoCita) throws Exception;

    List<ItemCitaDTO> listarHistorialPaciente(int codigoPaciente) throws Exception;

    List<ItemCitaDTO> listarCitasPendientesPaciente(int codigoPaciente) throws Exception;

    List<ItemCitaDTO> filtrarCitasMedico(int idMedico) throws Exception;


}
