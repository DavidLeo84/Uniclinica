package co.edu.uniquindio.uniclinica.dto.administrador;

import co.edu.uniquindio.uniclinica.modelo.entidades.Medico;
import co.edu.uniquindio.uniclinica.modelo.enums.Especialidad;

public record ItemMedicoDTO(

        int codigo,
        String nombre,
        String cedula,
        String urlFoto,
        Especialidad especialidad
) {

    /*
    public ItemMedicoDTO(Medico medico) {
        this(
                medico.getId(),
                medico.getNombre(),
                medico.getCedula(),
                medico.getUrlFoto(),
                medico.getEspecialidad()
        );
    }

     */
}
