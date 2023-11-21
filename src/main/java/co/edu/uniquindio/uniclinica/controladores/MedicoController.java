package co.edu.uniquindio.uniclinica.controladores;

import co.edu.uniquindio.uniclinica.dto.medico.DiaLibreDTO;
import co.edu.uniquindio.uniclinica.dto.medico.RegistroAtencionDTO;
import co.edu.uniquindio.uniclinica.dto.paciente.ItemCitaDTO;
import co.edu.uniquindio.uniclinica.dto.seguridad.MensajeDTO;
import co.edu.uniquindio.uniclinica.servicios.interfaces.AdministradorServicio;
import co.edu.uniquindio.uniclinica.servicios.interfaces.MedicoServicio;
import co.edu.uniquindio.uniclinica.servicios.interfaces.PacienteServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/medicos")
public class MedicoController {
    
    private final AdministradorServicio administradorServicio;
    private final PacienteServicio pacienteServicio;
    private final MedicoServicio medicoServicio;


    @PostMapping("/atender-cita")
    public ResponseEntity<MensajeDTO<String>> atenderCita(@Valid @RequestBody String cedula, @Valid @RequestBody int id)throws Exception{
        RegistroAtencionDTO atencion = medicoServicio.atenderCita(cedula, id);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "La atención de la cita " + atencion.codigoCita() + " fue registrada correctamente"));
    }

    @GetMapping("/listar-citas-realizadas/{id}")
    public ResponseEntity<MensajeDTO<List<ItemCitaDTO>>> listarCitasRealizadasMedico(@PathVariable int id) throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, medicoServicio.listarCitasRealizadasMedico(id)));
    }

    @PostMapping("/agendar-dia-libre")
    public ResponseEntity<MensajeDTO<String>> agendarDiaLibre(@PathVariable int idMedico , @Valid @RequestBody LocalDate fechaLibre)throws Exception{
        medicoServicio.agendarDiaLibre(idMedico, fechaLibre);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Su dia libre quedó para el " + fechaLibre));
    }

    @PutMapping("/modificar/{id}")
    public ResponseEntity<MensajeDTO<LocalDate>> modificarDiaLibre(@PathVariable int id, @Valid @RequestBody LocalDate fechaLibre) throws Exception{
        return ResponseEntity.ok().body(new MensajeDTO<>(false, medicoServicio.modificarDiaLibre(id, fechaLibre)));
    }

    @DeleteMapping("/cancelar/{id}")
    public ResponseEntity<MensajeDTO<String>> cancelarDiaLibre(@PathVariable int id) throws Exception{
        medicoServicio.cancelarDiaLibre(id);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "La fecha de día libre fue cancelada con éxito"));
    }

    @GetMapping("/citas-pendientes/{id}")
    public ResponseEntity<MensajeDTO<List<ItemCitaDTO>>> listarCitasPendientes(@PathVariable int id) throws Exception{
        return ResponseEntity.ok().body(new MensajeDTO<>(false, medicoServicio.listarCitasPendientes(id)));
    }

    @GetMapping("/citas-pendientes-dia/{id}")
    public ResponseEntity<MensajeDTO<List<ItemCitaDTO>>> listarCitasPendientesDia(@PathVariable int id) throws Exception{
        return ResponseEntity.ok().body(new MensajeDTO<>(false, medicoServicio.listarCitasPendientesDia(id)));
    }






}
