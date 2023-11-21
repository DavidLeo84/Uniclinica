package co.edu.uniquindio.uniclinica.servicios.implementacion;

import co.edu.uniquindio.uniclinica.dto.RespuestaDTO;
import co.edu.uniquindio.uniclinica.dto.administrador.*;
import co.edu.uniquindio.uniclinica.dto.paciente.*;
import co.edu.uniquindio.uniclinica.dto.paciente.ItemPqrsDTO;
import co.edu.uniquindio.uniclinica.modelo.entidades.*;
import co.edu.uniquindio.uniclinica.modelo.enums.EstadoCita;
import co.edu.uniquindio.uniclinica.modelo.enums.EstadoPqrs;
import co.edu.uniquindio.uniclinica.modelo.enums.EstadoUsuario;
import co.edu.uniquindio.uniclinica.repositorios.*;
import co.edu.uniquindio.uniclinica.servicios.interfaces.AdministradorServicio;
import co.edu.uniquindio.uniclinica.servicios.validaciones.Validacion;
import jakarta.validation.ValidationException;
import co.edu.uniquindio.uniclinica.servicios.validaciones.excepciones.ResourceNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AdministradorServicioImpl implements AdministradorServicio {

    private final PqrsRepo pqrsRepo;
    private final CitaRepo citaRepo;
    private final MensajeRepo mensajeRepo;
    private final MedicoRepo medicoRepo;
    private final PacienteRepo pacienteRepo;
    private final HorarioRepo horarioRepo;
    private final DiaLibreRepo diaLibreRepo;
    private final Validacion validacion;


    @Override
    public Medico crearMedico(RegistroMedicoDTO medicoDTO) throws Exception {

        validacion.validarMedico(medicoDTO);
        Medico medicoNuevo = new Medico();

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String passwordEncriptada = encoder.encode(medicoDTO.password());
        medicoNuevo.setNombre(medicoDTO.nombre());
        medicoNuevo.setCedula(medicoDTO.cedula());
        medicoNuevo.setCiudad(medicoDTO.ciudad());
        medicoNuevo.setEspecialidad(medicoDTO.especialidad());
        medicoNuevo.setTipoSangre(medicoDTO.tipoSangre());
        medicoNuevo.setTelefono(medicoDTO.telefono());
        medicoNuevo.setCorreo(medicoDTO.correo());
        medicoNuevo.setPassword(passwordEncriptada);
        medicoNuevo.setUrlFoto(medicoDTO.urlFoto());
        asignarHorariosMedico(medicoNuevo, medicoDTO.horarios());
        medicoNuevo.setEstado(EstadoUsuario.ACTIVO);
        medicoRepo.save(medicoNuevo);
        System.out.println("Medico " + medicoNuevo.getId() + " creado");

        return medicoNuevo;

    }

    private void asignarHorariosMedico(Medico medicoNuevo, List<HorarioDTO> horarios) {

        for( HorarioDTO h : horarios ){

            HorarioMedico hm = new HorarioMedico();
            //hm.setDia( h.dia() );
            hm.setHoraInicio( h.horaInicio() );
            hm.setHoraSalida( h.horaSalida() );
            hm.setMedico( medicoNuevo );
            horarioRepo.save(hm);
        }
    }
    private Medico validar(int codigo) {
        Optional<Medico> opcional = medicoRepo.findById(codigo);
        if (opcional.isEmpty()) {
            throw new ValidationException("No existe un médico con el código " + codigo);
        }
        return opcional.get();
    }

    @Override
    public Medico actualizarMedico(DetalleMedicoDTO medicoDTO){

        validacion.existenciaMedico(medicoDTO.codigo());

       Optional<Medico> opcional =medicoRepo.findById(medicoDTO.codigo());

        Medico buscado = opcional.get();
        buscado.actualizar(medicoDTO);
        medicoRepo.save(buscado);
        return buscado;

    }

    @Override
    public String eliminarMedico(int idMedico) throws Exception {

        validacion.existenciaMedico(idMedico);
        Optional<Medico> opcional =medicoRepo.findById(idMedico);
        Medico buscado = opcional.get();
        List<Cita> citas = citaRepo.buscarCitasMedico(buscado.getId());
        for (Cita c : citas) {
            if (c.getEstadoCita().equals(EstadoCita.PROGRAMADA)) {
                throw new Exception("El médico " + opcional.get().getNombre() + "tiene citas programadas");
            }
                buscado.setEstado(EstadoUsuario.INACTIVO);
                medicoRepo.save( buscado );
                Optional<DiaLibreMedico> dia = diaLibreRepo.findById(idMedico);
                DiaLibreMedico diaLibre = dia.get();
                if (diaLibre.getFechaLibre().isAfter(LocalDate.now())) {
                    diaLibreRepo.delete(diaLibre);
                }
        }
        return "Médico eliminado con éxito";
    }

    @Override
    public List<ItemMedicoDTO> listarMedicos() throws Exception {
        List<Medico> medicos = medicoRepo.findAll();
        if (medicos.isEmpty()) {
            throw new Exception("No hay médicos registrados");
        }
        return medicos.stream()
                .filter(m -> m.getEstado() == EstadoUsuario.ACTIVO)
                .map(ItemMedicoDTO::new)
                .collect(Collectors.toList());
    }

@Override
public DetalleMedicoDTO obtenerMedico(int codigo) throws Exception {

    Medico medico = validacion.existenciaMedico(codigo);
    if(medico.getEstado()==EstadoUsuario.INACTIVO){
        throw new ValidationException("El Medico esta Inactivo");
    }
    List<HorarioMedico> horarios = horarioRepo.findAllByMedicoCodigo(codigo);
    List<HorarioDTO> horariosDTO = horarios.stream()
            .map(HorarioDTO::new)
            .toList();
    return new DetalleMedicoDTO(medico, horariosDTO);
}

    @Override
    public List<ItemPqrsDTO> listarPqrs() throws Exception{
        List<Pqrs> listaPqrs = pqrsRepo.findAll();
        if (listaPqrs.isEmpty()) {
            throw new Exception("No hay pqrs");
        }
        return listaPqrs.stream()
                .map(ItemPqrsDTO::new)
                .toList();
    }

    @Override
    public DetallePqrsDTO verDetallePqrs(int codigoPqrs) throws Exception {
        Optional<Pqrs> opcional = pqrsRepo.findById(codigoPqrs);
        if (opcional.isEmpty()) {
            throw new Exception("No existe una pqrs con el código " + codigoPqrs);
        }
        Pqrs pqrs = opcional.get();
        List<Mensaje> mensajes = mensajeRepo.findAllByPqrsCodigo(codigoPqrs);

        return new DetallePqrsDTO(pqrs,convertirRespuestasDTO(mensajes));
    }

    private List<RespuestaDTO> convertirRespuestasDTO(List<Mensaje> mensajes) {
        return mensajes.stream().map(m -> new RespuestaDTO(
                m.getCodigo(),
                m.getContenido(),
                m.getCuenta().getCorreo(),
                m.getFechaMensaje()
        )).toList();
    }

    @Override
    public Pqrs responderPqrs(int codigoPqrs, int idAdmin,
                             RegistroRespuestaPacienteDTO registro) throws Exception{

        Optional<Pqrs> opcional = pqrsRepo.findById(codigoPqrs);

        if (opcional.isEmpty()) {
            throw new Exception("No existe una pqrs con el codigo " + codigoPqrs);
        }
        Mensaje mensajeNuevo = new Mensaje();
        mensajeNuevo.setPqrs(opcional.get());
        mensajeNuevo.getCuenta().setId(idAdmin);
        mensajeNuevo.setFechaMensaje( LocalDateTime.now() );
        mensajeNuevo.setContenido(registro.mensaje());

        List<Mensaje> mensajes = new ArrayList<>();
        mensajes.add(mensajeNuevo);

        mensajeRepo.save(mensajeNuevo);
        Pqrs pqrs = opcional.get();
        pqrs.setMensajes(mensajes);
        pqrs.setEstadoPqrs(EstadoPqrs.EN_PROCESO);
        pqrsRepo.save(pqrs);

        return pqrs;
    }
    @Override
    public void cambiarEstadoPqrs(int codigoPqrs, EstadoPqrs nuevoEstado)throws Exception {
        Optional<Pqrs> opcional = pqrsRepo.findById(codigoPqrs);

        if (opcional.isEmpty()) {
            throw new ValidationException("No existe un Pqrs con el código " + codigoPqrs);
        }
        Pqrs pqrs = opcional.get();
        pqrs.setEstadoPqrs(nuevoEstado);
        pqrsRepo.save(pqrs);
    }

    @Override
    public List<ItemCitaDTO> listarCitasMedico(int codigoMedico) throws Exception {
        List<Cita> citas = citaRepo.buscarCitasMedico(codigoMedico);

        if(citas.isEmpty()){
            throw new Exception("No existen citas atendidas por el medico " + codigoMedico);
        }
        List<ItemCitaDTO> listaCitas = new ArrayList<>();

        for (Cita c : citas) {

            listaCitas.add(new ItemCitaDTO(
                    c.getCodigo(),
                    c.getPaciente().getCedula(),
                    c.getPaciente().getNombre(),
                    c.getMedico().getNombre(),
                    c.getMedico().getEspecialidad(),
                    c.getEstadoCita(),
                    c.getFechaCita()
            ));
        }
        return listaCitas;
    }
    @Override
    public List<ItemPacienteDTO> listarPacientes() throws Exception{
        List<Paciente> pacientes = pacienteRepo.findAll();

        if (pacientes.isEmpty()) {
            throw new Exception("No hay pacientes registrados");
        }
        List<ItemPacienteDTO> listaPacientes = new ArrayList<>();

        for (Paciente p : pacientes) {

            listaPacientes.add(new ItemPacienteDTO(
                    p.getId(),
                    p.getCedula(),
                    p.getNombre(),
                    p.getCiudad()
            ));
        }
        return listaPacientes;
    }

    private boolean estaRepetidoCorreo(String correo) {
        return medicoRepo.findByCorreo(correo) != null;
    }

    private boolean estaRepetidaCedula(String cedula) {
        return medicoRepo.findByCedula(cedula) != null;
    }

}