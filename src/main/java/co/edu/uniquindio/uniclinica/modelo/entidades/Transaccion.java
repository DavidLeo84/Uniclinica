package co.edu.uniquindio.uniclinica.modelo.entidades;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Transaccion implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codigo;

    @Column(nullable = false)
    @DateTimeFormat
    private LocalDateTime fecha;

    @OneToOne
    private Pago pago;

    @ManyToOne
    private Paciente paciente;

    @ManyToOne
    private Medico medico;

    @OneToOne
    private Atencion atencion;

    @ManyToMany
    private List<Medicamento> medicamentos;
}
