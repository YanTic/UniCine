package co.edu.uniquindio.unicine.entidades;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Pelicula implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(nullable = false)
    private String nombre, imagenURL, trailerURL, sinpsis, reparto;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoPelicula estado;


    // --- Relaciones ---

    @OneToMany(mappedBy = "pelicula")
    private List<Funcion> funciones;

    @ManyToMany
    private List<Genero> generos;
}
