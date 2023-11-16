package co.edu.uniquindio.uniclinica.modelo.entidades;

import co.edu.uniquindio.uniclinica.modelo.enums.Ciudad;
import co.edu.uniquindio.uniclinica.modelo.enums.TipoSangre;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Usuario extends Cuenta implements Serializable {

    @Column(nullable = false,  unique = true)
    private String cedula;

    @Column(nullable = false, length = 200)
    private String nombre;

    @Column(nullable = false, length = 20, unique = true)
    private String telefono;

    @Lob
    @Column(nullable = false, unique = true)
    private String urlFoto;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false, length=30)
    private Ciudad ciudad;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private TipoSangre tipoSangre;

}
