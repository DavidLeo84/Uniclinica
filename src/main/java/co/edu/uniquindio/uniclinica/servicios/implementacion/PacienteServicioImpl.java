package co.edu.uniquindio.uniclinica.servicios.implementacion;

import co.edu.uniquindio.uniclinica.dto.RespuestaDTO;
import co.edu.uniquindio.uniclinica.dto.administrador.ItemMedicoDTO;
import co.edu.uniquindio.uniclinica.dto.administrador.RegistroMedicoDTO;
import co.edu.uniquindio.uniclinica.dto.administrador.RegistroRespuestaDTO;
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
    private final CitaRepo citaRepo;
    private final HorarioRepo horarioRepo;
    private final Validacion validacion;


    @Override
    public Paciente registrarse(RegistroPacienteDTO pacienteDTO) {

        validacion.validarPaciente(pacienteDTO);

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
    public String editarPerfil(DetallePacienteDTO pacienteDTO) {

        Optional<Paciente> optional = pacienteRepo.findById(pacienteDTO.codigo());

        if (optional.isEmpty()) {
            throw new ResourceNotFoundException("No existe un paciente con el código " + pacienteDTO.codigo());
        }

        Paciente buscado = optional.get();

        buscado.setNombre(pacienteDTO.nombre());
        buscado.setCedula(pacienteDTO.cedula());
        //buscado.setCorreo(pacienteDTO.correo());
        buscado.setCiudad(pacienteDTO.ciudad());
        buscado.setTipoSangre(pacienteDTO.tipoSangre());
        buscado.setTelefono(pacienteDTO.telefono());
        buscado.setUrlFoto(pacienteDTO.urlFoto());

        pacienteRepo.save(buscado);

        return "Ha sido exitoso los cambios realizados";
    }

    @Override
    public DetallePacienteDTO verDetallePaciente(int codigo) throws Exception {
        Optional<Paciente> optional = pacienteRepo.findById(codigo);

        if (optional.isEmpty()) {
            throw new Exception("El usuario no es un paciente registrado");
        }
        Paciente buscado = (Paciente) optional.get();
        return new DetallePacienteDTO(

                buscado.getCedula(),
                buscado.getCiudad(),
                buscado.getNombre(),
                buscado.getTelefono(),
                buscado.getTipoSangre(),
                buscado.getUrlFoto(),
                buscado.getFechaNacimiento(),
                buscado.getAlergias(),
                buscado.getEps(),
                buscado.getId()
                //buscado.getCorreo(),
        );
    }

    @Override
    public void inactivarCuenta(int id) throws Exception {

        Cuenta cuenta = cuentaRepo.findById(id).orElse(null);
        if (cuenta == null) {
            throw new Exception(" no existe en los registros de cuentas de usuarios");
        }
        Optional<Cuenta> optional = cuentaRepo.findById(id);
        List<Cita> citas = citaRepo.buscarCitasMedico(id);
        int contador = 0;
        Cuenta buscado = optional.get();
        if (citas.isEmpty()) {
            throw new Exception("El paciente no tiene historial de citas");
        }
        for (Cita c : citas) {
            if (c.getEstadoCita().equals(EstadoCita.PROGRAMADA)) {
                throw new Exception("No puede cancelar la cuenta porque tiene citas programadas");
            }
            contador++;
        }
        if (contador == 0) {
            buscado.setEstado(EstadoUsuario.INACTIVO);
            cuentaRepo.save(buscado);
        }
    }

    @Override
    public void enviarLinkRecuperacion() throws Exception {

    }

    @Override
    public String cambiarPassword(String passwordActualDTO, String passwordNewDTO,
                                  DetallePacienteDTO pacienteDTO) throws Exception {
        Optional<Paciente> optional = pacienteRepo.findById(pacienteDTO.codigo());

        if (optional.isEmpty()) {
            throw new Exception("No existe un paciente con el id " + pacienteDTO.codigo());
        }

        Paciente buscado = optional.get();

        if (buscado.getPassword().equals(passwordActualDTO) && pacienteDTO.cedula().equals(buscado.getCedula())) {

            buscado.setPassword(passwordNewDTO);

            pacienteRepo.save(buscado);

        } else {
            throw new Exception("La contraseña actual no coincide");
        }
        return "La contraseña se cambió con éxito";
    }

    // Pendiente arreglarlo y enviar correos medico y paciente
    @Override
    public int crearCita(RegistroCitaDTO citaDTO, String cedulaPaciente, LocalDateTime fechaCita,
                         Especialidad especialidad) throws Exception {

        LocalDateTime fechaSistema = LocalDateTime.now();
        List<Cita> citas = citaRepo.findByPacienteCedula(cedulaPaciente);
        List<Medico> medicos = medicoRepo.findAllByEspecialidad(especialidad);
        List<Cita> citasActivas = new ArrayList<>();
        if (medicos.isEmpty()) {
            throw new Exception("No hay médicos registrados con esa especialidad");
        }
        for (Cita c : citas) {
            if (c.getEstadoCita().equals(EstadoCita.PROGRAMADA)) {
                citasActivas.add(c);
            }
        }
        var diferenciaDe30Min = Duration.between(fechaSistema, fechaCita).toMinutes() < 30;
        if (fechaCita.isBefore(fechaSistema)) {
            throw new Exception("Fecha no valida");
        }
        if (citasActivas.size() >= 3) {
            throw new Exception("Ya tiene 3 citas agendadas. No puede agendar por el momento");
        }
        if (diferenciaDe30Min) {
            throw new ValidationException("Las consultas deben programarse con al menos " +
                    "30 minutos de anticipación");
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
                } else {
                    throw new Exception("El médico " + m.getNombre() + " no horario disponible");
                }
            }
        }
        return nuevaCita.getCodigo();
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
    public DetalleCitaDTO modificarCita(int codigoCita) throws Exception {

        Optional<Cita> optional = citaRepo.findById(codigoCita);

        if (optional.isEmpty()) {
            throw new Exception("El codigo de cita no existe");
        }
        Cita buscado = optional.get();

        /*
        buscado.setFechaCita(buscado.fechaCita());
        buscado.setMotivo(citaDTO.motivo());
        buscado.getMedico().setNombre(citaDTO.nombreMedico());
        buscado.getMedico().setEspecialidad(citaDTO.especialidad());
        citaRepo.save(buscado);
        return buscado.getCodigo();
         */
        return new DetalleCitaDTO(
                buscado.getCodigo(),
                buscado.getPaciente().getNombre(),
                buscado.getMedico().getNombre(),
                buscado.getMedico().getEspecialidad(),
                buscado.getFechaCita(),
                buscado.getMotivo()
        );
    }

    @Override
    public String cancelarCita(int codigoCita) throws Exception {

        Optional<Cita> optional = citaRepo.findById(codigoCita);
        Cita buscado = optional.get();

        if (optional.isEmpty()) {
            throw new Exception("El codigo de cita no existe");
        }
            buscado.setEstadoCita(EstadoCita.CANCELADA);
            citaRepo.save(buscado);
        return "La cita con codigo " + codigoCita + " fue cancelada con exito";
    }

    //Falta parte de enviar correo al administrador
    @Override
    public int crearPqrs(RegistroPqrsDTO pqrsDTO) throws Exception {

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
        nuevaPqrs.getCita().setCodigo(pqrsDTO.codigoCita());
        nuevaPqrs.setEstadoPqrs(EstadoPqrs.NUEVO);
        convertirRespuestasPacienteDTO(mensajes);
        pqrsRepo.save(nuevaPqrs);

        return nuevaPqrs.getCodigo();
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
            throw new Exception("No existe un PQRS con el código " + registroRespuestaPacienteDTO.codigoPqrs());
        }

        Optional<Cuenta> opcionalCuenta = cuentaRepo.findById(registroRespuestaPacienteDTO.codigoCuenta());

        if(opcionalCuenta.isEmpty()){
            throw new Exception("No existe una cuenta con el código "+ registroRespuestaPacienteDTO.codigoCuenta());
        }

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

        List<Cita> listaCitas = citaRepo.buscarCitasMedico(id);

        if (listaCitas.isEmpty()) {

            throw new Exception("No existen citas agendadas");
        }

        List<DetalleCitaDTO> citas =new ArrayList<>();

        for (Cita c : listaCitas) {

            citas.add(new DetalleCitaDTO(
                    c.getCodigo(),
                    c.getPaciente().getNombre(),
                    c.getMedico().getNombre(),
                    c.getMedico().getEspecialidad(),
                    c.getFechaCita(),
                    c.getMotivo()
            ));
        }
        return citas;
    }
    @Override
    public List<ItemCitaDTO> filtrarCitasMedico(int idMedico) throws Exception {
        List<Cita> citas = citaRepo.buscarCitasMedico(idMedico);
        if(citas.isEmpty()){
            throw new Exception("No existen citas asociandas al medico "+idMedico);
        }
        return citas.stream().map(ItemCitaDTO::new).toList();
    }
    @Override
    public List<ItemCitaDTO> filtrarCitasPorFecha(LocalDateTime fechaInicio, LocalDateTime fechaFin,
                                                  int codigoPaciente) throws Exception{
        List<Cita> citas = citaRepo.buscarCitasMedico(codigoPaciente);
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
            throw new Exception("No existen citas creadas");
        }
        return citas.stream()
                .map(ItemCitaDTO::new)
                .toList();
    }
    @Override
    public DetalleCitaDTO verDetalleCita(int id) throws Exception{

        Optional<Cita> optional = citaRepo.findById(id);

        if(optional.isEmpty()){
            throw new Exception("No existe una cita con el código "+ id);
        }

        Cita buscado = optional.get();

        return new DetalleCitaDTO(
                buscado.getCodigo(),
                buscado.getPaciente().getNombre(),
                buscado.getMedico().getNombre(),
                buscado.getMedico().getEspecialidad(),
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
        List<Cita> citas = citaRepo.buscarCitasMedicoEstado(EstadoCita.PROGRAMADA, codigoPaciente);
        if (citas.isEmpty()) {
            throw new Exception("No hay citas pendientes");
        }
        return citas.stream().map(ItemCitaDTO::new).toList();

    }
    public void verDetalleCita() throws Exception{

    }

    private boolean estaRepetidaCedula(String cedula) {return pacienteRepo.findByCedula(cedula) != null;}

    private boolean estaRepetidoCorreo(String email) {
        return pacienteRepo.findByCorreo(email) != null;
    }




}
