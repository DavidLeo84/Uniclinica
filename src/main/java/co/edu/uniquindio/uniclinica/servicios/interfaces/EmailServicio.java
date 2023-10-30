package co.edu.uniquindio.uniclinica.servicios.interfaces;

import co.edu.uniquindio.uniclinica.dto.EmailDTO;
import org.springframework.stereotype.Service;

@Service
public interface EmailServicio {

    String enviarCorreo(EmailDTO emailDTO) throws Exception;
}
