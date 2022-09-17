package co.edu.uniquindio.unicine.entidades;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Cliente extends Persona implements Serializable {

    @Email
    @Column(length = 150, nullable = false, unique = true)
    private String email;

    @ElementCollection
    private Map<String,String> telefonos;

    /*@ElementCollection
    private List<String> telefonos;*/

    @ManyToOne // Muchas personas pertenecen a una ciudad
    private Ciudad ciudad;

    @OneToMany(mappedBy = "cliente")
    private List<Prestamo> prestamos;

}
