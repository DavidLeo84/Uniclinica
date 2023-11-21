package co.edu.uniquindio.uniclinica.servicios.interfaces;

import co.edu.uniquindio.uniclinica.dto.administrador.*;
import co.edu.uniquindio.uniclinica.dto.paciente.ItemCitaDTO;
import co.edu.uniquindio.uniclinica.dto.paciente.ItemPacienteDTO;
import co.edu.uniquindio.uniclinica.dto.paciente.ItemPqrsDTO;
import co.edu.uniquindio.uniclinica.dto.paciente.RegistroRespuestaPacienteDTO;
import co.edu.uniquindio.uniclinica.modelo.entidades.Medico;
import co.edu.uniquindio.uniclinica.modelo.entidades.Pqrs;
import co.edu.uniquindio.uniclinica.modelo.enums.EstadoPqrs;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdministradorServicio {

    Medico crearMedico(RegistroMedicoDTO medicoDTO)throws Exception;

    Medico actualizarMedico(DetalleMedicoDTO medicoDTO) throws Exception;

    String eliminarMedico(int codigo) throws Exception;

    List<ItemMedicoDTO> listarMedicos() throws Exception;

    DetalleMedicoDTO obtenerMedico(int codigo) throws Exception;

    //Medico buscarMedico(int id) throws  Exception;

    List<ItemPqrsDTO> listarPqrs() throws Exception;

    //Medico actualizarMedico2(Medico medico);

    DetallePqrsDTO verDetallePqrs(int codigo) throws Exception;

    Pqrs responderPqrs(int codigoPqrs, int idAdmin,
                       RegistroRespuestaPacienteDTO registro) throws Exception;

    void cambiarEstadoPqrs(int codigoPqrs, EstadoPqrs nuevoEstado) throws Exception;

    List<ItemCitaDTO> listarCitasMedico(int codigoMedico) throws Exception;

    List<ItemPacienteDTO>listarPacientes() throws Exception;
}
