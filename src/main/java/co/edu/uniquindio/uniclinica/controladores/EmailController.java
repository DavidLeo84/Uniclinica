package co.edu.uniquindio.uniclinica.controladores;

import co.edu.uniquindio.uniclinica.dto.EmailDTO;
import co.edu.uniquindio.uniclinica.dto.administrador.DetalleMedicoDTO;
import co.edu.uniquindio.uniclinica.dto.administrador.RegistroMedicoDTO;
import co.edu.uniquindio.uniclinica.dto.paciente.ItemCitaDTO;
import co.edu.uniquindio.uniclinica.dto.paciente.ItemPacienteDTO;
import co.edu.uniquindio.uniclinica.dto.paciente.ItemPqrsDTO;
import co.edu.uniquindio.uniclinica.dto.seguridad.MensajeDTO;
import co.edu.uniquindio.uniclinica.modelo.entidades.Medico;
import co.edu.uniquindio.uniclinica.modelo.enums.EstadoPqrs;
import co.edu.uniquindio.uniclinica.servicios.interfaces.AdministradorServicio;
import co.edu.uniquindio.uniclinica.servicios.interfaces.EmailServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/imagenes")
@RequiredArgsConstructor
public class EmailController {

    private final EmailServicio emailServicio;
    @PostMapping("/enviar")
    public ResponseEntity<MensajeDTO<String>> enviarEmail(@RequestParam("file") EmailDTO emailDTO) throws Exception{
        emailServicio.enviarCorreo(emailDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false,"Se ha enviado un correo electronico"));
    }

}
