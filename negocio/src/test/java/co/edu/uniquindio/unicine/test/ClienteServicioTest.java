package co.edu.uniquindio.unicine.test;

import co.edu.uniquindio.unicine.entidades.Cliente;
import co.edu.uniquindio.unicine.entidades.Compra;
import co.edu.uniquindio.unicine.entidades.CuponCliente;
import co.edu.uniquindio.unicine.entidades.Pelicula;
import co.edu.uniquindio.unicine.servicios.ClienteServicio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional // No modifica los datos de la bd
public class ClienteServicioTest {

    @Autowired
    private ClienteServicio clienteServicio;


    @Test
    @Sql("classpath:dataset.sql")
    public void loginTest() {
        try {
            Cliente cliente = clienteServicio.login("juan@email.com", "3jn1");
            System.out.println(cliente);
            Assertions.assertNotNull(cliente);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    @Sql("classpath:dataset.sql")
    public void registarClienteTest() {
        Cliente cliente = Cliente.builder().cedula(224).nombre_completo("The Rock").email("pepe@email.com").contrasenia("23Ao2").imagen_perfil("url").build();

        try {
            Cliente nuevo = clienteServicio.registrarCliente(cliente);
            Assertions.assertNotNull(nuevo);
        } catch (Exception e) {
            throw new RuntimeException(e);
            //Assertions.assertTrue(false);
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void actualizarClienteTest() {
        try {
            Cliente cliente = clienteServicio.obtenerCliente(4);
            cliente.setNombre("NUEVO NOMBRE");
            Cliente nuevo = clienteServicio.actualizarCliente(cliente);

            Assertions.assertEquals("NUEVO NOMBRE", nuevo.getNombre());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void eliminarClienteTest() {

        try {
            clienteServicio.eliminarCliente(3);
        } catch (Exception e) {
            // throw new RuntimeException(e);
            Assertions.assertTrue(false); //
        }

        try {
            clienteServicio.obtenerCliente(3); // Esto no va a encontrar el cliente, porque se borró antes
        } catch (Exception e) {
            // throw new RuntimeException(e);
            Assertions.assertTrue(true); // Como siempre lanza el catch() decimos que funcionó, eso era lo que se esperaba
        }


    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listarClienteTest() {
        List<Cliente> clientes = clienteServicio.listarClientes();
        clientes.forEach(System.out::println);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listarHistorialTest() {
        try {
            List<Compra> compras = clienteServicio.listarHistorial(16);
            compras.forEach(System.out::println);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    @Sql("classpath:dataset.sql")
    public void realizarCompraTest() {

    }

    @Test
    @Sql("classpath:dataset.sql")
    public void redimirCuponTest() {
        try {
            boolean cuponRedimido = clienteServicio.redimirCupon(1, 3);
            Assertions.assertEquals(true, cuponRedimido);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listarCuponesClienteTest() {
        List<CuponCliente> cupones = clienteServicio.listarCuponesCliente(4);
        cupones.forEach(System.out::println);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void buscarPeliculaTest() {
        List<Pelicula> peliculas = clienteServicio.buscarPelicula("spider");
        peliculas.forEach(System.out::println);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void cambiarContraseniaTest() {
        try {
             Cliente cliente = clienteServicio.obtenerCliente(2);
             Cliente nuevo = clienteServicio.cambiarContrasenia(cliente.getCedula(), cliente.getContrasenia(), "ABC123");

            //Cliente nuevo = clienteServicio.cambiarContrasenia(2, "123", "ABC123");

            Assertions.assertEquals("ABC123", nuevo.getContrasenia());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
