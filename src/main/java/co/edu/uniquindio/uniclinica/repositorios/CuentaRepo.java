package co.edu.uniquindio.uniclinica.repositorios;

import co.edu.uniquindio.uniclinica.modelo.entidades.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuentaRepo extends JpaRepository<Cuenta, Integer> {

    Cuenta findByCorreo(String username);

}
