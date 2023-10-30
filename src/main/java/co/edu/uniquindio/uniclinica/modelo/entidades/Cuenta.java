package co.edu.uniquindio.uniclinica.modelo.entidades;


import co.edu.uniquindio.uniclinica.modelo.enums.EstadoUsuario;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Inheritance(strategy = InheritanceType.JOINED)
public class Cuenta implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, length = 80, nullable = false)
    private String correo;

    @Column(length = 10, nullable = false)
    private String password;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private EstadoUsuario estado;

    @OneToMany(mappedBy = "cuenta")
    private List<Mensaje> mensajes;


}
