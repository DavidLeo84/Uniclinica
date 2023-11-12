package co.edu.uniquindio.uniclinica.modelo.entidades;

import co.edu.uniquindio.uniclinica.modelo.enums.EstadoPqrs;
import co.edu.uniquindio.uniclinica.modelo.enums.TipoPqrs;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Pqrs implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @EqualsAndHashCode.Include
    private int codigo;

    @Column
    private LocalDateTime FechaCreacion;

    @Column
    @Enumerated(EnumType.ORDINAL)
    TipoPqrs tipoPqrs;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Cita cita;

    @Column
    private Paciente paciente; // revisar si es valido el objeto para persistencia

    @Column
    @Lob
    private String motivo;

    @Enumerated(EnumType.STRING)
    @Column
    private EstadoPqrs estadoPqrs;

    @OneToMany(mappedBy = "pqrs")
    private List<Mensaje> mensajes;

}
