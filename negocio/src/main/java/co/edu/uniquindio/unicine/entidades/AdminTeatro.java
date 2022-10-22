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
public class AdminTeatro implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    private String cedula;

    @Column(nullable = false)
    private String nombre;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @ToString.Exclude
    @Column(nullable = false)
    private String contrasenia;


    // --- Relaciones ---

    @ManyToOne
    @JoinColumn(nullable = false)
    private Teatro teatro;

    @Builder
    public AdminTeatro(String cedula, String nombre, String email, String contrasenia, Teatro teatro) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.email = email;
        this.contrasenia = contrasenia;
        this.teatro = teatro;
    }
}
