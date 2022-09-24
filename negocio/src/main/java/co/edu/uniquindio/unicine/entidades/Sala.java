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
public class Sala implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

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

    @Builder
    public Sala(TipoSala tipo, Teatro teatro) {
        this.tipo = tipo;
        this.teatro = teatro;
    }
}
