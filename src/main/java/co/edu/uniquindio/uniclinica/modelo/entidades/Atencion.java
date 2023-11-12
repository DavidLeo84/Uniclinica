package co.edu.uniquindio.uniclinica.modelo.entidades;

import co.edu.uniquindio.uniclinica.dto.medico.RegistroAtencionDTO;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int codigo;

    @Lob
    private String diagnostico;

    @Lob
    private String tratamiento;

    @Lob
    private String notasMedicas;

    private float precio;

    @OneToOne(mappedBy = "atencion")
    private Transaccion transaccion;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Cita cita;

    /*
    public Atencion(RegistroAtencionDTO datos, Cita cita){
        this.setDiagnostico(datos.diagnostico());
        this.setTratamiento(datos.tratamiento());
        this.setNotasMedicas(datos.notasMedicas());
        this.setPrecio(datos.precio());
        this.setCita(cita);
    }

    public void actualizar(DetalleAtencionMedicaDTO datos, Cita cita) {
        this.setDiagnostico(datos.diagnostico());
        this.setTratamiento(datos.tratamiento());
        this.setNotasMedicas(datos.notasMedicas());
        this.setCita(cita);
    }

    */
}
