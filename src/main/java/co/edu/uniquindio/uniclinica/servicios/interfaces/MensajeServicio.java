package co.edu.uniquindio.uniclinica.servicios.interfaces;

import co.edu.uniquindio.uniclinica.dto.RespuestaDTO;
import co.edu.uniquindio.uniclinica.dto.administrador.RegistroRespuestaDTO;
import co.edu.uniquindio.uniclinica.modelo.entidades.Mensaje;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MensajeServicio {

    int crear(RegistroRespuestaDTO datos) throws Exception;


    List<RespuestaDTO> listar() throws Exception;


    int eliminar(int codigo) throws Exception;

    RespuestaDTO update(String contenido) throws Exception;

    RespuestaDTO obtener(int codigo) throws Exception;

    Mensaje validar(int codigo) throws Exception;
}
