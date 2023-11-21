package co.edu.uniquindio.uniclinica.dto;

import co.edu.uniquindio.uniclinica.modelo.entidades.Paciente;

public record CambiarPasswordDTO(

        int codigoCuenta,

        String actualPassword



) {
    public CambiarPasswordDTO(Paciente paciente, String nuevaPassword){


        this(
                paciente.getId(),
                paciente.getPassword()

        );



    }
}
