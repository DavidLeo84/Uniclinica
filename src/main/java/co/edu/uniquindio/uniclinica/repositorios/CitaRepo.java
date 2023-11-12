package co.edu.uniquindio.uniclinica.repositorios;

import co.edu.uniquindio.uniclinica.modelo.entidades.Cita;
import co.edu.uniquindio.uniclinica.modelo.enums.EstadoCita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CitaRepo extends JpaRepository<Cita, Integer> {

    @Query("select  cit from Cita cit join cit.paciente pac where pac.id = ?1")
    List<Cita> findByPacienteCedula(String cedula);

    @Query("select  cit from Cita cit join cit.paciente pac where pac.id = ?1")
    Optional<Cita> findByPacienteCedulaOp(String cedula);

    @Query( "select ct from Cita ct join ct.medico med where med.id = :id")
    List<Cita> buscarCitasMedico(int id);


    @Query("select  cit from Cita cit join cit.paciente pac where pac.id = ?1")
    List<Cita> buscarCitasPaciente(int codigoPaciente);

    @Query("select cit from Cita cit join cit.medico med where med.id = :codigoMedico and cit.estadoCita = :estadoCita")
    List<Cita> buscarCitasMedicoEstado(EstadoCita estadoCita, int codigoMedico);
}
