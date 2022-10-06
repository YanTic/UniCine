package co.edu.uniquindio.unicine.entidades;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

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

    @OneToMany(mappedBy = "cupon")
    @ToString.Exclude
    private List<CuponCliente> cuponesClientes;


    @Builder
    public Cupon(Float valor_descuento, LocalDate fecha_vencimiento, String descripcion, String criterio) {
        this.valor_descuento = valor_descuento;
        this.fecha_vencimiento = fecha_vencimiento;
        this.descripcion = descripcion;
        this.criterio = criterio;
    }
}
