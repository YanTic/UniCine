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
public class Movimiento implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    private String codigo;

    @Column(nullable = false)
    private String nombre;

    @ManyToMany(mappedBy = "movimientos")
    private List<PokemonCapturado> pokemonCapturados;
}
