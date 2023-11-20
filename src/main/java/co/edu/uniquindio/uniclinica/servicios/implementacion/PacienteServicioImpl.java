package co.edu.uniquindio.uniclinica.servicios.implementacion;

import co.edu.uniquindio.uniclinica.dto.CambiarPasswordDTO;
import co.edu.uniquindio.uniclinica.dto.EmailDTO;
import co.edu.uniquindio.uniclinica.dto.RespuestaDTO;
import co.edu.uniquindio.uniclinica.dto.administrador.*;
import co.edu.uniquindio.uniclinica.servicios.interfaces.CloudinaryServicio;
import co.edu.uniquindio.uniclinica.servicios.interfaces.EmailServicio;
import co.edu.uniquindio.uniclinica.servicios.validaciones.Validacion;
import co.edu.uniquindio.uniclinica.servicios.validaciones.excepciones.ResourceNotFoundException;
import co.edu.uniquindio.uniclinica.dto.paciente.*;
import co.edu.uniquindio.uniclinica.modelo.entidades.*;
import co.edu.uniquindio.uniclinica.modelo.enums.Especialidad;
import co.edu.uniquindio.uniclinica.modelo.enums.EstadoCita;
import co.edu.uniquindio.uniclinica.modelo.enums.EstadoPqrs;
import co.edu.uniquindio.uniclinica.modelo.enums.EstadoUsuario;
import co.edu.uniquindio.uniclinica.repositorios.*;
import co.edu.uniquindio.uniclinica.servicios.interfaces.PacienteServicio;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PacienteServicioImpl implements PacienteServicio {

    private final MedicoRepo medicoRepo;
    private final PacienteRepo pacienteRepo;
    private final PqrsRepo pqrsRepo;
    private final CuentaRepo cuentaRepo;
    private final MensajeRepo mensajeRepo;
    private final EmailServicio emailServicio;
    private final CitaRepo citaRepo;
    private final HorarioRepo horarioRepo;
    private final Validacion validacion;
    private final CloudinaryServicio cloudinaryServicio;

    @Override
    public Paciente registrarse(RegistroPacienteDTO pacienteDTO) {

        validacion.existenciaPaciente(pacienteDTO.cedula());

        Paciente paciente = new Paciente();
        paciente.setCedula(pacienteDTO.cedula());
        paciente.setTelefono(pacienteDTO.telefono());
        paciente.setNombre(pacienteDTO.nombre());
        paciente.setUrlFoto(pacienteDTO.urlFoto());
        paciente.setCiudad(pacienteDTO.ciudad());
        paciente.setFechaNacimiento(pacienteDTO.fechaNacimiento());
        paciente.setAlergias(pacienteDTO.alergias());
        paciente.setEps(pacienteDTO.eps());
        paciente.setTipoSangre(pacienteDTO.tipoSangre());
        paciente.setCorreo(pacienteDTO.correo());

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String passwordEncriptada = passwordEncoder.encode(pacienteDTO.password());
        paciente.setPassword(passwordEncriptada);
        paciente.setEstado(EstadoUsuario.ACTIVO);
        pacienteRepo.save(paciente);

        return paciente;
    }

    @Override
    public Paciente editarPerfil(DetallePacienteDTO pacienteDTO) {

        validacion.validarPacienteActualizado(pacienteDTO.cedula(), pacienteDTO.correo());
        Optional<Paciente> optional = pacienteRepo.findById(pacienteDTO.codigoPaciente());

        Paciente pacActualizado = optional.get();
        pacActualizado.setNombre(pacienteDTO.nombre());
        pacActualizado.setCedula(pacienteDTO.cedula());
        pacActualizado.setCorreo(pacienteDTO.correo());
        pacActualizado.setCiudad(pacienteDTO.ciudad());
        pacActualizado.setTipoSangre(pacienteDTO.tipoSangre());
        pacActualizado.setTelefono(pacienteDTO.telefono());
        pacActualizado.setUrlFoto(pacienteDTO.urlFoto());
        pacienteRepo.save(pacActualizado);

        return pacActualizado;
    }

    @Override
    public DetallePacienteDTO verDetallePaciente(int codigo) throws Exception {

        Optional<Paciente> optional = pacienteRepo.findById(codigo);

        if (optional.isEmpty()) {
            throw new Exception("El usuario no es un paciente registrado");
        }
        Paciente pacActualizado = optional.get();
        return new DetallePacienteDTO(
                pacActualizado.getId(),
                pacActualizado.getCedula(),
                pacActualizado.getCiudad(),
                pacActualizado.getNombre(),
                pacActualizado.getTelefono(),
                pacActualizado.getCorreo(),
                pacActualizado.getTipoSangre(),
                pacActualizado.getUrlFoto(),
                pacActualizado.getFechaNacimiento(),
                pacActualizado.getAlergias(),
                pacActualizado.getEps()
        );
    }

    @Override
    public Paciente buscarPaciente(int id) throws Exception {
        Paciente paciente = pacienteRepo.findById(id).orElse(null);
        if(paciente == null)
            throw new Exception("El paciente no existe");
        return paciente;
    }

    @Override
    public DetallePacienteDTO obtenerPaciente(int codigo) throws Exception {

        Paciente paciente = validacion.existenciaIdPaciente(codigo);
        if(paciente.getEstado()==EstadoUsuario.INACTIVO){
            throw new ValidationException("El paciente esta Inactivo");
        }
        return new DetallePacienteDTO(paciente);
    }


    @Override
    public String eliminarPaciente(int id) throws Exception {

        Cuenta cuenta = cuentaRepo.findById(id).orElse(null);
        if (cuenta == null) {
            throw new Exception("El " + id + " no existe en los registros de cuentas de usuarios");
        }
        //Optional<Cuenta> optional = cuentaRepo.findById(id);
        List<Cita> citas = citaRepo.buscarCitasPaciente(id);
        int contador = 0;
        for (Cita c : citas) {
            if (c.getEstadoCita().equals(EstadoCita.PROGRAMADA)) {
                throw new Exception("No puede cancelar la cuenta porque tiene citas programadas");
            }
            contador++;
        }
        if (contador == 0) {
            cuenta.setEstado(EstadoUsuario.INACTIVO);
            cuentaRepo.save(cuenta);
        }
        return "La cuenta del paciente fue eliminado correctamente";
    }

    @Override
    public EmailDTO enviarLinkRecuperacion(String cedula) throws Exception {

        Paciente paciente = pacienteRepo.findByCedula(cedula);
        if (paciente == null) {
            throw new Exception("La cédula " + cedula + " no se encuentra registrada en el sistema");
        }
        String passwordGenerica = "zxcdsaqwemnbjklpoi";
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String passwordEncriptada = passwordEncoder.encode(passwordGenerica);
        paciente.setPassword(passwordEncriptada);
        pacienteRepo.save(paciente);

        String asunto = "";
        String cuerpo = "";
        String destinatario = paciente.getCorreo();

        EmailDTO emailDTO = new EmailDTO(
                asunto,
                cuerpo,
                destinatario
        );
        emailServicio.enviarCorreo(emailDTO);
        return emailDTO;
    }

    @Override
    public String cambiarPassword(int id, String passwordNuevo) throws Exception {

        //Paciente paciente = validacion.existenciaIdPaciente(id);
        Optional<Cuenta> optional = cuentaRepo.findById(id);

        Cuenta buscado = optional.get();
        if (buscado.getPassword().equals(passwordNuevo)) {
            throw new Exception("Esta intentando cambiar la contraseña por la misma contraseña actual");
        }
            buscado.setPassword(passwordNuevo);
            cuentaRepo.save(buscado);
        return "La contraseña se cambio con éxito";
    }

    // Pendiente enviar correos medico y paciente
    @Override
    public Cita crearCita(RegistroCitaDTO citaDTO, String cedulaPaciente, LocalDateTime fechaCita,
                         Especialidad especialidad) throws Exception {

        LocalDateTime fechaHoy = LocalDateTime.now();
        List<Cita> citas = citaRepo.findByPacienteCedula(cedulaPaciente);
        List<Medico> medicos = medicoRepo.findAllByEspecialidad(especialidad);
        List<Cita> citasActivas = new ArrayList<>();
        if (medicos.isEmpty()) {
            throw new Exception("No hay disponibilidad de medicos con la especialidad " + especialidad);
        }
        for (Cita c : citas) {
            if (c.getEstadoCita().equals(EstadoCita.PROGRAMADA)) {
                citasActivas.add(c);
            }
        }
        if (citasActivas.size() >= 3) {
            throw new Exception("Ya tiene 3 citas agendadas. No puede agendar por el momento");
        }
        var anticipacion = Duration.between(fechaHoy, fechaCita).toMinutes() < 30;
        if (fechaCita.isBefore(fechaHoy)) {
            throw new Exception("Fecha no valida");
        }
        if (anticipacion) {
            throw new ValidationException("Debe de agendar su cita con minimo 30 minutos de anticipación a la fecha deseada");
        }
        Cita nuevaCita = new Cita();

        for (Medico m : medicos) {

            List<Cita> citasMedico = citaRepo.buscarCitasMedico(m.getId());

            for (Cita c : citasMedico) {
                if (c.getFechaCita().equals(fechaCita)) {
                    throw new Exception("El horario" + fechaCita + " ya está ocupado");
                }
            }
            List<HorarioMedico> horarios = horarioRepo.findAllByMedicoCodigo(m.getId());
            for (HorarioMedico hm : horarios) {
                if (
                        hm.getHoraInicio().minusMinutes(1).isAfter(LocalDateTime.from(fechaCita))
                                && hm.getHoraSalida().plusMinutes(1).isAfter(LocalDateTime.from(fechaCita))) {
                    nuevaCita.setFechaCreacion(LocalDateTime.now());
                    nuevaCita.setFechaCita(fechaCita);
                    nuevaCita.setMotivo(citaDTO.motivo());
                    nuevaCita.getPaciente().setNombre(citaDTO.nombrePaciente());
                    nuevaCita.getMedico().setNombre(citaDTO.nombreMedico());
                    nuevaCita.setEstadoCita(EstadoCita.PROGRAMADA);
                    citaRepo.save(nuevaCita);
                    horarioRepo.save(hm);
                    String asunto = "Aviso nueva cita agenda";
                    String cuerpo = " Se ha agendado una cita en la fecha" + nuevaCita.getFechaCita() +
                            " con el medico " + nuevaCita.getMedico().getNombre();
                    String destinatario = nuevaCita.getPaciente().getCorreo();
                    EmailDTO emailDTO = new EmailDTO( asunto, cuerpo, destinatario);
                    emailServicio.enviarCorreo(emailDTO);

                } else {
                    throw new Exception("El médico " + m.getNombre() + " no horario disponible");
                }
            }
        }
        return nuevaCita;
    }

    public List<ItemMedicoDTO> listarPorEspecialidad(Especialidad especialidad) throws Exception {

        List<Medico> medicos = medicoRepo.findAllByEspecialidad(especialidad);

        if (medicos.isEmpty()) {
            throw new Exception("No hay médicos registrados con esa especialidad");
        }
        List<ItemMedicoDTO> lista = new ArrayList<>();

        for (Medico m : medicos) {

            lista.add(new ItemMedicoDTO(
                    m.getId(),
                    m.getCedula(),
                    m.getNombre(),
                    m.getUrlFoto(),
                    m.getEspecialidad())
            );
        }
        return lista;
    }
    @Override
    public Cita buscarCita(int id) throws Exception {
        Cita cita = citaRepo.findById(id).orElse(null);
        if (cita == null) {
            throw new Exception("La cita " + id + "no existe");
        }
        return cita;
    }

    @Override
    public int modificarCita(DetalleCitaDTO citaDTO) throws Exception {

        Optional<Cita> optional = citaRepo.findById(citaDTO.codigoCita());

        if (optional.isEmpty()) {
            throw new Exception("El codigo de cita no existe");
        }
        Cita buscado = optional.get();
        buscado.setFechaCita(citaDTO.fechaCita());
        buscado.setMotivo(citaDTO.motivo());
        buscado.getMedico().setNombre(citaDTO.nombreMedico());
        citaRepo.save(buscado);
        return buscado.getCodigo();
    }

    @Override
    public String cancelarCita(int codigoCita) throws Exception {

        Optional<Cita> optional = citaRepo.findById(codigoCita);
        Cita buscado = optional.get();
        validacion.existenciaCita(buscado.getCodigo());
            buscado.setEstadoCita(EstadoCita.CANCELADA);
            citaRepo.save(buscado);
        return "La cita con codigo " + codigoCita + " fue cancelada con exito";
    }

    //Falta parte de enviar correo al administrador
    @Override
    public Pqrs crearPqrs(RegistroPqrsDTO pqrsDTO) throws Exception {

        List<Pqrs> pqrs = pqrsRepo.buscarPqrsPaciente(pqrsDTO.codigoPaciente());
        List<Mensaje> mensajes = new ArrayList<>();
        List<Pqrs> prqsEnProceso = new ArrayList<>();
        for (Pqrs pq : pqrs) {
            if (pq.getEstadoPqrs().equals(EstadoPqrs.NUEVO) || pq.getEstadoPqrs().equals(EstadoPqrs.EN_PROCESO)) {
                prqsEnProceso.add(pq);
            }
        }
        if (prqsEnProceso.size() > 3) {
            throw new Exception("Ya tiene 3 pqrs en proceso. No puede crear mas por el momento");
        }
        Pqrs nuevaPqrs = new Pqrs();
        nuevaPqrs.setFechaCreacion(LocalDateTime.now());
        nuevaPqrs.setTipoPqrs(pqrsDTO.tipoPqrs());
        nuevaPqrs.setMotivo(pqrsDTO.motivo());
        nuevaPqrs.setCodigo(pqrsDTO.codigoCita());
        nuevaPqrs.setEstadoPqrs(EstadoPqrs.NUEVO);
        convertirRespuestasPacienteDTO(mensajes);
        pqrsRepo.save(nuevaPqrs);

        return nuevaPqrs;
    }
    @Override
    public List<ItemPqrsPacienteDTO> listarPqrsPaciente(int id) throws Exception{

        List<Pqrs> pqrs =  pqrsRepo.buscarPqrsPaciente(id);
        List<ItemPqrsPacienteDTO> listaPqrs = new ArrayList<>();

        if (pqrs.isEmpty()){
            throw new Exception("No tiene pqrs creadas");
        }

        for (Pqrs p: pqrs) {
            listaPqrs.add(new ItemPqrsPacienteDTO(
                    p.getCodigo(),
                    p.getEstadoPqrs(),
                    p.getMotivo(),
                    p.getFechaCreacion()
            ));
        }
        return listaPqrs;
    }

    @Override
    public int responderPqrsPaciente(RegistroRespuestaPacienteDTO registroRespuestaPacienteDTO) throws Exception{

        Optional<Pqrs> opcionalPqrs = pqrsRepo.findById(registroRespuestaPacienteDTO.codigoPqrs());

        if(opcionalPqrs.isEmpty()){
            throw new Exception("No existe una peticion con el código " + registroRespuestaPacienteDTO.codigoPqrs());
        }
        Optional<Cuenta> opcionalCuenta = cuentaRepo.findById(registroRespuestaPacienteDTO.codigoCuenta());

        Cuenta buscada = opcionalCuenta.get();
        validacion.existenciaIdPaciente(buscada.getId());
        Mensaje mensajeNuevo = new Mensaje();
        mensajeNuevo.setPqrs(opcionalPqrs.get());
        mensajeNuevo.setFechaMensaje( LocalDateTime.now() );
        mensajeNuevo.setCuenta(opcionalCuenta.get());
        mensajeNuevo.setContenido(registroRespuestaPacienteDTO.mensaje() );

        Mensaje respuesta = mensajeRepo.save(mensajeNuevo);

        return respuesta.getCodigo();
    }

    private List<RespuestaDTO> convertirRespuestasPacienteDTO(List<Mensaje> mensajes) {
        return mensajes.stream().map(m -> new RespuestaDTO(
                m.getCodigo(),
                m.getContenido(),
                m.getCuenta().getCorreo(),
                m.getFechaMensaje()
        )).toList();
    }

    @Override
    public List<DetalleCitaDTO> listarCitasPaciente(int id) throws Exception{

        List<Cita> listaCitas = citaRepo.buscarCitasPaciente(id);

        if (listaCitas.isEmpty()) {

            throw new Exception("No existen citas agendadas");
        }

        List<DetalleCitaDTO> citas =new ArrayList<>();

        for (Cita c : listaCitas) {

            citas.add(new DetalleCitaDTO(
                    c.getCodigo(),
                    c.getPaciente().getNombre(),
                    c.getMedico().getNombre(),
                    c.getFechaCreacion(),
                    c.getFechaCita(),
                    c.getMotivo()
            ));
        }
        return citas;
    }

    @Override
    public List<ItemCitaDTO> filtrarCitasMedico(int idPaciente, int idMedico) throws Exception {
        List<Cita> citas = citaRepo.buscarCitasPaciente(idPaciente);
        if(citas.isEmpty()){
            throw new Exception("No tiene citas agendadas " + idPaciente);
        }
        List<Cita> citasMedico = new ArrayList<>();
        for (Cita c : citas){
            if (c.getMedico().getId() == idMedico) {
                citasMedico.add(c);
            }
        }
        return citasMedico.stream().map(ItemCitaDTO::new).toList();
    }
    @Override
    public List<ItemCitaDTO> filtrarCitasPorFecha(LocalDateTime fechaInicio, LocalDateTime fechaFin,
                                                  int codigoPaciente) throws Exception{

        List<Cita> citas = citaRepo.buscarCitasPaciente(codigoPaciente);
        List<ItemCitaDTO> listaCitas = new ArrayList<>();
        for (Cita c : citas) {
            if (c.getFechaCita().isAfter(fechaInicio) && c.getFechaCita().isBefore(fechaFin)) {
                listaCitas.add(new ItemCitaDTO(
                        c.getCodigo(),
                        c.getPaciente().getCedula(),
                        c.getPaciente().getNombre(),
                        c.getMedico().getNombre(),
                        c.getMedico().getEspecialidad(),
                        c.getEstadoCita(),
                        c.getFechaCita()
                ));
            }else {
                throw new Exception("No existen citas en el periodo de tiempo seleccionado");
            }
        }
        return listaCitas;
    }

    @Override
    public List<ItemCitaDTO> listarCitas() throws Exception {
        List<Cita> citas = citaRepo.findAll();
        if(citas.isEmpty()){
            throw new Exception("Tiene vacío el historial de citas y no tiene en agenda");
        }
        return citas.stream()
                .map(ItemCitaDTO::new)
                .toList();
    }
    @Override
    public DetalleCitaDTO verDetalleCita(int id) throws Exception{

        Optional<Cita> optional = citaRepo.findById(id);
        Cita buscado = optional.get();
        return new DetalleCitaDTO(
                buscado.getCodigo(),
                buscado.getPaciente().getNombre(),
                buscado.getMedico().getNombre(),
                buscado.getFechaCreacion(),
                buscado.getFechaCita(),
                buscado.getMotivo()
        );
    }
    @Override
    public List<ItemCitaDTO> listarHistorialPaciente(int codigoPaciente) throws Exception {
        List<Cita> citas = citaRepo.buscarCitasPaciente(codigoPaciente);
        if(citas.isEmpty()){
            throw new ValidationException("No existe historial de citas para el paciente "+codigoPaciente);
        }
        return citas.stream()
                .map(ItemCitaDTO::new)
                .toList();
    }
    @Override
    public List<ItemCitaDTO> listarCitasPendientesPaciente(int codigoPaciente) throws Exception {
        List<Cita> citas = citaRepo.buscarCitasPacienteEstado(EstadoCita.PROGRAMADA, codigoPaciente);
        if (citas.isEmpty()) {
            throw new Exception("No hay citas pendientes");
        }
        return citas.stream().map(ItemCitaDTO::new).toList();
    }
    private boolean estaRepetidaCedula(String cedula) {return pacienteRepo.findByCedula(cedula) != null;}

    private boolean estaRepetidoCorreo(String email) {
        return pacienteRepo.findByCorreo(email) != null;
    }




}
