package co.edu.uniquindio.uniclinica.test;

import co.edu.uniquindio.uniclinica.dto.paciente.DetallePacienteDTO;
import co.edu.uniquindio.uniclinica.dto.paciente.ItemPacienteDTO;
import co.edu.uniquindio.uniclinica.dto.paciente.RegistroPacienteDTO;
import co.edu.uniquindio.uniclinica.modelo.enums.Ciudad;
import co.edu.uniquindio.uniclinica.modelo.enums.Eps;
import co.edu.uniquindio.uniclinica.modelo.enums.TipoSangre;
import co.edu.uniquindio.uniclinica.servicios.interfaces.PacienteServicio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootTest
public class PacienteServicioTest {

    @Autowired
    private PacienteServicio pacienteServicio;

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
        int nuevo = pacienteServicio.registrarse(pacienteDTO);
        Assertions.assertNotEquals(0,nuevo);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void editarPerfilTest() throws Exception{

        DetallePacienteDTO guardado = pacienteServicio.verDetallePaciente(1);

        DetallePacienteDTO modificado = new DetallePacienteDTO(
                guardado.codigo(),
                guardado.cedula(),
                guardado.nombre(),
                "3002345679",
                guardado.correo(),
                guardado.fechaNacimiento(),
                guardado.urlFoto(),
                guardado.ciudad(),
                guardado.eps(),
                guardado.tipoSangre(),
                guardado.alergias()
        );
        pacienteServicio.editarPerfil(modificado);

        DetallePacienteDTO objetoModificado = pacienteServicio.verDetallePaciente(1);

        Assertions.assertEquals("3002345679", objetoModificado.telefono());
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void inactivarTest() throws Exception{

        pacienteServicio.inactivarCuenta(1);

        Assertions.assertThrows(Exception.class,() ->pacienteServicio.verDetallePaciente(1));

    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listarTest() throws Exception{

        List<ItemPacienteDTO> lista = pacienteServicio.listarPacientes();

    }
}
