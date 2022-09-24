package co.edu.uniquindio.unicine.entidades;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Teatro implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String direccion;


    // --- Relaciones ---

    @ManyToOne
    private Ciudad ciudad;

    @OneToMany(mappedBy = "teatro")
    private List<Evento> eventos;

    @OneToMany(mappedBy = "teatro")
    private List<Funcion> funciones;

    @OneToMany(mappedBy = "teatro")
    private List<Administrador> administradores;

    @OneToMany(mappedBy = "teatro")
    private List<Sala> salas;

    @Builder
    public Teatro(String nombre, String direccion, Ciudad ciudad) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.ciudad = ciudad;
    }
}
