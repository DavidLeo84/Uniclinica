package co.edu.uniquindio.uniclinica.test;

import co.edu.uniquindio.uniclinica.dto.CambiarPasswordDTO;
import co.edu.uniquindio.uniclinica.dto.EmailDTO;
import co.edu.uniquindio.uniclinica.dto.administrador.DetalleMedicoDTO;
import co.edu.uniquindio.uniclinica.dto.administrador.HorarioDTO;
import co.edu.uniquindio.uniclinica.dto.administrador.ItemMedicoDTO;
import co.edu.uniquindio.uniclinica.dto.administrador.RegistroMedicoDTO;
import co.edu.uniquindio.uniclinica.dto.paciente.*;
import co.edu.uniquindio.uniclinica.modelo.entidades.Cita;
import co.edu.uniquindio.uniclinica.modelo.entidades.Medico;
import co.edu.uniquindio.uniclinica.modelo.entidades.Paciente;
import co.edu.uniquindio.uniclinica.modelo.entidades.Pqrs;
import co.edu.uniquindio.uniclinica.modelo.enums.*;
import co.edu.uniquindio.uniclinica.servicios.interfaces.AdministradorServicio;
import co.edu.uniquindio.uniclinica.servicios.interfaces.PacienteServicio;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootTest
@Transactional
public class PacienteServicioTest {

    @Autowired
    private PacienteServicio pacienteServicio;
    @Autowired
    private AdministradorServicio administradorServicio;

