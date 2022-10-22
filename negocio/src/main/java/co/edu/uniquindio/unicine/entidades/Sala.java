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
@ToString
public class Sala implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoSala tipo;


    // --- Relaciones ---

    @ManyToOne
    @JoinColumn(nullable = false)
    private Teatro teatro;

    @OneToMany(mappedBy = "sala")
    @ToString.Exclude
    private List<Funcion> funciones;

    @ManyToOne
    @JoinColumn(nullable = false)
    private DistribucionSillas distribucionSillas;

    @Builder
    public Sala(String nombre, TipoSala tipo, Teatro teatro, DistribucionSillas distribucionSillas) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.teatro = teatro;
        this.distribucionSillas = distribucionSillas;
    }
}
