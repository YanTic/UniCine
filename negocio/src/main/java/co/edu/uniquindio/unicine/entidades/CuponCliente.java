package co.edu.uniquindio.unicine.entidades;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class CuponCliente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(nullable = false)
    private Boolean estado;
    // Digamos que 1 es redimido y 0 es sin redimir


    // --- Relaciones ---

    @ManyToOne
    @JoinColumn(nullable = false)
    private Cupon cupon;

    //cascade=CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY

    // fetch = FetchType.Lazy   -->
    // tanto en este @OneToOne como en el otro (de compra hacia el CuponCliente) evita que Hibernate
    // lanze la excepcion -> Hibernate : More than one row with the given identifier was found.
    // Que significa que hibernate está buscando el objeto y como tiene relaciones asociadas a esa
    // entidad supone que tiene varias filas (objetos) duplicados por lo que lanza el error
    // entonces usando el FetchType.Lazy le decimos que esa relacion OneToOne no contiene filas
    // duplicadas. (O eso entedí yo, segun StackOverFlow es para que busque más despacio la relacion)
    @OneToOne(mappedBy = "cupon", fetch = FetchType.LAZY)
    @ToString.Exclude
    private Compra compra;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Cliente cliente;


    @Builder
    public CuponCliente(Boolean estado, Cupon cupon, Cliente cliente) {
        this.estado = estado;
        this.cupon = cupon;
        this.cliente = cliente;
    }
}
