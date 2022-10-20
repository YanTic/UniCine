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
@ToString
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
    @JoinColumn(nullable = false)
    private Ciudad ciudad;

    @OneToMany(mappedBy = "teatro")
    @ToString.Exclude
    private List<Evento> eventos;

    @OneToMany(mappedBy = "teatro")
    @ToString.Exclude
    private List<AdministradorGeneral> administradores;

    @OneToMany(mappedBy = "teatro")
    @ToString.Exclude
    private List<Sala> salas;

    @Builder
    public Teatro(String nombre, String direccion, Ciudad ciudad) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.ciudad = ciudad;
    }
}
