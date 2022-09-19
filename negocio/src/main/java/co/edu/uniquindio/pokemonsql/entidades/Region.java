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
public class Region implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    private Integer codigo;

    @Column(length = 100, nullable = false)
    private String nombre;

    @Column(length = 255)
    private String descripcion;

    @OneToMany(mappedBy = "region")
    private List<Pokemon> pokemones;
}
