package co.edu.uniquindio.uniclinica.modelo.entidades;


import jakarta.persistence.Column;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ToString
//@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Administrador extends Cuenta implements Serializable {


    @Column(name = "nombre")
    private String NombreAdministrador;
}
