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
public class Ciudad implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(nullable = false)
    private String nombre;


    // --- Relaciones ---

    // SIEMPRE QUE SE VEA LA ANOTACION @ONETOMANY NUNCA!! SE DEBE METER EN EL CONSTRUCTOR
    // EN CASO DE ONETOONE, EL QUE LLEVA EL MAPPEDBY NUNCA SE PONE EN EL CONSTRUCTOR.
    @OneToMany(mappedBy = "ciudad")
    private List<Teatro> teatros;

    @Builder
    public Ciudad(String nombre) {
        this.nombre = nombre;
    }
}
