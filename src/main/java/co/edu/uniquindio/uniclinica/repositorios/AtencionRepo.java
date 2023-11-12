package co.edu.uniquindio.uniclinica.repositorios;

import co.edu.uniquindio.uniclinica.modelo.entidades.Atencion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AtencionRepo extends JpaRepository<Atencion, Integer> {

    List<Atencion> findAllByCita_Paciente_Cedula(String cedula);

    Optional<Atencion> findAtencionByCitaCodigo(int codigoCita);

}
