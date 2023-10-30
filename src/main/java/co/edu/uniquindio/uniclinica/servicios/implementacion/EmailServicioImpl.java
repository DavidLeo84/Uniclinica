package co.edu.uniquindio.uniclinica.servicios.implementacion;

import co.edu.uniquindio.uniclinica.dto.EmailDTO;
import co.edu.uniquindio.uniclinica.servicios.interfaces.EmailServicio;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class EmailServicioImpl implements EmailServicio {
    @Override
    public String enviarCorreo(EmailDTO emailDTO) throws Exception {
        return null;
    }
}
