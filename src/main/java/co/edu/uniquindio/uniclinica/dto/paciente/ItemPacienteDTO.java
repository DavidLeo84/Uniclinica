package co.edu.uniquindio.uniclinica.dto.paciente;

import co.edu.uniquindio.uniclinica.modelo.entidades.Paciente;
import co.edu.uniquindio.uniclinica.modelo.enums.Ciudad;

public record ItemPacienteDTO(

        int id,
        String cedula,
        String nombre,
        Ciudad ciudad
) {


    public ItemPacienteDTO(Paciente paciente){
        this(
                paciente.getId(),
                paciente.getCedula(),
                paciente.getNombre(),
                paciente.getCiudad()
        );
    }


}
