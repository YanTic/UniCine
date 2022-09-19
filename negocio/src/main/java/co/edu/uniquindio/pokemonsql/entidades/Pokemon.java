package co.edu.uniquindio.pokemonsql.entidades;

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
public class Pokemon implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    private Integer numero;

    @Column(length = 100, nullable = false)
    private String nombre;

    private String foto;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoPokemon tipo;

    @ManyToOne
    private Region region;

    @OneToMany(mappedBy = "pokemon")
    private List<PokemonCapturado> pokemonCapturados;

}
