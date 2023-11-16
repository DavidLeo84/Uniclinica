package co.edu.uniquindio.uniclinica.modelo.entidades;

import co.edu.uniquindio.uniclinica.dto.administrador.RegistroRespuestaDTO;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Inheritance(strategy = InheritanceType.JOINED)
public class Mensaje implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codigo;

    @Column(nullable = false)
    private LocalDateTime fechaMensaje;

    private String tipo;

    @Lob
    private String contenido;

    @OneToOne(fetch = FetchType.LAZY)
    private Mensaje mensaje;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Cuenta cuenta;

    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Pqrs pqrs;

    /*
    public Mensaje(RegistroRespuestaDTO datos, LocalDateTime fecha, Pqrs pqrs, Cuenta cuenta) {
        this.setContenido(datos.mensaje());
        this.setFechaMensaje(fecha);
        this.setPqrs(pqrs);
        this.setCuenta(cuenta);
    }

     */
}
