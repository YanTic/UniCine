package co.edu.uniquindio.pokemonsql.entidades;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Entrenador implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    private Integer codigo;

    @Column(length = 100, nullable = false)
    private String nombre;

    @Column(length = 100, nullable = false)
    private String username;

    @Column(nullable = false)
    private LocalDate fecha_nacimiento;

    @Column(nullable = false)
    private String avatar;

    @OneToMany(mappedBy = "entrenador")
    private List<PokemonCapturado> pokemonCapturados;

    @OneToMany(mappedBy = "entrenador")
    private List<Equipo> equipos;
}
