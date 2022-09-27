package co.edu.uniquindio.unicine.entidades;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
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

    @Builder
    public Administrador(String cedula, String nombre, String contrasenia, Teatro teatro) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.contrasenia = contrasenia;
        this.teatro = teatro;
    }
}
