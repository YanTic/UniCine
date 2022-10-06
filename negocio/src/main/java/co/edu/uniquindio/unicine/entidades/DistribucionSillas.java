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
public class DistribucionSillas implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(nullable = false)
    private Integer filas;

    @Column(nullable = false)
    private Integer columnas;

    @Column(nullable = false)
    private Integer totalSillas;

    @Column(nullable = false)
    private String rutaEsquema;

    // --- Relaciones ---

    @OneToMany(mappedBy = "distribucionSillas")
    @ToString.Exclude
    private List<Sala> salas;

    @Builder
    public DistribucionSillas(Integer filas, Integer columnas, String rutaEsquema, Integer totalSillas) {
        this.filas = filas;
        this.columnas = columnas;
        this.rutaEsquema = rutaEsquema;
        this.totalSillas = totalSillas;
    }
}
