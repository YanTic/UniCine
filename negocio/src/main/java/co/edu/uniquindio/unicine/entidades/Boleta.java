package co.edu.uniquindio.unicine.entidades;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Boleta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Positive
    @Column(nullable = false)
    private Float precio;

    @Column(nullable = false, length = 30)
    private String fila;

    @Column(nullable = false, length = 30)
    private String columna;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoSilla tipo;


    // --- Relaciones ---

    @ManyToOne
    @JoinColumn(nullable = false)
    private Compra compra;

    private float obtenerPrecioBoleta(TipoSilla tipo, Funcion funcion) {
        float precioBoleta = 0;

        if(TipoSilla.ESTANDAR == tipo){
            precioBoleta = funcion.getPrecio() * 1;
        }
        if(TipoSilla.VIP == tipo){
            precioBoleta = (float) (funcion.getPrecio() * 1.2);
        }
        if(TipoSilla.DISCAPACITADO == tipo){
            precioBoleta = (float) (funcion.getPrecio() * 0.8);
        }

        return precioBoleta;
    }


    // Aunque Compra puede ser agregado al builder al no tener el mappedBy es mejor dejar por fuera.
    // Ya que al crear boletas primero se debería crear una compra la cual tiene que ser asignada a la
    // boleta despues de validar todas sus restrincciones, para luego agregarle la compra y asignar el
    // valor de la boleta con el precio de la funcion y el tipo de boleta
    @Builder
    public Boleta(String fila, String columna, TipoSilla tipo) {
        this.fila = fila;
        this.columna = columna;
        this.tipo = tipo;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
        this.precio = obtenerPrecioBoleta(tipo, compra.getFuncion());
    }
}