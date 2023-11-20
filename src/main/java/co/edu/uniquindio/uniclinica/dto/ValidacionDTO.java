package co.edu.uniquindio.uniclinica.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public record ValidacionDTO(

        String campo,
        String error


) {

}