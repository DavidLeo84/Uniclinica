package co.edu.uniquindio.uniclinica.test;

import co.edu.uniquindio.uniclinica.dto.administrador.*;
import co.edu.uniquindio.uniclinica.dto.paciente.*;
import co.edu.uniquindio.uniclinica.modelo.entidades.Cita;
import co.edu.uniquindio.uniclinica.modelo.entidades.Medico;
import co.edu.uniquindio.uniclinica.modelo.entidades.Paciente;
import co.edu.uniquindio.uniclinica.modelo.entidades.Pqrs;
import co.edu.uniquindio.uniclinica.modelo.enums.*;
import co.edu.uniquindio.uniclinica.servicios.interfaces.AdministradorServicio;
import co.edu.uniquindio.uniclinica.servicios.interfaces.MensajeServicio;
import co.edu.uniquindio.uniclinica.servicios.interfaces.PacienteServicio;
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
    @Autowired
    private PacienteServicio pacienteServicio;
    @Autowired
    private MensajeServicio mensajeServicio;


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
                    TipoSangre.A_POSITIVO,
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
    public void actualizarMedicoTest() throws Exception {
        DetalleMedicoDTO guardado = administradorServicio.obtenerMedico(6);
        System.out.println(guardado);
        DetalleMedicoDTO modificado = new DetalleMedicoDTO(
                guardado.codigo(),
                guardado.nombre(),
                guardado.cedula(),
                guardado.ciudad(),
                guardado.especialidad(),
                guardado.tipoSangre(),
                "111111",
                guardado.correo(),
                guardado.urlFoto(),
                guardado.horarios()
        );
        System.out.println(modificado);


        administradorServicio.actualizarMedico(modificado);
        DetalleMedicoDTO objetoModificado = administradorServicio.obtenerMedico(6);
        Assertions.assertEquals("111111", objetoModificado.telefono());
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void eliminarMedicoTest() throws Exception {
        administradorServicio.eliminarMedico(6);
        //Assertions.assertThrows(Exception.class, () -> administradorServicio.obtenerMedico(6));
    }
    @Test
    @Sql("classpath:dataset.sql")
    public void listarMedicosTest() throws Exception {
        List<ItemMedicoDTO> listaMedicos = administradorServicio.listarMedicos();
        listaMedicos.forEach(System.out::println);
        Assertions.assertEquals(5, listaMedicos.size());
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void cambiarEstadoPqrsTest() throws Exception {
        DetallePqrsDTO guardado = administradorServicio.verDetallePqrs(1);
        administradorServicio.cambiarEstadoPqrs(
                guardado.codigo(),
                EstadoPqrs.RESUELTO
        );
        DetallePqrsDTO objetoModificado = administradorServicio.verDetallePqrs(1);
        Assertions.assertEquals(EstadoPqrs.RESUELTO, objetoModificado.estadoPqrs());
    }
    @Test
    @Sql("classpath:dataset.sql")
    public void listarPqrsTest() throws Exception {
        List<ItemPqrsDTO> listaPqrs = administradorServicio.listarPqrs();
        listaPqrs.forEach(System.out::println);
        Assertions.assertEquals(5, listaPqrs.size());
    }
    @Test
    @Sql("classpath:dataset.sql")
    public void verDetallePqrsTest() throws Exception {

        DetallePqrsDTO guardado = administradorServicio.verDetallePqrs(1);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listarCitasMedicoTest() throws Exception {
        List<ItemCitaDTO> lista = administradorServicio.listarCitasMedico(7);
        lista.forEach(System.out::println);
        Assertions.assertEquals(5, lista.size());
    }
    @Test
    @Sql("classpath:dataset.sql")
    public void listarPacientesTest() throws Exception {
        List<ItemPacienteDTO> listaPacientes = administradorServicio.listarPacientes();
        listaPacientes.forEach(System.out::println);
        Assertions.assertEquals(5, listaPacientes.size());
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void responderPqrsTest() throws Exception{
        List<HorarioDTO> horarios = new ArrayList<>();
        Medico medico = administradorServicio.crearMedico(new RegistroMedicoDTO("¨Patricia Ramirez", "23435324", Ciudad.ARMENIA, Especialidad.MEDICINA_GENERAL,TipoSangre.A_POSITIVO,"312311312", "patty@gmail.com","12345", "url_foto",horarios));

        Paciente paciente = pacienteServicio.registrarse(new RegistroPacienteDTO("24567234", "Pepito Perez", "5454545", "url_foto", Ciudad.ARMENIA, LocalDate.of(2000,10,10), "Sin alergias", Eps.ALIANSALUD, TipoSangre.A_NEGATIVO, "pepito@email.com", "123"));

        Cita cita = pacienteServicio.crearCita(new RegistroCitaDTO(LocalDateTime.now(), LocalDateTime.of(2023,10,30,8,00,00), "desconocidos", "111111", "fulanito", "Josefo", Especialidad.GASTROENTEROLOGIA, EstadoCita.PROGRAMADA), "111111", LocalDateTime.of(2023,11,30,8,00,0), Especialidad.ODONTOLOGIA);

        Pqrs pqrs = pacienteServicio.crearPqrs(new RegistroPqrsDTO(
                LocalDateTime.now(),
                TipoPqrs.QUEJA,
                "Mala atencion",
                cita.getCodigo(),
                paciente.getId(),
                "Motivos varios",
                EstadoPqrs.EN_PROCESO
        ));
        RegistroRespuestaPacienteDTO respuesta = new RegistroRespuestaPacienteDTO(
                1,
                1,
                TipoMensaje.DESTINATARIO,
                LocalDateTime.of(2023, 11,20,10,30,00),
                "Buena tarde, ¿en qué le puedo servir?"

        );
        /*
        Pqrs nuevo = administradorServicio.responderPqrs(1,1, respuesta);
        String primerMensaje = mensajeServicio.obtener(nuevo.getMensajes());
        String segundoMensaje = mensajeServicio.obtener(nuevo).contenido();
        System.out.println(primerMensaje);
        System.out.println(segundoMensaje);
        Assertions.assertNotEquals(0, nuevo);

         */
    }




}
