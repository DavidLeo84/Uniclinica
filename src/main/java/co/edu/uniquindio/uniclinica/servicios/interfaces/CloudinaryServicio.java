package co.edu.uniquindio.uniclinica.servicios.interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public interface CloudinaryServicio {

    Map subirImagen(MultipartFile file) throws Exception;

    Map eliminarImagen(String idImagen) throws Exception;


}
