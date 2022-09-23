package co.edu.uniquindio.unicine.entidades;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Cupon implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(nullable = false)
    private Integer valor_descuento;

    @Column(nullable = false)
    private LocalDate fecha_vencimiento;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private String criterio;


    // --- Relaciones ---

    @OneToOne(mappedBy = "cupon")
    private Compra compra;

    @ManyToOne
    private Cliente cliente;
}
