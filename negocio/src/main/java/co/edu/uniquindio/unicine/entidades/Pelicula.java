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
public class Pelicula implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(nullable = false)
    private String nombre, imagenURL, trailerURL, sinopsis, reparto;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoPelicula estado;


    // --- Relaciones ---

    @OneToMany(mappedBy = "pelicula")
    @ToString.Exclude
    private List<Funcion> funciones;

    @ManyToMany
    @JoinColumn(nullable = false)
    private List<Genero> generos;

    @Builder
    public Pelicula(String nombre, String imagenURL, String trailerURL, String sinopsis, String reparto, EstadoPelicula estado, List<Genero> generos) {
        this.nombre = nombre;
        this.imagenURL = imagenURL;
        this.trailerURL = trailerURL;
        this.sinopsis = sinopsis;
        this.reparto = reparto;
        this.estado = estado;
        this.generos = generos;
    }
}
