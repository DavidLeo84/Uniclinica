package co.edu.uniquindio.uniclinica.modelo.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DiaLibreMedico implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int codigo;

    @Column(nullable = false)
    private LocalDateTime fechaLibre;

    @ManyToOne(fetch = FetchType.LAZY)
    private Medico medico;

    public DiaLibre(DiaLibreDTO diaLibreDTO, Medico medico){
        this.setFechaLibre(diaLibreDTO.fechaLibre());
        this.setMedico(medico);
    }
    public void actualizar(DiaLibreDTO datos, Medico medico) {
        this.setFechaLibre(datos.fechaLibre());
        this.setMedico(medico);
    }
}
