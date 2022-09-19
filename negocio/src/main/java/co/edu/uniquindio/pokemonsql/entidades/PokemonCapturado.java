package co.edu.uniquindio.pokemonsql.entidades;

import lombok.*;

import javax.annotation.security.DenyAll;
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
public class PokemonCapturado implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    private Integer codigo;

    @Column(length = 100, nullable = false)
    private String nombre;

    @Column(nullable = false)
    private int salud, nivel, ataque, defensa;

    private String ataque_especial;

    private String defensa_especial;

    @ManyToOne
    private Pokemon pokemon;

    @ManyToMany
    private List<Movimiento> movimientos;

    @ManyToOne
    private Entrenador entrenador;

    @OneToOne
    private Equipo equipo;
}
