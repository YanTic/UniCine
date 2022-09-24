package co.edu.uniquindio.unicine.entidades;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Horario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(nullable = false)
    private LocalTime hora_inicio;

    @Column(nullable = false)
    private LocalTime hora_fin;

    @Column(nullable = false)
    private LocalDate fecha;


    // --- Relaciones ---

    @OneToMany(mappedBy = "horario")
    private List<Funcion> funciones;

    @Builder
    public Horario(LocalTime hora_inicio, LocalTime hora_fin, LocalDate fecha) {
        this.hora_inicio = hora_inicio;
        this.hora_fin = hora_fin;
        this.fecha = fecha;
    }
}
