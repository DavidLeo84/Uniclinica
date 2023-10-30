package co.edu.uniquindio.uniclinica.servicios.interfaces;

import co.edu.uniquindio.uniclinica.dto.administrador.*;
import co.edu.uniquindio.uniclinica.dto.paciente.ItemPqrsDTO;
import co.edu.uniquindio.uniclinica.modelo.enums.EstadoPqrs;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdministradorServicio {

    int crearMedico(RegistroMedicoDTO medicoDTO) throws Exception;

    int actualizarMedico(DetalleMedicoDTO medicoDTO) throws Exception;

    String eliminarMedico(DetalleMedicoDTO medicoDTO) throws Exception;

    List<ItemMedicoDTO> listarMedicos() throws Exception;

    DetalleMedicoDTO obtenerMedico(DetalleMedicoDTO medicoDTO) throws Exception;

    List<ItemPqrsDTO> listarPqrs() throws Exception;

    DetallePqrsDTO verDetallePqrs(DetallePqrsDTO pqrsDTO) throws Exception;

    int responderPqrs(DetallePqrsDTO pqrsDTO, DetalleAdministradorDTO adminDTO,
                      RegistroRespuestaDTO registroRespuestaDTO) throws Exception;

    void cambiarEstadoPqrs(int codigoPqrs, EstadoPqrs estadoPqrs) throws Exception;

    List<ItemCitaAdminDTO> listarCitasMedico(DetalleMedicoDTO medicoDTO) throws Exception;
}
