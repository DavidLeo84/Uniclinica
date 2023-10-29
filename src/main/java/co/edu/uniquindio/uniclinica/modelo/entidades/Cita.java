package co.edu.uniquindio.uniclinica.modelo.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoCita estadoCita;

    public Cita(LocalDateTime fechaCreacion,LocalDateTime  fechaCita, String motivo, Paciente paciente, Medico medico, EstadoCita estadoCita) {
        this.fechaCreacion = fechaCreacion;
        this.fechaCita = fechaCita;
        this.motivo = motivo;
        this.paciente = paciente;
        this.medico = medico;
        this.estadoCita = estadoCita;
    }
}
