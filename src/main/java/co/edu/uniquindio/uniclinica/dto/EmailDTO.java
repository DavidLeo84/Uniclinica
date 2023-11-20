package co.edu.uniquindio.uniclinica.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;

public record EmailDTO(

        @NotBlank
        String asunto,
        @Lob
        String cuerpo,
        @NotNull
        String destinatario

) {

}
