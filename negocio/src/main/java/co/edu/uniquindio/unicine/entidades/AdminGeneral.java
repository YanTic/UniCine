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
public class AdminGeneral implements Serializable {

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
    private Ciudad ciudad;

    @Builder
    public AdminGeneral(String cedula, String nombre, String email, String contrasenia, Ciudad ciudad) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.email = email;
        this.contrasenia = contrasenia;
        this.ciudad = ciudad;
    }
}
