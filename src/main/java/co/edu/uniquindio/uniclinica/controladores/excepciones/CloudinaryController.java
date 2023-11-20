package co.edu.uniquindio.uniclinica.controladores.excepciones;

import co.edu.uniquindio.uniclinica.dto.ImagenDTO;
import co.edu.uniquindio.uniclinica.dto.seguridad.MensajeDTO;
import co.edu.uniquindio.uniclinica.servicios.interfaces.CloudinaryServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/imagenes")
@RequiredArgsConstructor

public class CloudinaryController {

    private final CloudinaryServicio cloudinaryServicio;

    @PostMapping("/subir")
    public ResponseEntity<MensajeDTO<Map>> subir(@RequestParam("file") MultipartFile imagen)
            throws Exception {
        Map respuesta = cloudinaryServicio.subirImagen(imagen);

        return ResponseEntity.ok().body(new MensajeDTO<>(false, respuesta));
    }

    public ResponseEntity<MensajeDTO<Map>> eliminar(@RequestBody ImagenDTO imagenDTO) throws
            Exception {
        Map respuesta = cloudinaryServicio.eliminarImagen(imagenDTO.id());
        return ResponseEntity.ok().body(new MensajeDTO<>(false, respuesta));
    }


}


