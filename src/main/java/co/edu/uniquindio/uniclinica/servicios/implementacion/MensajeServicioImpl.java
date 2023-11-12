package co.edu.uniquindio.uniclinica.servicios.implementacion;

import co.edu.uniquindio.uniclinica.dto.RespuestaDTO;
import co.edu.uniquindio.uniclinica.dto.administrador.RegistroRespuestaDTO;
import co.edu.uniquindio.uniclinica.modelo.entidades.Mensaje;
import co.edu.uniquindio.uniclinica.servicios.interfaces.MensajeServicio;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MensajeServicioImpl implements MensajeServicio {
    @Override
    public int crear(RegistroRespuestaDTO datos) throws Exception {
        return 0;
    }

    @Override
    public List<RespuestaDTO> listar() throws Exception {
        return null;
    }

    @Override
    public int eliminar(int codigo) throws Exception {
        return 0;
    }

    @Override
    public RespuestaDTO update(String contenido) throws Exception {
        return null;
    }

    @Override
    public RespuestaDTO obtener(int codigo) throws Exception {
        return null;
    }

    @Override
    public Mensaje validar(int codigo) throws Exception {
        return null;
    }
}
