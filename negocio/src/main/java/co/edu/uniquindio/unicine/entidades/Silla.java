package co.edu.uniquindio.unicine.entidades;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Silla implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoSilla estado;

    @Column(nullable = false)
    private String fila;

    @Column(nullable = false)
    private String columna;


    // --- Relaciones ---

    @ManyToOne
    private Sala sala;

    @ManyToOne
    private TipoSilla tipo;

    @OneToMany(mappedBy = "silla")
    @ToString.Exclude
    private List<Boleta> boletas;

    @Builder
    public Silla(EstadoSilla estado, String fila, String columna, Sala sala, TipoSilla tipo) {
        this.estado = estado;
        this.fila = fila;
        this.columna = columna;
        this.sala = sala;
        this.tipo = tipo;
    }
}
