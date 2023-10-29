package co.edu.uniquindio.uniclinica.modelo.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Atencion implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Lob
    private String diagnostico;

    @Lob
    private String tratamiento;

    @Lob
    private String notasMedicas;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Cita cita;

    public Atencion(RegistroAtencionDTO datos, Cita cita){
        this.setDiagnostico(datos.diagnostico());
        this.setTratamiento(datos.tratamiento());
        this.setNotasMedicas(datos.notasMedicas());
        this.setCita(cita);
    }

    public void actualizar(DetalleAtencionMedicaDTO datos, Cita cita) {
        this.setDiagnostico(datos.diagnostico());
        this.setTratamiento(datos.tratamiento());
        this.setNotasMedicas(datos.notasMedicas());
        this.setCita(cita);
    }
}
