package co.edu.uniquindio.uniclinica.test;

import co.edu.uniquindio.uniclinica.dto.administrador.HorarioDTO;
import co.edu.uniquindio.uniclinica.dto.administrador.RegistroMedicoDTO;
import co.edu.uniquindio.uniclinica.modelo.enums.Ciudad;
import co.edu.uniquindio.uniclinica.modelo.enums.Especialidad;
import co.edu.uniquindio.uniclinica.servicios.interfaces.AdministradorServicio;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
public class AdministradorServicioTest {

    @Autowired
    private AdministradorServicio administradorServicio;


    @Test
    public void crearMedicoTest(){

        List<HorarioDTO> horarios = new ArrayList<>();
        horarios.add( new HorarioDTO("LUNES", LocalTime.of(7, 0, 0), LocalTime.of(14, 0, 0) ) );

        RegistroMedicoDTO medicoDTO = new RegistroMedicoDTO(
                "Pepito",
                "82872",
                Ciudad.ARMENIA,
                Especialidad.CARDIOLOGIA,
                "78387",
                "pepito@email.com",
                "123a",
                "url_foto",
                horarios
        );

        try {
            administradorServicio.crearMedico(medicoDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
