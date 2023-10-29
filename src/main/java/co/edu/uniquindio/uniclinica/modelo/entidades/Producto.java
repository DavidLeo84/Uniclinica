package co.edu.uniquindio.uniclinica.modelo.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Producto implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codigo;

    @Column(length = 30, nullable = false)
    private String nombre;

    @ManyToOne
    private TipoProducto tipoProducto;

    @Column(nullable = false)
    private int unidades;

    @Column(nullable = false)
    private float precio;

    @ManyToMany(mappedBy = "productos")
    private List<Transaccion> transacciones;

}
