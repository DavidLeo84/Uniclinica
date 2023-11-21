package co.edu.uniquindio.uniclinica.modelo.entidades;

import co.edu.uniquindio.uniclinica.modelo.enums.EstadoCita;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cita implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codigo;

    @Column(nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name="fecha_cita")
    private LocalDateTime fechaCita;

    @Lob
    private String motivo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Medico medico;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private EstadoCita estadoCita;

    @OneToOne(mappedBy = "cita")
    private Atencion atencion;

    @OneToMany(mappedBy = "cita")
    private List<Pqrs> pqrs;

    public Cita(LocalDateTime fechaCreacion,LocalDateTime  fechaCita, String motivo, Paciente paciente, Medico medico, EstadoCita estadoCita) {
        this.fechaCreacion = fechaCreacion;
        this.fechaCita = fechaCita;
        this.motivo = motivo;
        this.paciente = paciente;
        this.medico = medico;
        this.estadoCita = estadoCita;
    }


}
