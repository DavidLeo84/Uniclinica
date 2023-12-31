package co.edu.uniquindio.uniclinica.servicios.implementacion;

import co.edu.uniquindio.uniclinica.dto.administrador.DetalleMedicoDTO;
import co.edu.uniquindio.uniclinica.dto.medico.DiaLibreDTO;
import co.edu.uniquindio.uniclinica.dto.medico.ItemAtencionDTO;
import co.edu.uniquindio.uniclinica.dto.medico.RegistroAtencionDTO;
import co.edu.uniquindio.uniclinica.dto.paciente.DetalleAtencionDTO;
import co.edu.uniquindio.uniclinica.dto.paciente.DetallePacienteDTO;
import co.edu.uniquindio.uniclinica.dto.paciente.ItemCitaDTO;
import co.edu.uniquindio.uniclinica.modelo.entidades.*;
import co.edu.uniquindio.uniclinica.modelo.enums.EstadoCita;
import co.edu.uniquindio.uniclinica.modelo.enums.EstadoUsuario;
import co.edu.uniquindio.uniclinica.repositorios.AtencionRepo;
import co.edu.uniquindio.uniclinica.repositorios.CitaRepo;
import co.edu.uniquindio.uniclinica.repositorios.DiaLibreRepo;
import co.edu.uniquindio.uniclinica.repositorios.PacienteRepo;
import co.edu.uniquindio.uniclinica.servicios.interfaces.MedicoServicio;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    private final PacienteRepo pacienteRepo;


    @Override
    public List<ItemCitaDTO> listarCitasPendientes(int idMedico) throws Exception {

        List<Cita> citas = citaRepo.buscarCitasMedico(idMedico);

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
                        c.getMedico().getNombre(),
                        c.getMedico().getEspecialidad(),
                        c.getEstadoCita(),
                        c.getFechaCita()
                ));
            }
        }
        return citasPendientes;
    }

    @Override
    public List<ItemCitaDTO> listarCitasPendientesDia(int idMedico) throws Exception {

        LocalDateTime fechaHoy = LocalDateTime.now();
        List<Cita> citas = citaRepo.buscarCitasMedico(idMedico);
        if (citas.isEmpty()) {
            throw new Exception("No tiene citas");
        }
        List<ItemCitaDTO> citasPendientesDia = new ArrayList<>();
        for (Cita c : citas) {
            if (c.getFechaCita().isAfter(fechaHoy)) {
                citasPendientesDia.add(new ItemCitaDTO(
                        c.getCodigo(),
                        c.getPaciente().getCedula(),
                        c.getPaciente().getNombre(),
                        c.getMedico().getNombre(),
                        c.getMedico().getEspecialidad(),
                        c.getEstadoCita(),
                        c.getFechaCita()
                ));
            }
        }
        return citasPendientesDia;
    }

    @Override
    public RegistroAtencionDTO atenderCita(String cedulaPaciente, int idMedico) throws Exception {

        String diagnostico = "";
        String tratamiento = "";
        String notasMedicas = "";
        float precio = 0;

        List<Cita> citas = citaRepo.buscarCitasMedico(idMedico);
        List<Cita> citasDia = new ArrayList<>();
        for (Cita c : citas) {
            citasDia.add(c);
        }
        if (citasDia.isEmpty()) {
            throw new Exception("No tiene citas agendadas para el día de hoy");
        }
        Cita buscada = new Cita();
        Atencion nueva = new Atencion();
        for (Cita ct : citasDia) {
            if (!LocalDateTime.now().equals(ct.getFechaCita())) {
                throw new Exception("El paciente no tiene cita asignada hoy");
            } else {
                buscada.setEstadoCita(EstadoCita.COMPLETADA);
                citaRepo.save(buscada);
                nueva.setDiagnostico(diagnostico);
                nueva.setTratamiento(tratamiento);
                nueva.setNotasMedicas(notasMedicas);
                nueva.setPrecio(precio);
                atencionRepo.save(nueva);
            }
        }
        return new RegistroAtencionDTO(
                nueva.getCita().getCodigo(),
                nueva.getDiagnostico(),
                nueva.getTratamiento(),
                nueva.getNotasMedicas(),
                nueva.getPrecio()
        );
    }

    @Override
    public List<ItemAtencionDTO> listarAtencionesPaciente(int idPaciente) throws Exception {

        List<Atencion> atenciones = atencionRepo.findAllByCita_Paciente_id(idPaciente);
        if (atenciones.isEmpty()) {
            throw new Exception("El paciente con id " + idPaciente + " no tiene historial clinico");
        }
        List<ItemAtencionDTO> atencion = new ArrayList<>();
        for (Atencion a : atenciones) {

            atencion.add(new ItemAtencionDTO(
                    a.getCodigo(),
                    a.getDiagnostico(),
                    a.getTratamiento(),
                    a.getNotasMedicas(),
                    a.getCita().getCodigo()
            ));
        }
        return atencion;
    }

    @Override
    public List<ItemCitaDTO> listarCitasRealizadasMedico(int idMedico) throws Exception{

        List<Cita> citas = citaRepo.buscarCitasMedico(idMedico);
        if (citas.isEmpty()) {
            throw new Exception("No hay citas que mostrar");
        }
        List<ItemCitaDTO> lista = new ArrayList<>();

        for (Cita c : citas) {

            if (c.getFechaCita().isBefore(LocalDateTime.now())) {
                lista.add(new ItemCitaDTO(
                        c.getCodigo(),
                        c.getPaciente().getCedula(),
                        c.getPaciente().getNombre(),
                        c.getMedico().getNombre(),
                        c.getMedico().getEspecialidad(),
                        c.getEstadoCita(),
                        c.getFechaCita()
                ));
            }
        }
        return lista;
    }

    @Override
    public LocalDate agendarDiaLibre(int idMedico, LocalDate fechaLibre) throws Exception{

        LocalDate fechaHoy = LocalDate.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy MMMM dd, HH:mm");
        String fecha = formato.format(fechaLibre);

        if (fechaLibre.isBefore(fechaHoy)) {
            throw new Exception("Fecha invalida");
        }
        Optional<DiaLibreMedico> optional = diaLibreRepo.findById(idMedico);
        DiaLibreMedico agendado = optional.get();
        if (agendado.getFechaLibre().isAfter(LocalDate.now())) {
            throw new Exception("Ya tiene una fecha agendada como día libre");
        }
        List<Cita> citas = citaRepo.buscarCitasMedico(idMedico);

        if (citas.isEmpty()) {
            throw new Exception("No tiene citas agendadas");
        }
        for (Cita c : citas) {

            //Valida si en la fecha libre existe una cita
            String fechaCita = formato.format(c.getFechaCita());

            if (fecha.equals(fechaCita)) {
                throw new Exception("Tiene citas agendadas en esa fecha");
            }
                DiaLibreMedico diaLibreMedico = new DiaLibreMedico();
                diaLibreMedico.setFechaLibre(fechaLibre);
                diaLibreRepo.save(diaLibreMedico);

        }
        return fechaLibre;
    }

    public LocalDate modificarDiaLibre(int idMedico, LocalDate nuevoDiaLibre) throws Exception {
        LocalDate fechaHoy = LocalDate.now();

        if (nuevoDiaLibre.isBefore(fechaHoy)) {
            throw new Exception("Fecha invalida");
        }
        List<Cita> citas = citaRepo.buscarCitasMedicoEstado(EstadoCita.PROGRAMADA,idMedico);
        for ( Cita c : citas) {
            if (c.getFechaCita().equals(nuevoDiaLibre)) {
                throw new Exception("El médico tiene fechas de días agendadas en la fecha solicitada");
            }
        }
        if(!buscarDiaLibre(idMedico)) {
            throw new Exception("El médico no tiene fecha de día libre agendado");
        }
        Optional<DiaLibreMedico> encontrado = diaLibreRepo.findById(idMedico);
        DiaLibreMedico diaLibre = encontrado.get();
        diaLibreRepo.delete(diaLibre);

        DiaLibreMedico cambioDia = new DiaLibreMedico();
        cambioDia.setFechaLibre(nuevoDiaLibre);
        diaLibreRepo.save(cambioDia);
        return cambioDia.getFechaLibre();
    }

    private boolean buscarDiaLibre(int codigo) throws Exception {return diaLibreRepo.findById(codigo) != null;}

    public String cancelarDiaLibre(int idMedico) throws Exception{


        List<DiaLibreMedico> dias = diaLibreRepo.findByCodigo(idMedico);

        for ( DiaLibreMedico dm : dias) {

            if (dm.getFechaLibre().isAfter(LocalDate.now())) {
                diaLibreRepo.delete(dm);
            }
        }
        return "El dia libre agendado fue cancelado con éxito";
    }


}