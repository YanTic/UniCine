package co.edu.uniquindio.unicine.entidades;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Cupon implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(nullable = false)
    private Float valor_descuento;

    @Column(nullable = false)
    private LocalDate fecha_vencimiento;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private String criterio;


    // --- Relaciones ---

    @OneToOne(mappedBy = "cupon")
    @ToString.Exclude
    private Compra compra;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Cliente cliente;

    @Builder
    public Cupon(Float valor_descuento, LocalDate fecha_vencimiento, String descripcion, String criterio, Cliente cliente) {
        this.valor_descuento = valor_descuento;
        this.fecha_vencimiento = fecha_vencimiento;
        this.descripcion = descripcion;
        this.criterio = criterio;
        this.cliente = cliente;
    }
}
