package co.edu.uniquindio.unicine.entidades;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Confiteria implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(nullable = false, unique = true)
    private String producto;

    @Column(nullable = false)
    @Positive
    private Float precio;

    @ElementCollection
    @Column(nullable = false)
    private Map<String, String> imagenes;


    // --- Relaciones ---

    @OneToMany(mappedBy = "confiteria")
    @ToString.Exclude
    private List<ConfiteriaCompra> confiteriaCompras;

    @Builder
    public Confiteria(String producto, Float precio) {
        this.producto = producto;
        this.precio = precio;
    }

    public String getImagenPrincipal() {
        if (!imagenes.isEmpty()) {
            String primeraImg = imagenes.keySet().toArray()[0].toString();
            return imagenes.get(primeraImg);
        }
        return "";
    }
}
