package co.edu.uniquindio.uniclinica.servicios.implementacion;

import co.edu.uniquindio.uniclinica.dto.administrador.ItemMedicoDTO;
import co.edu.uniquindio.uniclinica.dto.administrador.RegistroMedicoDTO;
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

    @Override
    public int registrarse(RegistroPacienteDTO pacienteDTO) throws Exception {

        if( estaRepetidaCedula(pacienteDTO.cedula()) ){
            throw new Exception("La cédula " + pacienteDTO.cedula() + " ya está en uso");
        }

        if( estaRepetidoCorreo(pacienteDTO.correo()) ){
            throw new Exception("El correo " + pacienteDTO.cedula() + " ya está en uso");
        }

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
        paciente.setPassword( passwordEncriptada );

        paciente.setEstado(EstadoUsuario.ACTIVO);

        Paciente pacienteNuevo = pacienteRepo.save(paciente);

        return pacienteNuevo.getId();

    }

    @Override
    public int editarPerfil(DetallePacienteDTO pacienteDTO) throws Exception {

        Optional<Paciente> optional = pacienteRepo.findById(pacienteDTO.codigo());

        if( optional.isEmpty() ){
            throw new Exception("No existe un paciente con el código " + pacienteDTO.codigo());
        }

        Paciente buscado = optional.get();

        buscado.setNombre(pacienteDTO.nombre());
        buscado.setCedula(pacienteDTO.cedula());
        buscado.setCorreo(pacienteDTO.correo());
        buscado.setCiudad(pacienteDTO.ciudad());
        buscado.setTipoSangre(pacienteDTO.tipoSangre());
        buscado.setTelefono(pacienteDTO.telefono());
        buscado.setUrlFoto(pacienteDTO.urlFoto());

        pacienteRepo.save(buscado);

        return buscado.getId();
    }

    @Override
    public DetallePacienteDTO verDetallePaciente(int pacienteDTO) throws Exception {

        Optional<Paciente> optional = pacienteRepo.findById(pacienteDTO);

        if (optional.isEmpty()) {
            throw new Exception("El usuario no es un paciente registrado");
        }
        Paciente buscado = (Paciente) optional.get();


        return new DetallePacienteDTO(
                buscado.getId(),
                buscado.getCedula(),
                buscado.getNombre(),
                buscado.getTelefono(),
                buscado.getCorreo(),
                buscado.getFechaNacimiento(),
                buscado.getUrlFoto(),
                buscado.getCiudad(),
                buscado.getEps(),
                buscado.getTipoSangre(),
                buscado.getAlergias()
        );

    }

    /*  @Override
    public void eliminarCuenta(int id) throws Exception {

         Optional<Paciente> optional = pacienteRepo.findById(id);

         if( optional.isEmpty() ){
             throw new Exception("No existe un médico con el código " + id);
         }
         Paciente buscado =  optional.get();
         buscado.setEstado(EstadoUsuario.INACTIVO);
         pacienteRepo.save(buscado);
     }
 */
    @Override
    public void enviarLinkRecuperacion() throws Exception{

    }

    @Override
    public String cambiarPassword(String passwordActualDTO, String passwordNewDTO,
                                  DetallePacienteDTO pacienteDTO) throws Exception {


        String message = "";
        Optional<Paciente> optional = pacienteRepo.findById(pacienteDTO.codigo());

        if( optional.isEmpty() ){
            throw new Exception("No existe un paciente con el id " + pacienteDTO.codigo());
        }

        Paciente buscado = optional.get();

        if (buscado.getPassword().equals(passwordActualDTO) && pacienteDTO.cedula().equals(buscado.getCedula())) {

            buscado.setPassword(passwordNewDTO);

            pacienteRepo.save(buscado);

        }else{
            throw new Exception("La contraseña actual no coincide");
        }
        return "La contraseña se cambió con éxito";
    }

    // Pendiente arreglarlo y enviar correos medico y paciente
    @Override
    public int crearCita(DetalleMedicoCitaDTO medicoDTO, RegistroCitaDTO citaDTO,
                         DetallePacienteDTO pacienteDTO, LocalDateTime fechaCita,
                         Especialidad especialidad) throws Exception {

        LocalDateTime fechaSistema = LocalDateTime.now();

        List<Cita> citas =  citaRepo.findByPacienteCedula(pacienteDTO.cedula());

        List<Medico> medicos = medicoRepo.findAllByEspecialidad(especialidad);
        List<Cita> citasActivas = new ArrayList<>();

        if (medicos.isEmpty()) {
            throw new Exception("No hay médicos registrados con esa especialidad");
        }

        for (Cita c: citas) {
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

        for (Medico m : medicos ) {

            List<Cita> citasMedico = citaRepo.findByMedico_Id(m.getId());

            for (Cita c : citasMedico) {
                if (c.getFechaCita().equals(fechaCita)) {
                    throw new Exception("El horario" + fechaCita + " ya está ocupado");
                }
            }
            List<HorarioMedico> horarios = horarioRepo.findByMedico_Id(m.getId());

            for (HorarioMedico hm : horarios) {

                if (hm.getDia().equals(fechaCita.getDayOfWeek().name()) &&
                        hm.getHoraInicio().minusMinutes(1).isAfter(LocalDateTime.from(fechaCita))
                        && hm.getHoraSalida().plusMinutes(1).isAfter(LocalDateTime.from(fechaCita)) )
                {
                    nuevaCita.setFechaCreacion(LocalDateTime.now());
                    nuevaCita.setFechaCita(fechaCita);
                    nuevaCita.setMotivo(citaDTO.motivo());
                    nuevaCita.getPaciente().setNombre(citaDTO.nombrePaciente());
                    nuevaCita.getMedico().setNombre(citaDTO.nombreMedico());
                    nuevaCita.setEstadoCita(EstadoCita.PROGRAMADA);
                    citaRepo.save(nuevaCita);
                    horarioRepo.save(hm);

                }else {
                    throw new Exception("El médico " + m.getNombre() +" no horario disponible");
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
    public int modificarCita(DetalleCitaDTO citaDTO) throws Exception {

        Optional<Cita> optional = citaRepo.findById(citaDTO.codigo());

        if (optional.isEmpty()) {
            throw new Exception("El codigo de cita no existe");
        }

        Cita buscado = optional.get();

        buscado.setFechaCita(citaDTO.fechaCita());
        buscado.setMotivo(citaDTO.motivo());
        buscado.getMedico().setNombre(citaDTO.nombreMedico());
        buscado.getMedico().setEspecialidad(citaDTO.especialidad());
        citaRepo.save(buscado);

        return buscado.getCodigo();

    }

    @Override
    public void cancelarCita(DetalleCitaDTO citaDTO) throws Exception {

        Optional<Cita> optional = citaRepo.findById(citaDTO.codigo());
        Cita buscado = optional.get();

        if (optional.isEmpty()) {
            throw new Exception("El codigo de cita no existe");
        }else {
            buscado.setEstadoCita(EstadoCita.CANCELADA);
            citaRepo.save(buscado);
        }
    }

    //Falta parte de enviar correo al administrador
    @Override
    public void crearPqrs(RegistroPqrsDTO pqrsDTO, DetallePacienteDTO pacienteDTO) throws Exception{

        List<Mensaje> mensajes = new ArrayList<>();
        List<Pqrs> pqrs = pqrsRepo.findAllById(pacienteDTO.codigo());

        List<Pqrs> prqsEnProceso = new ArrayList<>();

        for (Pqrs pq : pqrs) {
            if (pq.getEstadoPqrs().equals(EstadoPqrs.NUEVO)) {
                prqsEnProceso.add(pq);
            }
        }
        if (prqsEnProceso.size() < 4) {
            for (Pqrs p : pqrs) {
                Pqrs nuevaPqrs = new Pqrs();
                nuevaPqrs.setFechaCreacion(LocalDateTime.now());
                nuevaPqrs.setMotivo(pqrsDTO.motivo());
                nuevaPqrs.getCita().setCodigo(pqrsDTO.codigoCita());
                nuevaPqrs.getPaciente().setNombre(pqrsDTO.nombrePaciente());
                nuevaPqrs.setEstadoPqrs(EstadoPqrs.NUEVO);
                convertirRespuestasPacienteDTO(mensajes);
                pqrsRepo.save(nuevaPqrs);
            }
        }else{
            throw new Exception("Ya tiene 3 pqrs en proceso. No puede crear mas por el momento");
        }
    }

    private List<RespuestaPacienteDTO> convertirRespuestasPacienteDTO(List<Mensaje> mensajes) {

        return mensajes.stream().map(m -> new RespuestaPacienteDTO(
                m.getCodigo(),
                m.getContenido(),
                m.getCuenta().getCorreo(),
                m.getFechaMensaje()
        )).toList();
    }

    @Override
    public List<ItemPqrsPacienteDTO> listarPqrsPaciente(int id) throws Exception{

        List<Pqrs> pqrs =  pqrsRepo.findAllById(id);
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

    //Hay que arreglar
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

    @Override
    public List<DetalleCitaDTO> listarCitasPaciente(int id) throws Exception{

        List<Cita> listaCitas = citaRepo.findAllById(id);

        if (listaCitas.isEmpty()) {

            throw new Exception("No existen citas agendadas");
        }

        List<DetalleCitaDTO> citas =new ArrayList<>();

        for (Cita c : listaCitas) {

            citas.add(new DetalleCitaDTO(
                    c.getCodigo(),
                    c.getFechaCita(),
                    c.getMotivo(),
                    c.getEstadoCita(),
                    c.getMedico().getNombre(),
                    c.getMedico().getEspecialidad()
            ));
        }
        return citas;
    }

    public List<ItemPacienteDTO> listarPacientes() throws Exception{

        List<Paciente> lista = pacienteRepo.findAll();

        if (lista.isEmpty()) {
            throw new Exception("No hay pacientes registrados");
        }

        List<ItemPacienteDTO> respuesta = new ArrayList<>();

        for (Paciente p : lista) {

            respuesta.add(new ItemPacienteDTO(

                    p.getId(),
                    p.getCedula(),
                    p.getNombre(),
                    p.getCiudad()
            ));
        }
        return respuesta;
    }

    @Override
    public List<ItemCitaDTO> filtrarCitasPorFecha(LocalDateTime fechaInicio,
                                                  LocalDateTime fechaFin,
                                                  DetallePacienteDTO pacienteDTO) throws Exception{

        List<Cita> citas = citaRepo.findAllById(pacienteDTO.codigo());

        List<ItemCitaDTO> listaCitas = new ArrayList<>();

        for (Cita c : citas) {

            if (c.getFechaCita().isAfter(fechaInicio) && c.getFechaCita().isBefore(fechaFin)) {

                listaCitas.add(new ItemCitaDTO(
                        c.getCodigo(),
                        c.getPaciente().getCedula(),
                        c.getPaciente().getNombre(),
                        c.getMotivo(),
                        c.getFechaCita()

                ));

            }else {
                throw new Exception("No existen citas en el periodo de tiempo seleccionado");
            }
        }
        return listaCitas;
    }

    @Override
    public List<ItemCitaDTO> filtrarCitasPorMedico(String nombreMedico) throws Exception{

        List<Cita> citas = citaRepo.findAllByMedicoNombre(nombreMedico);

        if (citas.isEmpty()) {
            throw new Exception("No existen citas con el medico asociado");
        }else {

            List<ItemCitaDTO> listaCitas = new ArrayList<>();

            for (Cita c : citas) {

                listaCitas.add(new ItemCitaDTO(
                        c.getCodigo(),
                        c.getPaciente().getCedula(),
                        c.getPaciente().getNombre(),
                        c.getMotivo(),
                        c.getFechaCita()
                ));
            }
            return listaCitas;
        }
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
                buscado.getFechaCita(),
                buscado.getMotivo(),
                buscado.getEstadoCita(),
                buscado.getMedico().getNombre(),
                buscado.getMedico().getEspecialidad()
        );

    }

    @Override
    public void inactivarCuenta(int id) throws Exception {

        if (validarCuenta(id)) {
            throw new Exception(" no existe en los registros de cuentas de usuarios");
        }

        Optional<Cuenta>  optional = cuentaRepo.findById(id);
        List<Cita> citas = citaRepo.findAllById(id);
        int contador = 0;
        Cuenta buscado = optional.get();

        if (citas.isEmpty()){
            throw new Exception("El paciente no tiene historial de citas");
        }

        for (Cita c : citas) {

            if (c.getEstadoCita().equals(EstadoCita.PROGRAMADA)) {

                throw new Exception("No puede cancelar la cuenta porque tiene citas programadas");
                //break;
            }
            contador++;
        }

        if (contador == 0) {
            buscado.setEstado(EstadoUsuario.INACTIVO);
            cuentaRepo.save(buscado);
        }
    }
    private boolean estaRepetidaCedula(String cedula) {return pacienteRepo.findByCedula(cedula) != null;}

    private boolean estaRepetidoCorreo(String email) {
        return pacienteRepo.findByCorreo(email) != null;
    }

    private boolean validarCuenta(int id){
        return cuentaRepo.findById(id) != null;
    }

}
