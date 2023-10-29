package co.edu.uniquindio.uniclinica.modelo.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HorarioMedico implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codigo;

    @Column(nullable = false)
    private String dia;

    @Column(nullable = false)
    private LocalTime horaInicio;

    @Column(nullable = false)
    private LocalTime horaSalida;

    @ManyToOne(fetch = FetchType.LAZY)
    private Medico medico;

    public HorarioMedico(HorarioDTO h, Medico medico){
        this.setDia(h.dia());
        this.setHoraInicio(h.horaInicio());
        this.setHoraSalida(h.horaSalida());
        this.setMedico(medico);
    }
}
