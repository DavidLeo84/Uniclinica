package co.edu.uniquindio.uniclinica.repositorios;

import co.edu.uniquindio.uniclinica.modelo.entidades.Medico;
import co.edu.uniquindio.uniclinica.modelo.enums.Especialidad;
import co.edu.uniquindio.uniclinica.modelo.enums.EstadoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicoRepo extends JpaRepository<Medico, Integer> {
    Medico findByCorreo(String correo);

    Medico findByCedula(String cedula);

   // Optional<Medico> findByCedula(String cedula);

    List<Medico> findAllByEspecialidad(Especialidad especialidad);

    @Query("select med from Medico  med where med.estado = :estadoUsuario")
    List<Medico> findAllByEstadoActivo(EstadoUsuario estadoUsuario);

}
