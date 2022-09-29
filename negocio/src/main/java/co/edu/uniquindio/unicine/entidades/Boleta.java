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
public class Boleta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Positive
    @Column(nullable = false)
    private Float precio;


    // --- Relaciones ---

    @ManyToOne
    private Compra compra;

    @ManyToOne
    private Funcion funcion;

    @ManyToOne
    private Silla silla;

    @Builder
    public Boleta(Float precio, Compra compra, Funcion funcion, Silla silla) {
        this.precio = precio;
        this.compra = compra;
        this.funcion = funcion;
        this.silla = silla;
    }
}
