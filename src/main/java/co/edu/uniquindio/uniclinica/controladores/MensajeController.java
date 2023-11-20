package co.edu.uniquindio.uniclinica.controladores;

import co.edu.uniquindio.uniclinica.dto.RespuestaDTO;
import co.edu.uniquindio.uniclinica.dto.paciente.RegistroRespuestaPacienteDTO;
import co.edu.uniquindio.uniclinica.dto.seguridad.MensajeDTO;
import co.edu.uniquindio.uniclinica.servicios.interfaces.MensajeServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mensajes")
public class MensajeController {

    private final MensajeServicio mensajeServicio;

    @PutMapping("/actualizar")
    public ResponseEntity<MensajeDTO<RegistroRespuestaPacienteDTO>> crearMensaje(@Valid @RequestBody RegistroRespuestaPacienteDTO registro) throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, mensajeServicio.crearMensaje(registro)));
    }

}
