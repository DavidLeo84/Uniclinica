package co.edu.uniquindio.uniclinica.repositorios;

import co.edu.uniquindio.uniclinica.modelo.entidades.Atencion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AtencionRepo extends JpaRepository<Atencion, Integer> {

    @Query("select at from Atencion at join at.cita cit on cit.codigo = at.codigo join cit.paciente pac on cit.codigo = pac.id")
    List<Atencion> findAllByCita_Paciente_id(int idPaciente);

    @Query("select at from Atencion at join at.cita cit on cit.codigo = at.codigo join cit.medico med on cit.codigo = med.id")
    List<Atencion> findAllByIdMedico (int idMedico);

    Optional<Atencion> findAtencionByCitaCodigo(int codigoCita);

}
