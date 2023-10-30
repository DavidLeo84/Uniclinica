package co.edu.uniquindio.uniclinica.servicios.implementacion;

import co.edu.uniquindio.uniclinica.dto.RespuestaDTO;
import co.edu.uniquindio.uniclinica.dto.administrador.*;
import co.edu.uniquindio.uniclinica.dto.paciente.ItemPqrsDTO;
import co.edu.uniquindio.uniclinica.modelo.entidades.*;
import co.edu.uniquindio.uniclinica.modelo.enums.EstadoCita;
import co.edu.uniquindio.uniclinica.modelo.enums.EstadoPqrs;
import co.edu.uniquindio.uniclinica.modelo.enums.EstadoUsuario;
import co.edu.uniquindio.uniclinica.repositorios.*;
import co.edu.uniquindio.uniclinica.servicios.interfaces.AdministradorServicio;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdministradorServicioImpl implements AdministradorServicio {

    private final MedicoRepo medicoRepo;
    private final PqrsRepo pqrsRepo;
    private final CuentaRepo cuentaRepo;
    private final MensajeRepo mensajeRepo;
    private final CitaRepo citaRepo;
    private final HorarioRepo horarioRepo;
    private final DiaLibreRepo diaLibreRepo;

    @Override
    public int crearMedico(RegistroMedicoDTO medicoDTO) throws Exception {

        if( estaRepetidaCedula(medicoDTO.cedula()) ){
            throw new Exception("La cédula " + medicoDTO.cedula() + " ya está en uso");
        }
        if( estaRepetidoCorreo(medicoDTO.correo()) ){
            throw new Exception("El correo " + medicoDTO.cedula() + " ya está en uso");
        }
        Medico medicoNuevo = new Medico();

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String passwordEncriptada = encoder.encode( medicoDTO.password());

        medicoNuevo.getId();
        medicoNuevo.setNombre(medicoDTO.nombre());
        medicoNuevo.setCedula(medicoDTO.cedula());
        medicoNuevo.setCiudad(medicoDTO.ciudad());
        medicoNuevo.setEspecialidad( medicoDTO.especialidad() );
        medicoNuevo.setTelefono(medicoDTO.telefono());
        medicoNuevo.setCorreo(medicoDTO.correo() );
        medicoNuevo.setPassword(passwordEncriptada);
        medicoNuevo.setUrlFoto(medicoDTO.urlFoto());
        asignarHorariosMedico(medicoNuevo, medicoDTO.horarios());
        medicoNuevo.setEstado(EstadoUsuario.ACTIVO);
        medicoRepo.save(medicoNuevo);

        return medicoNuevo.getId();

        //BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
       // String passwordEncriptada = encoder.encode( medicoDTO.password());

        //Medico medicoNuevo = medicoRepo.save(new Medico(medicoDTO, passwordEncriptada));
        //asignarHorariosMedico(medicoNuevo, medicoDTO.horarios());
        //System.out.println("Medico " + medicoNuevo.getId() + " creado");

        //return medicoNuevo.getId();
    }

    private void asignarHorariosMedico(Medico medicoNuevo, List<HorarioDTO> horarios) {

        for( HorarioDTO h : horarios ){

            HorarioMedico hm = new HorarioMedico();
            hm.setDia( h.dia() );
            hm.setHoraInicio( h.horaInicio() );
            hm.setHoraSalida( h.horaSalida() );
            hm.setMedico( medicoNuevo );
            horarioRepo.save(hm);
        }
    }
    @Override
    public int actualizarMedico(DetalleMedicoDTO medicoDTO) throws Exception {

        Optional<Medico> opcional =medicoRepo.findById(medicoDTO.codigo());

        if( opcional.isEmpty() ){
            throw new Exception("No existe un médico con el código "+medicoDTO.codigo());
        }

        Medico buscado = opcional.get();

        buscado.setCedula(medicoDTO.cedula() );
        buscado.setTelefono(medicoDTO.telefono());
        buscado.setNombre(medicoDTO.nombre() );
        buscado.setEspecialidad( medicoDTO.especialidad() );
        buscado.setCiudad(medicoDTO.ciudad());
        buscado.setCorreo(medicoDTO.correo() );
        buscado.setUrlFoto(medicoDTO.urlFoto());
        medicoRepo.save( buscado );

        return buscado.getId();
    }

    @Override
    public String eliminarMedico(DetalleMedicoDTO medicoDTO) throws Exception {

        Optional<Medico> opcional =medicoRepo.findById(medicoDTO.codigo());

        if( opcional.isEmpty() ){
            throw new Exception("No existe un médico con el código "+ medicoDTO.codigo());
        }

        List<Cita> citas = citaRepo.findAllById(medicoDTO.codigo());

        if (citas.isEmpty()) {
            throw new Exception("El médico " + medicoDTO.nombre() + "no tiene historial de citas");
        }

        for (Cita c : citas) {

            if (c.getEstadoCita().equals(EstadoCita.PROGRAMADA)) {

                throw new Exception("El médico " + medicoDTO.nombre() + "tiene citas programadas");
            }else {

                Medico buscado = opcional.get();
                buscado.setEstado(EstadoUsuario.INACTIVO);
                medicoRepo.save( buscado );

                Optional<DiaLibreMedico> dia = diaLibreRepo.findById(medicoDTO.codigo());

                DiaLibreMedico diaLibre = dia.get();

                if (diaLibre.getFechaLibre().isBefore(LocalDateTime.now())) {

                    diaLibreRepo.delete(diaLibre);
                }
            }
        }
        return "Médico eliminado con éxito";
    }

    @Override
    public List<ItemMedicoDTO> listarMedicos() throws Exception {

        List<Medico> medicos = medicoRepo.findAllByEstadoActivo(EstadoUsuario.ACTIVO);

        if(medicos.isEmpty()){
            throw new Exception("No hay médicos registrados");
        }

        List<ItemMedicoDTO> activos = new ArrayList<>();

        for (Medico m : medicos) {

            if (m.getEstado().equals(EstadoUsuario.ACTIVO)) {

                activos.add(new ItemMedicoDTO(
                        m.getId(),
                        m.getCedula(),
                        m.getNombre(),
                        m.getUrlFoto(),
                        m.getEspecialidad())
                );
            }
        }
        return activos;

        /*List<ItemMedicoDTO> respuesta = medicos.stream().map( m -> new ItemMedicoDTO(
                m.getCodigo(),
                m.getCedula(),
                m.getNombre(),
                m.getUrlFoto(),
                m.getEspecialidad()
        ) ).toList();
        return respuesta;

         */
    }

    @Override
    public DetalleMedicoDTO obtenerMedico(DetalleMedicoDTO medicoDTO) throws Exception {

        Optional<Medico> opcional = medicoRepo.findById(medicoDTO.codigo());
        Optional<DiaLibreMedico> optional = diaLibreRepo.findById(medicoDTO.codigo());

        if( opcional.isEmpty() ){
            throw new Exception("No existe un médico con el código "+ medicoDTO.codigo());
        }

        Medico buscado = opcional.get();
        DiaLibreMedico diaBuscado = optional.get();

        List<HorarioMedico> horarios = horarioRepo.findAllByMedicoCodigo(medicoDTO.codigo());

        List<HorarioDTO> horariosDTO = new ArrayList<>();

        for( HorarioMedico h : horarios ){
            horariosDTO.add( new HorarioDTO(
                    h.getDia(),
                    h.getHoraInicio(),
                    h.getHoraSalida(),
                    h.getMedico()
            ) );
        }
        return new DetalleMedicoDTO(
                buscado.getId(),
                buscado.getNombre(),
                buscado.getCedula(),
           buscado.getCiudad(),
           buscado.getEspecialidad(),
           buscado.getTelefono(),
           buscado.getCorreo(),
           buscado.getUrlFoto(),
           horarios,
           buscado.getDiaLibre()
        );
    }

    @Override
    public List<ItemPqrsDTO> listarPqrs() throws Exception {

        List<Pqrs> listaPqrs = pqrsRepo.findAll();
        List<ItemPqrsDTO> respuesta = new ArrayList<>();

        for( Pqrs p: listaPqrs ){

            respuesta.add( new ItemPqrsDTO(
                    p.getCodigo(),
                    p.getEstadoPqrs(),
                    p.getFechaCreacion(),
                    p.getMotivo(),
                    p.getCita().getPaciente().getNombre()
            ) );

        }

        return respuesta;
    }

    @Override
    public DetallePqrsDTO verDetallePqrs(DetallePqrsDTO pqrsDTO) throws Exception {

        Optional<Pqrs> opcional = pqrsRepo.findById(pqrsDTO.codigoPqrs());

        if(opcional.isEmpty()){
            throw new Exception("No existe un pqrs con el código " + pqrsDTO.codigoPqrs());
        }

        Pqrs buscado = opcional.get();
        List<Mensaje> mensajes = mensajeRepo.findAllByPqrsCodigo(pqrsDTO.codigoPqrs());

        return new DetallePqrsDTO(
                buscado.getCodigo(),
                buscado.getFechaCreacion(),
                buscado.getMotivo(),
                buscado.getCita().getCodigo(),
                buscado.getCita().getPaciente().getNombre(),
                buscado.getEstadoPqrs(),
                convertirRespuestasDTO(mensajes)
        );
    }

    private List<RespuestaDTO> convertirRespuestasDTO(List<Mensaje> mensajes) {
        return mensajes.stream().map(m -> new RespuestaDTO(
                m.getCodigo(),
                m.getContenido(),
                m.getCuenta().getCorreo(),
                m.getFechaMensaje(),
                m.getContenido()
        )).toList();
    }

    @Override
    public int responderPqrs(DetallePqrsDTO pqrsDTO, DetalleAdministradorDTO adminDTO,
                             RegistroRespuestaDTO registroRespuestaDTO) throws Exception{

        Optional<Pqrs> opcional = pqrsRepo.findById(pqrsDTO.codigoPqrs());

        if (opcional.isEmpty()) {
            throw new Exception("El paciente " +pqrsDTO.nombrePaciente() + " no tiene pqrs");
        }

        Mensaje mensajeNuevo = new Mensaje();
        mensajeNuevo.setPqrs(opcional.get());
        mensajeNuevo.getCuenta().setId(adminDTO.id());
        mensajeNuevo.setFechaMensaje( LocalDateTime.now() );
        mensajeNuevo.setContenido(registroRespuestaDTO.mensaje());

        Mensaje respuesta = mensajeRepo.save(mensajeNuevo);
        Pqrs pqrs = opcional.get();
        pqrs.setEstadoPqrs(EstadoPqrs.EN_PROCESO);
        pqrsRepo.save(pqrs);

        return respuesta.getCodigo();

    }

    @Override
    public void cambiarEstadoPqrs(int codigoPqrs, EstadoPqrs estadoPqrs) throws Exception {

        Optional<Pqrs> opcional = pqrsRepo.findById(codigoPqrs);
        Pqrs pqrs = opcional.get();

        if( opcional.isEmpty() ){
            throw new Exception("No existe un PQRS con el código " + codigoPqrs);
        }
        pqrs.setEstadoPqrs(estadoPqrs);
        pqrsRepo.save(pqrs);
    }

    @Override
    public List<ItemCitaAdminDTO> listarCitasMedico(DetalleMedicoDTO medicoDTO) throws Exception {

        List<Cita> citas = citaRepo.findAllByMedicoNombre(medicoDTO.nombre());
        List<ItemCitaAdminDTO> respuesta = new ArrayList<>();

        if(citas.isEmpty()){
            throw new Exception("No existen citas creadas al medico " + medicoDTO.nombre());
        }

        for( Cita c : citas ){
            respuesta.add( new ItemCitaAdminDTO(
                    c.getCodigo(),
                    c.getPaciente().getCedula(),
                    c.getPaciente().getNombre(),
                    c.getMedico().getNombre(),
                    c.getMedico().getEspecialidad(),
                    c.getEstadoCita(),
                    c.getFechaCita()
            ) );
        }

        return respuesta;
    }
    private boolean estaRepetidoCorreo(String correo) {
        return medicoRepo.findByCorreo(correo) != null;
    }

    private boolean estaRepetidaCedula(String cedula) {
        return medicoRepo.findByCedula(cedula) != null;
    }
}