package co.edu.uniquindio.pokemonsql.entidades;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Gimnasio implements Serializable {

    @Id
    private String codigo;

    @Column(nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "gimnasio")
    private List<Combate> combates;
}
