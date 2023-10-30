package co.edu.uniquindio.uniclinica.modelo.entidades;

import co.edu.uniquindio.uniclinica.dto.medicamento.RegistroMedicamentoDTO;
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
public class Medicamento implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codigo;

    @Column(length = 30, nullable = false)
    private String nombre;

    @ManyToOne
    private TipoMedicamento tipoMedicamento;

    @Column(nullable = false)
    private int unidades;

    @Column(nullable = false)
    private float precio;

    @ManyToMany(mappedBy = "medicamentos")
    private List<Transaccion> transacciones;
/*
    public Medicamento(RegistroMedicamentoDTO medicamentoDTO){
        this.getCodigo();
        this.setTipoMedicamento(medicamentoDTO.tipo());
        this.setNombre(medicamentoDTO.nombre());
        this.setUnidades(medicamentoDTO.unidades());
        this.setPrecio(medicamentoDTO.precio());
    }

 */

}
