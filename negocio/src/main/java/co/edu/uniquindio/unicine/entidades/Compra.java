package co.edu.uniquindio.unicine.entidades;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.time.LocalDate;
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
    private LocalDateTime fechaCompra;

    @Column(nullable = false)
    private LocalDate fechaFuncionCompra;

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

    @OneToOne(fetch = FetchType.LAZY)
    private CuponCliente cupon;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "compra")
    @ToString.Exclude
    private List<Boleta> boletas;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Funcion funcion;

    @Builder
    public Compra(LocalDate fechaFuncionCompra, MetodoPago metodo_pago, CuponCliente cupon, Cliente cliente, Funcion funcion) {
        this.fechaCompra = LocalDateTime.now();
        this.fechaFuncionCompra = fechaFuncionCompra;
        this.metodo_pago = metodo_pago;
        this.cupon = cupon;
        this.cliente = cliente;
        this.funcion = funcion;
    }
}
