package co.edu.uniquindio.uniclinica.controladores;

import co.edu.uniquindio.uniclinica.dto.administrador.ItemMedicoDTO;
import co.edu.uniquindio.uniclinica.dto.paciente.RegistroPacienteDTO;
import co.edu.uniquindio.uniclinica.dto.seguridad.LoginDTO;
import co.edu.uniquindio.uniclinica.dto.seguridad.MensajeDTO;
import co.edu.uniquindio.uniclinica.dto.seguridad.TokenDTO;
import co.edu.uniquindio.uniclinica.servicios.interfaces.AdministradorServicio;
import co.edu.uniquindio.uniclinica.servicios.interfaces.AutenticacionServicio;
import co.edu.uniquindio.uniclinica.servicios.interfaces.PacienteServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AutenticacionController {

    private final AutenticacionServicio autenticacionServicio;
    private final AdministradorServicio administradorServicio;
    private final PacienteServicio pacienteServicio;


    @PostMapping("/login")
    public ResponseEntity<MensajeDTO<TokenDTO>> login(@Valid @RequestBody LoginDTO loginDTO)
            throws Exception {
        TokenDTO tokenDTO = autenticacionServicio.login(loginDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, tokenDTO));
    }
    @PostMapping("/registrarse")
    public ResponseEntity<MensajeDTO<String>> registrarse(@Valid @RequestBody RegistroPacienteDTO pacienteDTO) throws Exception{
        pacienteServicio.registrarse(pacienteDTO);
        return ResponseEntity.ok().body( new MensajeDTO<>(false, "Paciente registrado correctamente") );
    }
    @GetMapping("/recuperar-password/{cedula}")
    public ResponseEntity<MensajeDTO<String>> enviarLinkRecuperacion(@Valid @PathVariable String cedula) throws Exception {
        pacienteServicio.enviarLinkRecuperacion(cedula);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Contraseña recuperada"));
    }

    @GetMapping("/listar-medicos")
    public ResponseEntity<MensajeDTO<List<ItemMedicoDTO>>> listarMedicos() throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, administradorServicio.listarMedicos()));
    }



}
