package co.edu.uniquindio.unicine.dto;

import co.edu.uniquindio.unicine.entidades.Funcion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@ToString
public class InformacionCompraDTO {
    // comp.valorTotal, comp.fecha, comp.funcion,
    // (select sum(conf.precio * conf.unidades) from ConfiteriaCompra conf where conf.compra.id = comp.id),
    // (select sum(bol.precio) from Boleta bol where bol.compra.id = comp.id)

    private Float valorTotal;
    private LocalDateTime fechaCompra;
    private Funcion funcion;
    private double precioConfiterias;
    private double precioBoletas;
}
