package co.edu.uniquindio.unicine.entidades;

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
public class Sala implements Serializable {

    @Id
    private String id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoSala tipo;


    // --- Relaciones ---

    @ManyToOne
    private Teatro teatro;

    @OneToMany(mappedBy = "sala")
    private List<Funcion> funciones;

    @OneToMany(mappedBy = "sala")
    private List<Silla> sillas;
}
