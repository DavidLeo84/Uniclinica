package co.edu.uniquindio.uniclinica.servicios.interfaces;

import co.edu.uniquindio.uniclinica.dto.administrador.DetalleMedicoDTO;
import co.edu.uniquindio.uniclinica.dto.medico.DiaLibreDTO;
import co.edu.uniquindio.uniclinica.dto.medico.ItemAtencionDTO;
import co.edu.uniquindio.uniclinica.dto.medico.RegistroAtencionDTO;
import co.edu.uniquindio.uniclinica.dto.paciente.DetallePacienteDTO;
import co.edu.uniquindio.uniclinica.dto.paciente.ItemCitaDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface MedicoServicio {

    List<ItemCitaDTO> listarCitasPendientes(DetalleMedicoDTO medicoDTO) throws Exception;

    List<ItemCitaDTO> listarCitasPendientesDia(DetalleMedicoDTO medicoDTO) throws Exception;

    void atenderCita(DetallePacienteDTO pacienteDTO, DetalleMedicoDTO medicoDTO,
                     RegistroAtencionDTO atencionDTO) throws Exception;

    List<ItemAtencionDTO> listarAtencionesPaciente(DetallePacienteDTO pacienteDTO) throws Exception ;

    LocalDateTime agendarDiaLibre(DetalleMedicoDTO medicoDTO,
                                  DiaLibreDTO fechaLibreDTO) throws Exception;

    LocalDateTime modificarDiaLibre(DetalleMedicoDTO medicoDTO, LocalDateTime nuevoDiaLibre) throws Exception;

    String cancelarDiaLibre(DetalleMedicoDTO medicoDTO) throws Exception;

    List<ItemCitaDTO> listarCitasRealizadasMedico(DetalleMedicoDTO medicoDTO) throws Exception;
}
