package co.edu.uniquindio.unicine.entidades;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TipoSilla implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false)
    @Positive
    private Float precio;


    // --- Relaciones ---

    @OneToMany(mappedBy = "tipo")
    private List<Silla> sillas;

    @Builder
    public TipoSilla(String nombre, Float precio) {
        this.nombre = nombre;
        this.precio = precio;
    }
}
