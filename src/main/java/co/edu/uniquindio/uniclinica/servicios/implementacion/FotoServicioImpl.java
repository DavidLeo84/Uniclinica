package co.edu.uniquindio.uniclinica.servicios.implementacion;

import co.edu.uniquindio.uniclinica.servicios.interfaces.FotoServicio;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class FotoServicioImpl implements FotoServicio {
    @Override
    public Map subirImagen(MultipartFile imagen) throws Exception {
        return null;
    }

    @Override
    public Map eliminarImagen(String idImagen) throws Exception {
        return null;
    }
}
