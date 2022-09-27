package co.edu.uniquindio.unicine.entidades;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Boleta implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    @ManyToOne
    private Compra compra;

    @Id
    @EqualsAndHashCode.Include
    @ManyToOne
    private Funcion funcion;

    @ManyToOne
    private Silla silla;

    @Builder
    public Boleta(Compra compra, Funcion funcion, Silla silla) {
        this.compra = compra;
        this.funcion = funcion;
        this.silla = silla;
    }
}
