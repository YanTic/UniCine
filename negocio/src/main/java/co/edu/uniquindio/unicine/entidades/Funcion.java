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
@AllArgsConstructor
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
    private Teatro teatro;

    @ManyToOne
    private Horario horario;

    @ManyToOne
    private Pelicula pelicula;

    @ManyToOne
    private Sala sala;

    @OneToMany(mappedBy = "funcion")
    private List<Boleta> boletas;
}
