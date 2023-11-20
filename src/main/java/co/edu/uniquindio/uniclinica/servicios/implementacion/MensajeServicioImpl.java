package co.edu.uniquindio.uniclinica.servicios.implementacion;

import co.edu.uniquindio.uniclinica.dto.RespuestaDTO;
import co.edu.uniquindio.uniclinica.dto.paciente.RegistroRespuestaPacienteDTO;
import co.edu.uniquindio.uniclinica.modelo.entidades.Cuenta;
import co.edu.uniquindio.uniclinica.modelo.entidades.Mensaje;
import co.edu.uniquindio.uniclinica.modelo.entidades.Pqrs;
import co.edu.uniquindio.uniclinica.modelo.enums.TipoMensaje;
import co.edu.uniquindio.uniclinica.repositorios.CuentaRepo;
import co.edu.uniquindio.uniclinica.repositorios.MensajeRepo;
import co.edu.uniquindio.uniclinica.repositorios.PqrsRepo;
import co.edu.uniquindio.uniclinica.servicios.interfaces.MensajeServicio;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MensajeServicioImpl implements MensajeServicio {

    private final MensajeRepo mensajeRepo;
    private final PqrsRepo pqrsRepo;
    private final CuentaRepo cuentaRepo;

    @Override
    public RegistroRespuestaPacienteDTO crearMensaje(RegistroRespuestaPacienteDTO registro) throws Exception {

        Optional<Pqrs> pqrsOp = pqrsRepo.findById(registro.codigoPqrs());

        if (pqrsOp == null) {
            throw new Exception("No existe pqrs");
        }
        Pqrs pqrs = pqrsOp.get();
        Optional <Cuenta>  optional = cuentaRepo.findById(registro.codigoCuenta());

        Cuenta cuenta = optional.get();
        if (cuenta == null) {
            throw new Exception("No existe la cuenta");
        }
        return new RegistroRespuestaPacienteDTO(
                pqrs.getCodigo(),
                cuenta.getId(),
                TipoMensaje.REMITE,
                LocalDateTime.now(),
                registro.mensaje());
    }
    @Override
    public List<RespuestaDTO> listar() throws Exception {
        return null;
    }

    @Override
    public int eliminar(int codigo) throws Exception {
        return 0;
    }

    @Override
    public RespuestaDTO update(String contenido) throws Exception {
        return null;
    }

    @Override
    public RespuestaDTO obtener(int codigo) throws Exception {
        return null;
    }

    @Override
    public Mensaje validar(int codigo) throws Exception {
        return null;
    }
}
