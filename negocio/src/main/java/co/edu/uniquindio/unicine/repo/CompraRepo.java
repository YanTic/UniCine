package co.edu.uniquindio.unicine.repo;

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
}
