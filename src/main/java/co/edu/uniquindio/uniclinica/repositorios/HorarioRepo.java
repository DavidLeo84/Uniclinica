package co.edu.uniquindio.uniclinica.repositorios;

import co.edu.uniquindio.uniclinica.modelo.entidades.HorarioMedico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HorarioRepo extends JpaRepository<HorarioMedico, Integer> {

    List<HorarioMedico> findAllByMedicoCodigo(int codigo);

    List<HorarioMedico> findByMedico_Id(int id);

    List<HorarioMedico> findByHoraInicio(LocalDateTime horaInicio);
}
