package co.edu.uniquindio.pokemonsql.entidades;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Equipo {

    @Id
    @EqualsAndHashCode.Include
    private String codigo;

    @ManyToOne
    private Entrenador entrenador;

    // No puedo usar el mappedBy porque no funciona la relacion
    @OneToOne
    private PokemonCapturado pokemonCapturado1;

    @OneToOne
    private PokemonCapturado pokemonCapturado2;

    @OneToOne
    private PokemonCapturado pokemonCapturado3;

    // No puedo usar el mappedBy porque  esta lista de combates señala a 2 equipos distintos
    @OneToMany
    private List<Combate> combates;

}
