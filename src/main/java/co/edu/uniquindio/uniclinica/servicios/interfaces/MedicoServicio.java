package co.edu.uniquindio.uniclinica.servicios.interfaces;

import co.edu.uniquindio.uniclinica.dto.administrador.DetalleMedicoDTO;
import co.edu.uniquindio.uniclinica.dto.medico.DiaLibreDTO;
import co.edu.uniquindio.uniclinica.dto.medico.ItemAtencionDTO;
import co.edu.uniquindio.uniclinica.dto.medico.RegistroAtencionDTO;
import co.edu.uniquindio.uniclinica.dto.paciente.DetallePacienteDTO;
import co.edu.uniquindio.uniclinica.dto.paciente.ItemCitaDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public interface MedicoServicio {

    List<ItemCitaDTO> listarCitasPendientes(int idMedico) throws Exception;

    List<ItemCitaDTO> listarCitasPendientesDia(int idMedico) throws Exception;

    RegistroAtencionDTO atenderCita(String cedulaPaciente, int idMedico) throws Exception;

    List<ItemAtencionDTO> listarAtencionesPaciente(int idPaciente) throws Exception ;

    LocalDate agendarDiaLibre(int idMedico, LocalDate fechaLibre) throws Exception;

    LocalDate modificarDiaLibre(int idMedico, LocalDate nuevoDiaLibre) throws Exception;

    String cancelarDiaLibre(int idMedico) throws Exception;

    List<ItemCitaDTO> listarCitasRealizadasMedico(int idMedico) throws Exception;
}
