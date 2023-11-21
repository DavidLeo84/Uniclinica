package co.edu.uniquindio.uniclinica.servicios.interfaces;

import co.edu.uniquindio.uniclinica.dto.seguridad.LoginDTO;
import co.edu.uniquindio.uniclinica.dto.seguridad.TokenDTO;
import org.springframework.stereotype.Service;

@Service
public interface AutenticacionServicio {

    TokenDTO login(LoginDTO loginDTO) throws Exception;
}
