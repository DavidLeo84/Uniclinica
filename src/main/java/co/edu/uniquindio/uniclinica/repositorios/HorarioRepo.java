package co.edu.uniquindio.uniclinica.repositorios;

import co.edu.uniquindio.uniclinica.modelo.entidades.HorarioMedico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HorarioRepo extends JpaRepository<HorarioMedico, Integer> {

    @Query("select hr from HorarioMedico hr join hr.medico med where med.id = ?1")
    List<HorarioMedico> findAllByMedicoCodigo(int codigo);

    List<HorarioMedico> findByHoraInicio(LocalDateTime horaInicio);
}
