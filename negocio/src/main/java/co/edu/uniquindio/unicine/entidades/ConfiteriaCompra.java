package co.edu.uniquindio.unicine.entidades;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class ConfiteriaCompra implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Positive
    @Column(nullable = false)
    private Float precio;

    @Positive
    @Column(nullable = false)
    private Integer unidades;

    // --- Relaciones ---

    @ManyToOne
    private Confiteria confiteria;

    @ManyToOne
    private Compra compra;


    @Builder
    public ConfiteriaCompra(Integer unidades, Confiteria confiteria, Compra compra) {
        this.unidades = unidades;
        this.confiteria = confiteria;
        this.compra = compra;
    }
}
