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

    // A todos los OneToMany deben ser excluidos del ToString, así como del constructor
    @ToString.Exclude
    @OneToMany(mappedBy = "cliente")
    private List<Cupon> cupones;

    @ToString.Exclude
    @OneToMany(mappedBy = "cliente")
    private List<Compra> compras;

    @Builder
    public Cliente(Integer cedula, String nombre_completo, List<String> telefonos, String email, String imagen_perfil, String contrasenia) {
        this.cedula = cedula;
        this.nombre_completo = nombre_completo;
        this.telefonos = telefonos;
        this.email = email;
        this.imagen_perfil = imagen_perfil;
        this.contrasenia = contrasenia;
    }



}
