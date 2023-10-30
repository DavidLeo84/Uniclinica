package co.edu.uniquindio.uniclinica.servicios.interfaces;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public interface FotoServicio {

    Map subirImagen(MultipartFile imagen) throws Exception;


    Map eliminarImagen(String idImagen) throws Exception;
}
