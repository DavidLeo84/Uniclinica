package co.edu.uniquindio.uniclinica.test;

import co.edu.uniquindio.uniclinica.dto.administrador.DetalleMedicoDTO;
import co.edu.uniquindio.uniclinica.dto.administrador.HorarioDTO;
import co.edu.uniquindio.uniclinica.dto.administrador.RegistroMedicoDTO;
import co.edu.uniquindio.uniclinica.dto.paciente.RegistroPacienteDTO;
import co.edu.uniquindio.uniclinica.modelo.entidades.Medico;
import co.edu.uniquindio.uniclinica.modelo.enums.Ciudad;
import co.edu.uniquindio.uniclinica.modelo.enums.Eps;
import co.edu.uniquindio.uniclinica.modelo.enums.Especialidad;
import co.edu.uniquindio.uniclinica.modelo.enums.TipoSangre;
import co.edu.uniquindio.uniclinica.servicios.interfaces.AdministradorServicio;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
public class AdministradorServicioTest {

    @Autowired
    private AdministradorServicio administradorServicio;


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
    @Sql("classpath:dataset.sql")
    public void actualizarMedico()  {
        Medico medicoActualizar;
        try {
            medicoActualizar = administradorServicio.buscarMedico(6);
            medicoActualizar.setCedula("92993");
            medicoActualizar.setNombre("David");
            medicoActualizar.setTelefono("111111");

            Medico actualizado = administradorServicio.actualizarMedico2(medicoActualizar);
            Assertions.assertEquals("111111", actualizado.getTelefono());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    @Test
    @Sql("classpath:dataset.sql")
    public void eliminarMedico() throws Exception {
        administradorServicio.eliminarMedico(6);
        Assertions.assertThrows(Exception.class, () -> administradorServicio.eliminarMedico(6));
    }

}
