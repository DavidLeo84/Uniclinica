package co.edu.uniquindio.uniclinica.modelo.entidades;

import co.edu.uniquindio.uniclinica.modelo.enums.EstadoPago;
import co.edu.uniquindio.uniclinica.modelo.enums.MedioPago;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Pago implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codigo;

    @DateTimeFormat
    @Column(nullable = false)
    private LocalDateTime fecha;

    @Column(nullable = false)
    private float totalPagado;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private MedioPago medioPago;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private EstadoPago estadoPago;

    @OneToOne(mappedBy = "pago")
    private Transaccion transaccion;
}
