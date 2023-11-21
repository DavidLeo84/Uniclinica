package co.edu.uniquindio.uniclinica.controladores;

import co.edu.uniquindio.uniclinica.dto.administrador.DetalleMedicoDTO;
import co.edu.uniquindio.uniclinica.dto.administrador.RegistroMedicoDTO;
import co.edu.uniquindio.uniclinica.dto.paciente.ItemCitaDTO;
import co.edu.uniquindio.uniclinica.dto.paciente.ItemPacienteDTO;
import co.edu.uniquindio.uniclinica.dto.paciente.ItemPqrsDTO;
import co.edu.uniquindio.uniclinica.dto.seguridad.MensajeDTO;
import co.edu.uniquindio.uniclinica.modelo.entidades.Medico;
import co.edu.uniquindio.uniclinica.modelo.enums.EstadoPqrs;
import co.edu.uniquindio.uniclinica.servicios.interfaces.AdministradorServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdministradorController {

    private final AdministradorServicio administradorServicio;

    @PostMapping("/crear-medico")
    public ResponseEntity<MensajeDTO<String>> crearMedico(@Valid @RequestBody RegistroMedicoDTO registro)throws Exception{
        Medico medico  = administradorServicio.crearMedico(registro);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "El médico con id " + medico.getId() + " fue registrado satisfactoriamente"));
    }

    @PutMapping("/actualizar-medico")
    public ResponseEntity<MensajeDTO<String>> acualizarMedico(@Valid @RequestBody DetalleMedicoDTO medico) throws Exception{
        administradorServicio.actualizarMedico(medico);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "El medico actualizado " + medico.nombre() + " fue actualizado con éxito"));
    }

    @DeleteMapping("/eliminar-medico/{id}")
    public ResponseEntity<MensajeDTO<String>> eliminarMedico(@PathVariable int id) throws Exception{
        return ResponseEntity.ok().body(new MensajeDTO<>(false, administradorServicio.eliminarMedico(id)));
    }

    @GetMapping("/listar-pacientes")
    public ResponseEntity<MensajeDTO<List<ItemPacienteDTO>>> listarPacientes() throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, administradorServicio.listarPacientes()));
    }

    @GetMapping("/detalle-medico/{id}")
    public ResponseEntity<MensajeDTO<DetalleMedicoDTO>> obtenerMedico(@PathVariable int id) throws Exception{
        return ResponseEntity.ok().body(new MensajeDTO<>(false, administradorServicio.obtenerMedico(id)));
    }

    @GetMapping("/listar-pqrs")
    ResponseEntity<MensajeDTO<List<ItemPqrsDTO>>> ListarPqrs() throws Exception{
        return ResponseEntity.ok().body(new MensajeDTO<>(false, administradorServicio.listarPqrs()));
    }

    @GetMapping("/listar-citas/{id}")
    ResponseEntity<MensajeDTO<List<ItemCitaDTO>>> listarCitasMedico(@PathVariable int id) throws Exception{
        return ResponseEntity.ok().body(new MensajeDTO<>(false, administradorServicio.listarCitasMedico(id)));
    }

    @PutMapping("/cambiar-estado-pqrs/{codigo}/{estado}")
    ResponseEntity<MensajeDTO<String>> cambiarEstadoPqrs(@PathVariable int codigo, @PathVariable EstadoPqrs estado) throws Exception{
        administradorServicio.cambiarEstadoPqrs(codigo,estado);
        return ResponseEntity.ok().body(new MensajeDTO<>(false, "Se ha actualizado con éxito la pqrs " + codigo));
    }

}