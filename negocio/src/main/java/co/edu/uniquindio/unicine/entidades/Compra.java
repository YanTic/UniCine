package co.edu.uniquindio.unicine.entidades;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Compra implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MetodoPago metodo_pago;

    @Column(nullable = false)
    @Positive
    private Float valorTotal;


    // --- Relaciones ---

    @OneToMany(mappedBy = "compra")
    @ToString.Exclude
    private List<ConfiteriaCompra> confiteriaCompras;

    @OneToOne
    @JoinColumn(nullable = true)
    private CuponCliente cupon;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "compra")
    @ToString.Exclude
    private List<Boleta> boletas;

    @Builder
    public Compra(MetodoPago metodo_pago, List<ConfiteriaCompra> confiteriaCompras, CuponCliente cupon, Cliente cliente) {
        this.fecha = LocalDateTime.now();
        this.metodo_pago = metodo_pago;
        this.confiteriaCompras = confiteriaCompras;
        this.cupon = cupon;
        this.cliente = cliente;
    }
}
