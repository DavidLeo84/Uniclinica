package co.edu.uniquindio.uniclinica.servicios.implementacion;

import co.edu.uniquindio.uniclinica.dto.administrador.DetalleMedicoDTO;
import co.edu.uniquindio.uniclinica.dto.medico.DiaLibreDTO;
import co.edu.uniquindio.uniclinica.dto.medico.ItemAtencionDTO;
import co.edu.uniquindio.uniclinica.dto.medico.RegistroAtencionDTO;
import co.edu.uniquindio.uniclinica.dto.paciente.DetallePacienteDTO;
import co.edu.uniquindio.uniclinica.dto.paciente.ItemCitaDTO;
import co.edu.uniquindio.uniclinica.modelo.entidades.Atencion;
import co.edu.uniquindio.uniclinica.modelo.entidades.Cita;
import co.edu.uniquindio.uniclinica.modelo.entidades.DiaLibreMedico;
import co.edu.uniquindio.uniclinica.modelo.enums.EstadoCita;
import co.edu.uniquindio.uniclinica.repositorios.AtencionRepo;
import co.edu.uniquindio.uniclinica.repositorios.CitaRepo;
import co.edu.uniquindio.uniclinica.repositorios.DiaLibreRepo;
import co.edu.uniquindio.uniclinica.servicios.interfaces.MedicoServicio;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MedicoServicioImpl implements MedicoServicio {

    private final CitaRepo citaRepo;
    private final AtencionRepo atencionRepo;
    private final DiaLibreRepo diaLibreRepo;

    @Override
    public List<ItemCitaDTO> listarCitasPendientes(DetalleMedicoDTO medicoDTO) throws Exception {

        List<Cita> citas = citaRepo.findByMedico_Id(medicoDTO.codigo());

        if (citas.isEmpty()) {
            throw new Exception("No tiene citas");
        }

        List<ItemCitaDTO> citasPendientes = new ArrayList<>();

        for (Cita c : citas) {
            if (c.getEstadoCita().equals(EstadoCita.PROGRAMADA)) {
                citasPendientes.add(new ItemCitaDTO(
                        c.getCodigo(),
                        c.getPaciente().getCedula(),
                        c.getPaciente().getNombre(),
                        c.getMotivo(),
                        c.getFechaCita()
                ));
            }
        }
        return citasPendientes;
    }

    @Override
    public List<ItemCitaDTO> listarCitasPendientesDia(DetalleMedicoDTO medicoDTO) throws Exception {

        LocalDateTime fechaSistema = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy MMMM dd");
        String fechaAhora = formato.format(fechaSistema);

        List<Cita> citas = citaRepo.findByMedico_Id(medicoDTO.codigo());

        if (citas.isEmpty()) {
            throw new Exception("No tiene citas");
        }

        List<ItemCitaDTO> citasPendientes = new ArrayList<>();

        for (Cita c : citas) {
            if (c.getFechaCita().equals(fechaAhora)) {
                citasPendientes.add(new ItemCitaDTO(
                        c.getCodigo(),
                        c.getPaciente().getCedula(),
                        c.getPaciente().getNombre(),
                        c.getMotivo(),
                        c.getFechaCita()
                ));
            }
        }
        return citasPendientes;
    }

    @Override
    public void atenderCita(DetallePacienteDTO pacienteDTO, DetalleMedicoDTO medicoDTO,
                            RegistroAtencionDTO atencionDTO) throws Exception{

        List<Cita> citas = citaRepo.findByPacienteCedula(pacienteDTO.cedula());

        if (citas.isEmpty()) {
            throw new Exception("No existe citas agendadas con el paciente " + pacienteDTO.nombre());
        }
        for (Cita c : citas) {

            if (c.getFechaCita().equals(LocalDateTime.now()) && c.getMedico().getId() == medicoDTO.codigo()) {

                Atencion atencion = new Atencion();
                atencion.setDiagnostico(atencionDTO.diagnostico());
                atencion.setTratamiento(atencionDTO.tratamiento());
                atencion.setNotasMedicas(atencionDTO.notasMedicas());
                atencion.setCita(c);
                atencionRepo.save(atencion);
            }

        }
    }

    public List<ItemAtencionDTO> listarAtencionesPaciente(DetallePacienteDTO pacienteDTO) throws Exception {

        List<Atencion> atenciones = atencionRepo.findAllByCita_Paciente_Cedula(pacienteDTO.cedula());

        if (atenciones.isEmpty()) {
            throw new Exception("El paciente " + pacienteDTO.nombre() + "no tiene historial clinico");
        }
        List<ItemAtencionDTO> atencion = new ArrayList<>();

        for (Atencion a : atenciones) {

            atencion.add(new ItemAtencionDTO(
                    a.getId(),
                    a.getDiagnostico(),
                    a.getTratamiento(),
                    a.getNotasMedicas(),
                    a.getCita().getCodigo()
            ));
        }

        return atencion;

    }

    @Override
    public LocalDateTime agendarDiaLibre(DetalleMedicoDTO medicoDTO, DiaLibreDTO fechaLibreDTO) throws Exception{

        LocalDateTime fechaSistema = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy MMMM dd, HH:mm");
        String fecha = formato.format(fechaLibreDTO.fecha());

        if (fechaLibreDTO.fecha().isBefore(fechaSistema)) {
            throw new Exception("Fecha invalida");
        }
        List<Cita> citas = citaRepo.findByMedico_Id(medicoDTO.codigo());

        if (citas.isEmpty()) {
            throw new Exception("No tiene citas agendadas");
        }
        for (Cita c : citas) {

            String fechaCita = formato.format(c.getFechaCita());
            if (fecha.equals(fechaCita)) {
                throw new Exception("Tiene citas agendadas en esa fecha");
            }else {
                DiaLibreMedico diaLibreMedico = new DiaLibreMedico();
                diaLibreMedico.setFechaLibre(fechaLibreDTO.fecha());
                diaLibreRepo.save(diaLibreMedico);
            }
        }
        return fechaLibreDTO.fecha();
    }

    public LocalDateTime modificarDiaLibre(DetalleMedicoDTO medicoDTO, LocalDateTime nuevoDiaLibre) throws Exception {

        Optional<DiaLibreMedico> encontrado = diaLibreRepo.findById(medicoDTO.codigo());

        if (encontrado.isEmpty()) {
            throw new Exception("El médico " + medicoDTO.nombre() + " no tiene fecha de día libre agendado");
        }

        DiaLibreMedico nuevo = encontrado.get();
        nuevo.setFechaLibre(nuevoDiaLibre);
        diaLibreRepo.save(nuevo);

        return nuevo.getFechaLibre();
    }

    private boolean buscarDiaLibre(int codigo) throws Exception {return diaLibreRepo.findById(codigo) != null;}

    public String cancelarDiaLibre(DetalleMedicoDTO medicoDTO) throws Exception{

        if(buscarDiaLibre(medicoDTO.codigo())) {
            throw new Exception("El médico " + medicoDTO.nombre() + " no tiene fecha de día libre agendado");
        }
        List<DiaLibreMedico> dias = diaLibreRepo.findByCodigo(medicoDTO.codigo());

        for ( DiaLibreMedico dm : dias) {

            if (dm.equals(medicoDTO.diaLibre())) {
                dias.remove(dm);
            }
            diaLibreRepo.delete(dm);
        }
        return "El dia libre" + medicoDTO.diaLibre() + " agendado fue cancelado con éxito";
    }

    @Override
    public List<ItemCitaDTO> listarCitasRealizadasMedico(DetalleMedicoDTO medicoDTO) throws Exception{

        List<Cita> citas = citaRepo.findAllById(medicoDTO.codigo());

        if (citas.isEmpty()) {
            throw new Exception("No hay citas que mostrar");
        }

        List<ItemCitaDTO> lista = new ArrayList<>();

        for (Cita c : citas) {

            lista.add(new ItemCitaDTO(
                    c.getCodigo(),
                    c.getPaciente().getCedula(),
                    c.getPaciente().getNombre(),
                    c.getMotivo(),
                    c.getFechaCita()
            ));
        }
        return lista;
    }
}