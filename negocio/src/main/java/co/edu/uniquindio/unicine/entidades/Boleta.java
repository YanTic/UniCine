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

    @Column(nullable = false, length = 30)
    private String fila;

    @Column(nullable = false, length = 30)
    private String columna;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoSilla tipo;


    // --- Relaciones ---

    @ManyToOne
    @JoinColumn(nullable = false)
    private Compra compra;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Funcion funcion;


    @Builder
    public Boleta(Float precio, Compra compra, Funcion funcion, TipoSilla tipo) {
        this.precio = precio;
        this.compra = compra;
        this.funcion = funcion;
        this.tipo = tipo;
    }
}
