package co.edu.uniquindio.unicine.repo;

import co.edu.uniquindio.unicine.dto.InformacionCompraDTO;
import co.edu.uniquindio.unicine.entidades.Administrador;
import co.edu.uniquindio.unicine.entidades.Boleta;
import co.edu.uniquindio.unicine.entidades.Compra;
import co.edu.uniquindio.unicine.entidades.CuponCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompraRepo extends JpaRepository<Compra, Integer> {

    @Query("select compras from Cliente c, IN (c.compras) compras where c.email = :emailCliente")
    List<Compra> obtenerCompras(String emailCliente);

    @Query("select c from Compra c where c.cliente.email = :emailCliente")
    List<Compra> obtenerCompras2(String emailCliente);

    //Puedo usar JOIN o INNER JOIN -> Es lo mismo
    @Query("select compras from Cliente c JOIN c.compras compras where c.email = :emailCliente")
    List<Compra> obtenerCompras3(String emailCliente);

    @Query("select bol from Compra c JOIN c.boletas bol where c.id = :idCompra")
    List<Boleta> obtenerBoletas(Integer idCompra);

    /*@Query("select comp from Compra comp")*/
    @Query("select comp from Cliente c JOIN c.compras comp")
    List<Compra> obtenerComprasTodos();

    // Con LEFT JOIN, se imprimen tambien los cliente que no tengan compras
    // Si solo se usa JOIN, esos clientes no apareceran en la consulta
    // LEFT JOIN -> Conservo registros que están a la izquierda así no se relacionen con registros
    //              de la derecha. (Conversa todos los clientes (izq))
    @Query("select c.nombre, c.email, comp from Cliente c LEFT JOIN c.compras comp")
    List<Object[]> obtenerComprasTodos2();



    // Cree una consulta que permita determinar el número de cupones que han sido redimidos por cada cliente.
    // @Query("select count(comp.cupon) from Cliente c JOIN c.compras comp")
    @Query("select c.cedula, count(c) from Cliente c JOIN c.compras comp where comp.cupon is not null group by c")
    List<Object[]> obtenerCuponesRedimidosTodosClientes();


    // Cree una consulta que devuelva la compra más costosa que se ha hecho. Se debe devolver el
    // cliente y la compra.
    @Query("select comp.cliente, comp from Compra comp where comp.valorTotal = (select MAX(c.valorTotal) from Compra c)")
    List<Object[]> obtenerCompraMasCostosa();

    @Query("select comp.cliente.cedula, MAX(comp.valorTotal) from Compra comp group by comp.cliente")
    List<Object[]> obtenerCompraMasCostosa2();


    // Cree una consulta que devuelva una lista de compras de un cliente, la respuesta debe incluir el valor total, la
    // fecha de la compra, la función, el valor pagado por la confitería y el valor pagado por las entradas, por
    // separado. Use un DTO para una mejor respuesta.
    @Query("select new co.edu.uniquindio.unicine.dto.InformacionCompraDTO(comp.valorTotal, comp.fecha, comp.funcion, (select sum(conf.precio * conf.unidades) from ConfiteriaCompra conf where conf.compra.id = comp.id), (select sum(bol.precio) from Boleta bol where bol.compra.id = comp.id)) from Compra comp where comp.cliente.cedula = :idCliente")
    List<InformacionCompraDTO> obtenerInformacionCompra(Integer idCliente);


}
