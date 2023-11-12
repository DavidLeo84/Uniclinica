package co.edu.uniquindio.uniclinica.servicios.implementacion;

import co.edu.uniquindio.uniclinica.dto.administrador.HorarioDTO;
import co.edu.uniquindio.uniclinica.dto.administrador.RegistroMedicoDTO;
import co.edu.uniquindio.uniclinica.modelo.entidades.Medico;
import co.edu.uniquindio.uniclinica.modelo.enums.Ciudad;
import co.edu.uniquindio.uniclinica.modelo.enums.Especialidad;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AdministradorServicioImplTest {

    @Test
    public void crearMedicoTest() {

        List<HorarioDTO> horarios = new ArrayList<>();
        horarios.add(new HorarioDTO(LocalDateTime.of(2023, Month.NOVEMBER, 23, 7, 00, 00, 0000),
                LocalDateTime.of(2023, Month.NOVEMBER, 23, 16, 00, 00, 0000), new Medico()));
        try {
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

            //Medico medico = administradorServicio.crearMedico(medicoDTO);
            // System.out.println(medico);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void actualizarMedico() {
    }

    @Test
    void eliminarMedico() {
    }

    @Test
    void listarMedicos() {
    }
}