    @Test
    //@Sql("classpath:dataset.sql")
    public void registrarseTest() throws Exception{


        RegistroPacienteDTO pacienteDTO = new RegistroPacienteDTO(
                "24477236",
                "Rubiela Marulanda",
                "3173278462",
                "www.foto.como",
                Ciudad.ARMENIA,
                LocalDate.of(1950,05,20),
                "Sol y polvo",
                Eps.SANITAS,
                TipoSangre.A_POSITIVO,
                "marubiel@gmail.com",
                "123456"
        );
        Paciente nuevo = pacienteServicio.registrarse(pacienteDTO);
        Assertions.assertNotEquals(0,nuevo);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void editarPerfilTest() throws Exception{

        DetallePacienteDTO guardado = pacienteServicio.verDetallePaciente(1);

        DetallePacienteDTO modificado = new DetallePacienteDTO(
                guardado.codigoPaciente(),
                guardado.cedula(),
                guardado.ciudad(),
                guardado.nombre(),
                "111111",
                guardado.correo(),
                guardado.tipoSangre(),
                guardado.urlFoto(),
                guardado.fechaNacimiento(),
                guardado.alergias(),
                guardado.eps()
        );
        pacienteServicio.editarPerfil(modificado);

        DetallePacienteDTO objetoModificado = pacienteServicio.verDetallePaciente(1);

        Assertions.assertEquals("111111", objetoModificado.telefono());
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void eliminarPacienteTest() throws Exception{

        pacienteServicio.eliminarPaciente(2);
        Assertions.assertThrows(Exception.class,() ->pacienteServicio.obtenerPaciente(2));

    }
    @Test
    @Sql("classpath:dataset.sql")
    public void enviarLinkRecuperacionTest() throws Exception{

        String passwordGenerica = "zxcdsaqwemnbjklpoi";
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String passwordEncriptada = passwordEncoder.encode(passwordGenerica);

        EmailDTO emailDTO =  pacienteServicio.enviarLinkRecuperacion("11111111");
        EmailDTO recuperar = new EmailDTO(

                "Clave de recuperación de cuenta",
                "Ingrese a su cuenta con la contraseña adjunta " + passwordEncriptada,
                emailDTO.destinatario()
        );
        Paciente buscado = pacienteServicio.buscarPaciente(1);
        Assertions.assertNotEquals("11111111", buscado.getPassword());
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void cambiarPasswordTest() throws Exception{

        pacienteServicio.cambiarPassword(2, "1234567");

        Paciente objetoModificado = pacienteServicio.buscarPaciente(2);
        Assertions.assertEquals("1234567", objetoModificado.getPassword());

    }

    @Test
    public void crearCitaTest() throws Exception{
        List<HorarioDTO> horarios = new ArrayList<>();
        Paciente paciente = pacienteServicio.registrarse(new RegistroPacienteDTO("18345678", "Jose Ramos", "3233434442", "url_foto", Ciudad.ARMENIA, LocalDate.of(1989,07,05), "Alergias ninguna", Eps.ALIANSALUD, TipoSangre.A_NEGATIVO, "jose@email.com", "98873"));
        Medico medico = administradorServicio.crearMedico(new RegistroMedicoDTO("¨Patricia Ramirez", "23435324", Ciudad.ARMENIA, Especialidad.MEDICINA_GENERAL,TipoSangre.A_POSITIVO,"312311312", "patty@gmail.com","12345", "url_foto",horarios));
        AgendarCitaDTO agendarCitaDTO = new AgendarCitaDTO(
                1,
                6,
                LocalDateTime.now(),
                LocalDateTime.of(2023,11,30,8,00),
                "motivo",
                Especialidad.PEDIATRIA
        );
        //int detalleCita = pacienteServicio.crearCita(RegistroCitaDTO, "12345", 2023-11-30 14:00:00, 1);
       // Assertions.assertNotEquals(0, detalleCita.codigoCita());
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listarPorEspecialidadTest() throws Exception {
        List<ItemMedicoDTO> lista = pacienteServicio.listarPorEspecialidad(Especialidad.MEDICINA_GENERAL);
        lista.forEach(System.out::println);
        Assertions.assertEquals(2, lista.size());
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void modificarCitaTest() throws Exception {
        DetalleCitaDTO citaDTO = pacienteServicio.verDetalleCita(1);
        DetalleCitaDTO modificada = new DetalleCitaDTO(
                citaDTO.codigoCita(),
                citaDTO.nombrePaciente(),
                citaDTO.nombreMedico(),
                citaDTO.fechaCreacion(),
                citaDTO.fechaCita(),
                "Otro motivo"
        );
        pacienteServicio.modificarCita(modificada);
        DetalleCitaDTO citaModificada = pacienteServicio.verDetalleCita(1);
        Assertions.assertEquals("Otro motivo", citaModificada.motivo());
    }
    @Test
    @Sql("classpath:dataset.sql")
    public void cancelarCitaTest() throws Exception {
        pacienteServicio.cancelarCita(1);

        Cita citaModificada = pacienteServicio.buscarCita(1);
        Assertions.assertEquals(EstadoCita.CANCELADA, citaModificada.getEstadoCita());
    }

    @Test
    public void crearPqrsTest() throws Exception {
        List<HorarioDTO> horarios = new ArrayList<>();
        Paciente paciente = pacienteServicio.registrarse(new RegistroPacienteDTO("24567234", "Pepito Perez", "5454545", "url_foto", Ciudad.ARMENIA, LocalDate.of(2000,10,10), "Sin alergias", Eps.ALIANSALUD, TipoSangre.A_NEGATIVO, "pepito@email.com", "123"));
        Medico medico = administradorServicio.crearMedico(new RegistroMedicoDTO("Josefo","222222",Ciudad.CALI,Especialidad.ODONTOLOGIA,TipoSangre.A_POSITIVO,"12345678","MedicoJosefo@gmail.com","1234567890","url_foto", horarios));

        Cita cita = pacienteServicio.crearCita(new RegistroCitaDTO(LocalDateTime.now(), LocalDateTime.of(2023,10,30,8,00,00), "desconocidos", "111111", "fulanito", "Josefo", Especialidad.GASTROENTEROLOGIA, EstadoCita.PROGRAMADA), "111111", LocalDateTime.of(2023,11,30,8,00,0), Especialidad.ODONTOLOGIA);

        RegistroPqrsDTO datos = new RegistroPqrsDTO(

                LocalDateTime.now(),
                TipoPqrs.QUEJA,
                "Dolor en un pelo",
                cita.getCodigo(),
                paciente.getId(),
                cita.getMotivo(),
                EstadoPqrs.NUEVO
        );

    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listarPqrsPacienteTest() throws Exception{
        List<ItemPqrsPacienteDTO> lista = pacienteServicio.listarPqrsPaciente(1);
        lista.forEach(System.out::println);
        Assertions.assertEquals(5, lista.size());
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listarCitasPacienteTest() throws Exception {
        List<DetalleCitaDTO> lista = pacienteServicio.listarCitasPaciente(1);
        lista.forEach(System.out::println);
        Assertions.assertEquals(5, lista.size());
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void filtrarCitasPorFechaTest() throws Exception {

        List<ItemCitaDTO> lista = pacienteServicio.filtrarCitasPorFecha(LocalDateTime.of(2023,10,01,7,00,00),
                LocalDateTime.of(2023,11,30,14,00,00),1);
        lista.forEach(System.out::println);
        Assertions.assertEquals(5, lista.size());
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void filtrarCitasMedicoTest() throws Exception{

        List<ItemCitaDTO> lista = pacienteServicio.filtrarCitasMedico(1, 7);
        lista.forEach(System.out::println);
        Assertions.assertEquals(5, lista.size());
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listarCitasTest() throws Exception{

        List<ItemCitaDTO> lista = pacienteServicio.listarCitas();
        lista.forEach(System.out::println);
        Assertions.assertEquals(5, lista.size());
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void verDetalleCita() throws Exception {
        DetalleCitaDTO detalle = pacienteServicio.verDetalleCita(1);
        System.out.println(detalle);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listarHistorialPacienteTest() throws Exception{
        List<ItemCitaDTO> lista = pacienteServicio.listarHistorialPaciente(1);
        lista.forEach(System.out::println);
        Assertions.assertEquals(5, lista.size());

    }
    @Test
    @Sql("classpath:dataset.sql")
    public void listarCitasPendientesPacienteTest() throws Exception {
        List<ItemCitaDTO> lista = pacienteServicio.listarCitasPendientesPaciente(1);
        lista.forEach(System.out::println);
        Assertions.assertEquals(4, lista.size());

    }

    @Test
    @Sql("classpath:dataset.sql")
    public void responderPqrsPacienteTest() throws Exception {
        RegistroRespuestaPacienteDTO datosParaElNuevoMsj = new RegistroRespuestaPacienteDTO(
                1,
                1,
                TipoMensaje.DESTINATARIO,
                LocalDateTime.of(2023,11,20,8, 00,00),
                "Como estás?, soy un admin"

        );
        //mensaje que se crea
       /*  int nuevo = pacienteServicio.responderPqrsPaciente(datosParaElNuevoMsj);
       System.out.println(pacienteServicio.obtener(nuevo).mensajesAsociados());
        System.out.println(pacienteServicio.obtener(nuevo).contenido());
        Assertions.assertNotEquals(0, nuevo);

       */
    }
}
