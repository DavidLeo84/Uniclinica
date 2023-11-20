package co.edu.uniquindio.uniclinica.test;


import co.edu.uniquindio.uniclinica.dto.administrador.HorarioDTO;
import co.edu.uniquindio.uniclinica.dto.administrador.RegistroMedicoDTO;
import co.edu.uniquindio.uniclinica.dto.medico.DiaLibreDTO;
import co.edu.uniquindio.uniclinica.dto.medico.ItemAtencionDTO;
import co.edu.uniquindio.uniclinica.dto.medico.RegistroAtencionDTO;
import co.edu.uniquindio.uniclinica.dto.paciente.*;
import co.edu.uniquindio.uniclinica.modelo.entidades.DiaLibreMedico;
import co.edu.uniquindio.uniclinica.modelo.entidades.Medico;
import co.edu.uniquindio.uniclinica.modelo.entidades.Paciente;
import co.edu.uniquindio.uniclinica.modelo.entidades.Cita;
import co.edu.uniquindio.uniclinica.modelo.enums.*;
import co.edu.uniquindio.uniclinica.servicios.interfaces.AdministradorServicio;
import co.edu.uniquindio.uniclinica.servicios.interfaces.MedicoServicio;
import co.edu.uniquindio.uniclinica.servicios.interfaces.PacienteServicio;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
public class MedicoServicioTest {

    @Autowired
    private MedicoServicio medicoServicio;
    @Autowired
    private PacienteServicio pacienteServicio;
    @Autowired
    private AdministradorServicio administradorServicio;


    @Test
    @Sql("classpath:dataset.sql")
    public void listarCitasPendientesTest() throws Exception {
        List<ItemCitaDTO> lista = medicoServicio.listarCitasPendientes(7);
        lista.forEach(System.out::println);
        Assertions.assertEquals(5, lista.size());
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listarCitasPendientesDiaTest() throws Exception{

        List<ItemCitaDTO> lista = medicoServicio.listarCitasPendientesDia(7);
        lista.forEach(System.out::println);
        Assertions.assertEquals(5, lista.size());
    }
    @Test
    public void atenderCitaTest() throws Exception {
        List<HorarioDTO> horarios = new ArrayList<>();
        Paciente paciente = pacienteServicio.registrarse(new RegistroPacienteDTO("18345678", "Jose Ramos", "3233434442", "url_foto", Ciudad.ARMENIA, LocalDate.of(1989,07,05), "Alergias ninguna", Eps.ALIANSALUD, TipoSangre.A_NEGATIVO, "jose@email.com", "98873"));
        Medico medico = administradorServicio.crearMedico(new RegistroMedicoDTO("¨Patricia Ramirez", "23435324", Ciudad.ARMENIA, Especialidad.MEDICINA_GENERAL,TipoSangre.A_POSITIVO,"312311312", "patty@gmail.com","12345", "url_foto",horarios));
        Cita cita = pacienteServicio.crearCita(new RegistroCitaDTO(LocalDateTime.now(),LocalDateTime.of(2023,11,30,8,00),"Motivaciones","111111", "Jose Ramos","Patricia Ramirez", Especialidad.MEDICINA_GENERAL, EstadoCita.PROGRAMADA),"111111",LocalDateTime.of(2023,11,30,8,00),Especialidad.MEDICINA_GENERAL);

        RegistroAtencionDTO registroAtencionDTO = new RegistroAtencionDTO(
                cita.getCodigo(),
                "Diagnostico",
                "notas",
                "tratamiento",
                80000

        );
        //Cita atendida = cita;
        //Assertions.assertEquals(cita.getEstadoCita(), EstadoCita.COMPLETADA);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listarAtencionesPacienteTest() throws Exception {

        List<ItemAtencionDTO> lista = medicoServicio.listarAtencionesPaciente(1);
        lista.forEach(System.out::println);
        Assertions.assertEquals(1, lista.size());
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listarCitasRealizadasMedicoTest() throws Exception {

        List<ItemCitaDTO> lista = medicoServicio.listarCitasRealizadasMedico(7);
        lista.forEach(System.out::println);
        Assertions.assertEquals(1, lista.size());
    }

    @Test
    public void agendarDiaLibreTest() throws Exception{

        List<HorarioDTO> horarios = new ArrayList<>();
        Medico medico = administradorServicio.crearMedico(new RegistroMedicoDTO("¨Patricia Ramirez", "23435324", Ciudad.ARMENIA, Especialidad.MEDICINA_GENERAL,TipoSangre.A_POSITIVO,"312311312", "patty@gmail.com","12345", "url_foto",horarios));
        DiaLibreDTO diaLibreDTO = new DiaLibreDTO(
                1,
                6,
                LocalDate.of(2023,12,7)

        );
        //LocalDate diaLibre = medicoServicio.agendarDiaLibre(6, LocalDate.of(2023,12,7));
        //Assertions.assertEquals(diaLibre, null);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void modificarDiaLibreTest() throws Exception {
        LocalDate diaLibre = medicoServicio.modificarDiaLibre(6,LocalDate.of(2023,12,8));
        //Assertions.assertNotEquals(diaLibre,LocalDate.of(2023,10,12));
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void cancelarDiaLibre() throws Exception {
        String diaLibre = medicoServicio.cancelarDiaLibre(6);
    }

}
