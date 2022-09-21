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
public class Silla implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    private String id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoSilla estado;

    @Column(nullable = false)
    private String fila;

    @Column(nullable = false)
    private String columna;


    // --- Relaciones ---

    @ManyToOne
    private Sala sala;

    @ManyToOne
    private TipoSilla tipo;

    @OneToMany(mappedBy = "silla")
    private List<Boleta> boletas;
}
