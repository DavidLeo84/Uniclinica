package co.edu.uniquindio.uniclinica.repositorios;

import co.edu.uniquindio.uniclinica.modelo.entidades.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CitaRepo extends JpaRepository<Cita, Integer> {

    //Cita findByPacienteCedula(String cedula);

    List<Cita> findByPacienteCedula(String cedula);

    List<Cita> findAllById(int id);

    List<Cita> findAllByPacienteCedula(String cedula);

    List<Cita> findAllByFechaCita(LocalDateTime fecha);

    List<Cita> findAllByMedicoNombre(String nombre);

    List<Cita> findByMedico_Id(int id);
}
