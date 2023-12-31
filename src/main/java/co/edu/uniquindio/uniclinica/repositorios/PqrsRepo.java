package co.edu.uniquindio.uniclinica.repositorios;

import co.edu.uniquindio.uniclinica.modelo.entidades.Pqrs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PqrsRepo extends JpaRepository<Pqrs, Integer> {

    //Pqrs findById(int id);

    //Pqrs findByCodigo(int codigo);

    @Query("select pq from Pqrs pq join pq.cita ct join ct.paciente pac where pac.id = :id")
    List<Pqrs> buscarPqrsPaciente(int id);
}
