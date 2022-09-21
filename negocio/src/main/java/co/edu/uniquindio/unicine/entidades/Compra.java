package co.edu.uniquindio.unicine.entidades;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Compra implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    private String id;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Column(nullable = false)
    private String metodo_pago;

    @Column(nullable = false)
    private String valor_total;


    // --- Relaciones ---

    @ManyToMany
    private List<Confiteria> confiterias;

    @OneToOne
    private Cupon cupon;

    @ManyToOne
    private Cliente cliente;

    @OneToMany(mappedBy = "compra")
    private List<Boleta> boletas;
}
