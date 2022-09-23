package co.edu.uniquindio.unicine.entidades;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Administrador implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    private String cedula;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String contrasenia;


    // --- Relaciones ---

    @ManyToOne
    private Teatro teatro;

}
