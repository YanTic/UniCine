package co.edu.uniquindio.unicine.entidades;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Cliente implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    private Integer cedula;

    @Column(nullable = false)
    private String nombre_completo;

    @ElementCollection
    private List<String> telefonos;

    @Email
    @Column(nullable = false)
    private String email;

    private String imagen_perfil;

    @Column(nullable = false)
    private String contrasenia;


    // --- Relaciones ---

    @OneToMany(mappedBy = "cliente")
    private List<Cupon> cupones;

    @OneToMany(mappedBy = "cliente")
    private List<Compra> compras;
}
