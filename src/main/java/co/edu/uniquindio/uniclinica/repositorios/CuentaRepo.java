package co.edu.uniquindio.uniclinica.repositorios;

import co.edu.uniquindio.uniclinica.modelo.entidades.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CuentaRepo extends JpaRepository<Cuenta, Integer> {

    Cuenta findByCorreo(String username);

    Optional<Cuenta> findByCorreo(String correo);
}
