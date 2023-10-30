package co.edu.uniquindio.uniclinica.dto.administrador;

import jakarta.validation.constraints.NotNull;

public record DetalleAdministradorDTO(
        @NotNull
        int id
) {
}
