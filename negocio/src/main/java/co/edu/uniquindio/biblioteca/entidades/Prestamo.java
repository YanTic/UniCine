package co.edu.uniquindio.biblioteca.entidades;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Prestamo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer codigo;

    @Column(nullable = false)
    private LocalDateTime fecha_prestamo;

    @Column(nullable = false)
    private LocalDate fecha_devolucion; // No importa la hora

    @ManyToOne
    private Cliente cliente;

    // El prestamo es la entidad propietaria, porque para que el prestamo exista,
    // el libro ya debe de existir
    @ManyToMany
    private List<Libro> libros;
}
