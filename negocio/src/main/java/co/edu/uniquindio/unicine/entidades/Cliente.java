package co.edu.uniquindio.unicine.entidades;

import lombok.*;
import org.hibernate.validator.constraints.Length;

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

    @Length(max = 100)
    @Column(nullable = false, length = 100)
    private String nombre;

    @ElementCollection
    private List<String> telefonos;

    @NonNull // Restrinccion a nivel de java
    @Email
    @Column(nullable = false, unique = true) // Restrinccion a nivel de sql
    private String email;

    private String imagen_perfil;

    @ToString.Exclude // Porque es inseguro
    @Length(max = 40)
    @Column(nullable = false, length = 40)
    private String contrasenia;

    @Column(nullable = false)
    private Boolean estado;


    // --- Relaciones ---

    // A todos los OneToMany deben ser excluidos del ToString, así como del constructor
    @OneToMany(mappedBy = "cliente")
    @ToString.Exclude
    private List<CuponCliente> cupones;

    @OneToMany(mappedBy = "cliente")
    @ToString.Exclude
    private List<Compra> compras;

    @Builder
    public Cliente(Integer cedula, String nombre_completo, List<String> telefonos, String email, String imagen_perfil, String contrasenia) {
        this.cedula = cedula;
        this.nombre = nombre_completo;
        this.telefonos = telefonos;
        this.email = email;
        this.imagen_perfil = imagen_perfil;
        this.contrasenia = contrasenia;
        this.estado = false;
    }



}
