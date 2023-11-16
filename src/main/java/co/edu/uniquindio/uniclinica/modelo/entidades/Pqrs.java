package co.edu.uniquindio.uniclinica.modelo.entidades;

import co.edu.uniquindio.uniclinica.dto.paciente.RegistroPqrsDTO;
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
    @Lob
    private String motivo;

    @Enumerated(EnumType.ORDINAL)
    @Column
    private EstadoPqrs estadoPqrs;

    @OneToMany(mappedBy = "pqrs")
    private List<Mensaje> mensajes;

    public Pqrs(RegistroPqrsDTO datos, Cita cita) {
        this.setFechaCreacion(datos.fechaCreacion());
        this.setTipoPqrs(datos.tipoPqrs());
        this.setMotivo(datos.motivo());
        this.setEstadoPqrs(datos.estadoPqrs());
        this.setCita(cita);

    }
}