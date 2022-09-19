package co.edu.uniquindio.biblioteca.entidades;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Libro implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    private String isbn;

    @Column(nullable = false, length = 100)
    private String nombre;

    @PositiveOrZero
    private int unidades;

    @PositiveOrZero
    private int anio;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Genero genero;

    // Como el prestamo es la entidad propietaria, el libro lleva el mappedBy
    @ManyToMany(mappedBy = "libros")
    private List<Prestamo> prestamos;

    @ManyToMany // Entidad propietaria, porque depende del autor
    private List<Autor> autores;

}
