package co.edu.uniquindio.unicine.entidades;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.List;

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

    @Column(nullable = false)
    private String producto;

    @Column(nullable = false)
    @Positive
    private Float precio;

    @Column(nullable = false)
    private String imagenURL;


    // --- Relaciones ---

    @ManyToMany(mappedBy = "confiterias")
    @ToString.Exclude
    private List<Compra> compras;

    @Builder
    public Confiteria(String producto, Float precio, String imagenURL) {
        this.producto = producto;
        this.precio = precio;
        this.imagenURL = imagenURL;
    }
}