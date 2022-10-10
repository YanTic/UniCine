package co.edu.uniquindio.unicine.test;

import co.edu.uniquindio.unicine.entidades.Administrador;
import co.edu.uniquindio.unicine.entidades.Boleta;
import co.edu.uniquindio.unicine.entidades.Compra;
import co.edu.uniquindio.unicine.entidades.CuponCliente;
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
public class CompraTest {

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
}
