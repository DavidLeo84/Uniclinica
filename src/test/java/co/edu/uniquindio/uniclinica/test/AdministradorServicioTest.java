package co.edu.uniquindio.uniclinica.test;

import co.edu.uniquindio.uniclinica.dto.administrador.*;
import co.edu.uniquindio.uniclinica.dto.paciente.ItemPqrsDTO;
import co.edu.uniquindio.uniclinica.dto.paciente.RegistroPacienteDTO;
import co.edu.uniquindio.uniclinica.modelo.entidades.Medico;
import co.edu.uniquindio.uniclinica.modelo.enums.*;
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
/*
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

 */

    @Test
    @Sql("classpath:dataset.sql")
    public void actualizarMedico() throws Exception {
        DetalleMedicoDTO guardado = administradorServicio.obtenerMedico(6);
        DetalleMedicoDTO modificado = new DetalleMedicoDTO(
                guardado.codigo(),
                guardado.cedula(),
                guardado.nombre(),
                guardado.ciudad(),
                guardado.especialidad(),
                "111111",
                guardado.correo(),
                guardado.urlFoto(),
                guardado.horarios()
        );

        administradorServicio.actualizarMedico(modificado);
        DetalleMedicoDTO objetoModificado = administradorServicio.obtenerMedico(6);
        Assertions.assertEquals("111111", objetoModificado.telefono());
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void eliminarMedico() throws Exception {
        administradorServicio.eliminarMedico(6);
        Assertions.assertThrows(Exception.class, () -> administradorServicio.obtenerMedico(6));
    }
    @Test
    @Sql("classpath:dataset.sql")
    public void listarMedicos() throws Exception {
        List<ItemMedicoDTO> lista = administradorServicio.listarMedicos();
        lista.forEach(System.out::println);
        Assertions.assertEquals(5, lista.size());
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void cambiarEstadoPQRS() throws Exception {
        DetallePqrsDTO guardado = administradorServicio.verDetallePqrs(1);
        administradorServicio.cambiarEstadoPqrs(
                guardado.codigo(),
                EstadoPqrs.RESUELTO
        );
        DetallePqrsDTO objetoModificado = administradorServicio.verDetallePqrs(1);
        Assertions.assertEquals(EstadoPqrs.RESUELTO, objetoModificado.estadoPqrs());
    }


}
