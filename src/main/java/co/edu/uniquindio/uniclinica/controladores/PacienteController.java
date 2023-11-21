package co.edu.uniquindio.uniclinica.controladores;


import co.edu.uniquindio.uniclinica.dto.administrador.DetallePqrsDTO;
import co.edu.uniquindio.uniclinica.dto.paciente.*;
import co.edu.uniquindio.uniclinica.dto.seguridad.MensajeDTO;
import co.edu.uniquindio.uniclinica.modelo.entidades.Cita;
import co.edu.uniquindio.uniclinica.modelo.entidades.Pqrs;
import co.edu.uniquindio.uniclinica.modelo.enums.Especialidad;
import co.edu.uniquindio.uniclinica.servicios.interfaces.AdministradorServicio;
import co.edu.uniquindio.uniclinica.servicios.interfaces.PacienteServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pacientes")
public class PacienteController {

    private final PacienteServicio pacienteServicio;


    @PutMapping("/editar-perfil")
    public ResponseEntity<MensajeDTO<String>> editarPerfil(@Valid @RequestBody DetallePacienteDTO pacienteDTO) throws Exception{
        pacienteServicio.editarPerfil(pacienteDTO);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Perfil actualizado correctamente"));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<MensajeDTO<String>> eliminarCuenta(@PathVariable int id) throws Exception{
        pacienteServicio.eliminarPaciente(id);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Perfil eliminado correctamente"));
    }

    @GetMapping("/detalle/{id}")
    public ResponseEntity<MensajeDTO<DetallePacienteDTO>> verDetallePaciente(@PathVariable int id) throws Exception{
        return ResponseEntity.ok().body(new MensajeDTO<>(false, pacienteServicio.verDetallePaciente(id)));
    }

    @PostMapping("/crear-cita")
    public ResponseEntity<MensajeDTO<String>> crearCita(@Valid @RequestBody RegistroCitaDTO citaDTO, @Valid @RequestBody String cedulaPaciente,
                                                          @Valid @RequestBody LocalDateTime fechaCita,
                                                          @PathVariable Especialidad especialidad)throws Exception{
        Cita cita = pacienteServicio.crearCita(citaDTO, cedulaPaciente, fechaCita, especialidad);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Registro de cita éxitoso"));
    }

    @PostMapping("/cancelar/{codigo}")
    public ResponseEntity<MensajeDTO<String>> cancelarCita(@PathVariable int codigo) throws Exception{
        pacienteServicio.cancelarCita(codigo);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "La cita fue cancelada satisfactoriamente"));
    }

    @GetMapping("/listar-historial-paciente/{codigo}")
    public ResponseEntity<MensajeDTO<List<ItemCitaDTO>>>  listarHistorialPaciente(@PathVariable int codigo) throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, pacienteServicio.listarHistorialPaciente(codigo)));
    }

    @GetMapping("/listar-pqrs-paciente/{id}")
    public ResponseEntity<MensajeDTO<List<ItemPqrsPacienteDTO>>>  listarPqrsPaciente(@PathVariable int id) throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, pacienteServicio.listarPqrsPaciente(id)));
    }

    @GetMapping("/citas-pendientes-paciente/{id}")
    public ResponseEntity<MensajeDTO<List<ItemCitaDTO>>> listarCitasPendientesPaciente(@PathVariable int id) throws Exception{
        return ResponseEntity.ok().body(new MensajeDTO<>(false, pacienteServicio.listarCitasPendientesPaciente(id)));
    }

    @GetMapping("/detalle-cita/{codigo}")
    ResponseEntity<MensajeDTO<DetalleCitaDTO>> verDetalleCita(@PathVariable int codigo) throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, pacienteServicio.verDetalleCita(codigo)));
    }

    @GetMapping("/filtrar-por-medico/{idPaciente}/{idMedico}")
    public ResponseEntity<MensajeDTO<List<ItemCitaDTO>>> filtrarCitasPorMedico(@PathVariable int idPaciente,@PathVariable int idMedico) throws Exception{
        return ResponseEntity.ok().body(new MensajeDTO<>(false, pacienteServicio.filtrarCitasMedico(idPaciente, idMedico)));
    }

    @GetMapping("/filtrar-por-fecha/{id}")
    public ResponseEntity<MensajeDTO<List<ItemCitaDTO>>> filtrarCitasPorFecha(@RequestBody @Valid LocalDateTime fechaInicio, @RequestBody @Valid LocalDateTime fechaFin,
                                                                              @PathVariable int id) throws Exception{
        return ResponseEntity.ok().body(new MensajeDTO<>(false, pacienteServicio.filtrarCitasPorFecha(fechaInicio, fechaFin, id)));
    }

    @PostMapping("/crear-pqrs")
    ResponseEntity<MensajeDTO<String>> crearPqrs(@RequestBody @Valid RegistroPqrsDTO registro) throws Exception{
        Pqrs pqrs = pacienteServicio.crearPqrs(registro);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Pqrs fue generada satisfactoriamente y su numero de peticion es " + pqrs.getCodigo()));
    }

}