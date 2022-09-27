package co.edu.uniquindio.unicine.entidades;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Evento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(nullable = false)
    private String nombre, imagenURL;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private LocalTime hora_inicio, hora_fin;


    // --- Relaciones ---

    @ManyToOne
    private Teatro teatro;

    @Builder
    public Evento(String nombre, String imagenURL, LocalDate fecha, LocalTime hora_inicio, LocalTime hora_fin, Teatro teatro) {
        this.nombre = nombre;
        this.imagenURL = imagenURL;
        this.fecha = fecha;
        this.hora_inicio = hora_inicio;
        this.hora_fin = hora_fin;
        this.teatro = teatro;
    }
}
