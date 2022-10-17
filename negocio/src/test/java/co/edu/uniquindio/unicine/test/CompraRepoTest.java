package co.edu.uniquindio.unicine.test;

import co.edu.uniquindio.unicine.dto.InformacionCompraDTO;
import co.edu.uniquindio.unicine.entidades.Boleta;
import co.edu.uniquindio.unicine.entidades.Compra;
import co.edu.uniquindio.unicine.repo.CompraRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CompraRepoTest {

    @Autowired
    private CompraRepo compraRepo;

    @Test
    @Sql("classpath:dataset.sql")
    public void obtenerCompras(){
        List<Compra> compras = compraRepo.obtenerCompras3("pepe@email.com");

        compras.forEach(System.out::println);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void obtenerBoletas(){
        List<Boleta> boletas = compraRepo.obtenerBoletas(4);
        boletas.forEach(System.out::println);

        Assertions.assertEquals(2, boletas.size());
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void obtenerComprasPorCliente(){
        List<Compra> compras = compraRepo.obtenerComprasTodos();

        compras.forEach(System.out::println);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void obtenerComprasPorCliente2(){
        List<Object[]> compras = compraRepo.obtenerComprasTodos2();

        compras.forEach( o ->
                System.out.println(o[0] + ", "+ o[1] + ", "+ o[2])
        );
    }


    // Cree una consulta que permita determinar el número de cupones que han sido redimidos por cada cliente.
    @Test
    @Sql("classpath:dataset.sql")
    public void obtenerCuponesRedimidosPorCliente(){
        List<Object[]> cuponesRedimidosPorCliente = compraRepo.obtenerCuponesRedimidosTodosClientes();

        cuponesRedimidosPorCliente.forEach( o ->
                System.out.println(o[0] + ": "+ o[1])
        );

    }

    // Cree una consulta que devuelva la compra más costosa que se ha hecho. Se debe devolver el
    // cliente y la compra.
    @Test
    @Sql("classpath:dataset.sql")
    public void obtenerCompraMasCostosa(){
        List<Object[]> CompraMasCostosa = compraRepo.obtenerCompraMasCostosa();
        //List<Object[]> CompraMasCostosa2 = compraRepo.obtenerCompraMasCostosa2();

        CompraMasCostosa.forEach( o ->
                System.out.println(o[0] + ": "+ o[1])
        );

    }

    // Cree una consulta que devuelva una lista de compras de un cliente, la respuesta debe incluir el valor total, la
    // fecha de la compra, la función, el valor pagado por la confitería y el valor pagado por las entradas, por
    // separado. Use un DTO para una mejor respuesta.
    @Test
    @Sql("classpath:dataset.sql")
    public void obtenerInformacionCompra(){
        List<InformacionCompraDTO> infoCompra = compraRepo.obtenerInformacionCompra(1);
        infoCompra.forEach(System.out::println);
    }
}
