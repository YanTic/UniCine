package co.edu.uniquindio.pokemonsql.entidades;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Combate {

    @Id
    @EqualsAndHashCode.Include
    private String codigo;

    @Column(nullable = false)
    private LocalDate fecha;

    // No puedo usar el mappedBy porque la relacion no funciona
    @ManyToOne
    private Equipo equipo1;

    @ManyToOne
    private Equipo equipo2;

    @ManyToOne
    private Gimnasio gimnasio;
}
