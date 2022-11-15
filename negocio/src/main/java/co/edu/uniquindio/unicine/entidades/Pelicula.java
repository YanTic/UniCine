package co.edu.uniquindio.unicine.entidades;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

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

    @Column(nullable = false, unique = true, length = 200)
    private String nombre;

    @ElementCollection
    @Column(nullable = false)
    private Map<String, String> imagenes;

    @Column(nullable = false)
    private String trailerURL;

    @Lob
    @Column(nullable = false)
    private String sinopsis, reparto;

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
    public Pelicula(String nombre, String trailerURL, String sinopsis, String reparto, EstadoPelicula estado, List<Genero> generos) {
        this.nombre = nombre;
        this.trailerURL = trailerURL;
        this.sinopsis = sinopsis;
        this.reparto = reparto;
        this.estado = estado;
        this.generos = generos;
    }

    public String getImagenPrincipal() {
        if (!imagenes.isEmpty()) {
            String primeraImg = imagenes.keySet().toArray()[0].toString();
            return imagenes.get(primeraImg);
        }
        return "";
    }
}
