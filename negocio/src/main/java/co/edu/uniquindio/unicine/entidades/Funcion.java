package co.edu.uniquindio.unicine.entidades;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Funcion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(nullable = false)
    @Positive
    private Float precio;


    // --- Relaciones ---

    @ManyToOne
    @JoinColumn(nullable = false)
    private Teatro teatro;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Horario horario;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Pelicula pelicula;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Sala sala;

    @OneToMany(mappedBy = "funcion")
    @ToString.Exclude
    private List<Boleta> boletas;

    @Builder
    public Funcion(Float precio, Teatro teatro, Horario horario, Pelicula pelicula, Sala sala) {
        this.precio = precio;
        this.teatro = teatro;
        this.horario = horario;
        this.pelicula = pelicula;
        this.sala = sala;
    }
}
