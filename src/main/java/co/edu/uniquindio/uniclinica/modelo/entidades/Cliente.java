package co.edu.uniquindio.uniclinica.modelo.entidades;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Entity
public class Cliente implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cedula;

    @Column(length = 60, nullable = false)
    private String nombre;

    @Email
    @Column(unique = true, nullable = false)
    private String correo;

    @ElementCollection
    @Column(nullable = false, length = 10)
    private Map<String, String> telefono;

    @OneToMany(mappedBy = "cliente")
    private List<Transaccion> transacciones;

}
