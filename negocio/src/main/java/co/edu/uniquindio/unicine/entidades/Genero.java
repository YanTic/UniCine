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
public class Genero implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(nullable = false)
    private String nombre;


    // --- Relaciones ---

    @ManyToMany(mappedBy = "generos")
    @ToString.Exclude
    private List<Pelicula> peliculas;

    @Builder
    public Genero(String nombre) {
        this.nombre = nombre;
    }
}
