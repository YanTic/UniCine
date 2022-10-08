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


    @Builder
    public Boleta(Float precio, String fila, String columna, TipoSilla tipo, Compra compra) {
        this.precio = precio;
        this.fila = fila;
        this.columna = columna;
        this.tipo = tipo;
        this.compra = compra;
    }

}