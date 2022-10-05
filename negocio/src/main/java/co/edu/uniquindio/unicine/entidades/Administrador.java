package co.edu.uniquindio.unicine.entidades;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
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

    @Email
    @Column(nullable = false)
    private String correo;

    @Column(nullable = false)
    private String contrasenia;


    // --- Relaciones ---

    @ManyToOne
    @JoinColumn(nullable = false)
    private Teatro teatro;

    @Builder
    public Administrador(String cedula, String nombre, String correo, String contrasenia, Teatro teatro) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasenia = contrasenia;
        this.teatro = teatro;
    }
}
