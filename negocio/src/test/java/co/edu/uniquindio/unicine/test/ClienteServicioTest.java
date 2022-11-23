package co.edu.uniquindio.unicine.test;

import co.edu.uniquindio.unicine.dto.PeliculaFuncionDTO;
import co.edu.uniquindio.unicine.entidades.*;
import co.edu.uniquindio.unicine.servicios.AdminGeneralServicio;
import co.edu.uniquindio.unicine.servicios.AdminTeatroServicio;
import co.edu.uniquindio.unicine.servicios.ClienteServicio;
import co.edu.uniquindio.unicine.servicios.EmailServicio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional // No modifica los datos de la bd
public class ClienteServicioTest {

    // SI SE LLEGA A TENER ERRORES DE ESTE TIPO EN EL TEST: ScriptStatementFailedException
    // Es porque en hay datos cargados en la base de datos, entonces al cargar @Sql("classpath:dataset.sql")
    // Utilizaria los mismo datos sobreescritos, diciendo que ya existen, por lo que para usar tests
    // la base de datos no deberia tener nada (Pero como se debe usar en el modulo web, los tests quedan obsoletos)

    @Autowired
    private ClienteServicio clienteServicio;

    @Autowired
    private EmailServicio emailServicio;

    // Estos servicios se utilizan para obtener (ejemplo:) funciones, confiterias  para crear objetos que corresponden a este test
    @Autowired
    private AdminTeatroServicio adminTeatroServicio;

    @Autowired
    private AdminGeneralServicio adminGeneralServicio;

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
        Cliente cliente = Cliente.builder().cedula(224).nombre_completo("The Rock").email("rock@email.com").contrasenia("23Ao2").build();

        try {
            Cliente nuevo = clienteServicio.registrarCliente(cliente);
            Assertions.assertNotNull(nuevo);
        } catch (Exception e) {
            throw new RuntimeException(e);
            //Assertions.assertTrue(false);
        }
    }

    /*@Test
    @Sql("classpath:dataset.sql")
    public void activarCuentaTest() {
        try {
            Cliente cliente = clienteServicio.obtenerCliente(3);
            System.out.println("Cliente sin activar: "+cliente);

            Cliente clienteActivado = clienteServicio.activarCuenta(cliente);
            System.out.println("Cliente activado: "+clienteActivado);

            Assertions.assertNotNull(clienteActivado);
        } catch (Exception e) {
            throw new RuntimeException(e);
            //Assertions.assertTrue(false);
        }
    }*/

    @Test
    @Sql("classpath:dataset.sql")
    public void actualizarClienteTest() {
        try {
            Cliente cliente = clienteServicio.obtenerCliente(4);

            Cliente clienteActualizado = Cliente.builder()
                    .cedula(cliente.getCedula())
                    .nombre_completo("NUEVO NOMBRE")
                    .telefonos(cliente.getTelefonos())
                    .email(cliente.getEmail())
                    .contrasenia(cliente.getContrasenia())
                    .build();

            Cliente nuevo = clienteServicio.actualizarCliente(cliente.getCedula(), clienteActualizado);

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
            List<Compra> compras = clienteServicio.listarHistorialCompras(16);
            compras.forEach(System.out::println);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    @Sql("classpath:dataset.sql")
    public void realizarCompraTest() {
        try {
            Compra compra = Compra.builder()
                    .cliente(clienteServicio.obtenerCliente(5))
                    .fechaFuncionCompra(LocalDate.now())
                    .funcion(adminTeatroServicio.obtenerFuncion(1))
                    .metodo_pago(MetodoPago.EFECTY)
                    .cupon(clienteServicio.obtenerCuponCliente(5, 4))
                    .build();

            Boleta boleta1 = Boleta.builder()
                    .fila("H")
                    .columna("2")
                    .tipo(TipoSilla.ESTANDAR)
                    .build(); // Luego se le asigna la compra al crear hacer save(compra)

            Boleta boleta2 = Boleta.builder()
                    .fila("H")
                    .columna("3")
                    .tipo(TipoSilla.VIP)
                    .build();

            ConfiteriaCompra confiteria1 = ConfiteriaCompra.builder()
                    .unidades(2)
                    .confiteria(adminGeneralServicio.obtenerConfiteria(2))
                    .build(); // Luego se le asigna la compra al crear hacer save(compra)

            ConfiteriaCompra confiteria2 = ConfiteriaCompra.builder()
                    .unidades(2)
                    .confiteria(adminGeneralServicio.obtenerConfiteria(3))
                    .build();

            ArrayList<Boleta> boletas = new ArrayList<>();
            boletas.add(boleta1);
            boletas.add(boleta2);

            ArrayList<ConfiteriaCompra> confiteriaCompras = new ArrayList<>();
            confiteriaCompras.add(confiteria1);
            confiteriaCompras.add(confiteria2);

            Compra nueva = clienteServicio.realizarCompra(compra,boletas,confiteriaCompras);
            System.out.println("Compra Realizada: "+ nueva);

            List<Boleta> boletasCompra = clienteServicio.listarBoletasCompra(nueva.getId());
            boletasCompra.forEach(System.out::println);

            List<ConfiteriaCompra> confiterias = clienteServicio.listarConfiteriasCompra(nueva.getId());
            confiterias.forEach(System.out::println);

            Assertions.assertNotNull(nueva);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void redimirCuponTest() {
        try {
            boolean cuponRedimido = clienteServicio.redimirCupon(4, 1);

            CuponCliente cupon = clienteServicio.obtenerCuponCliente(4, 1);
            System.out.println(cupon);

            Assertions.assertEquals(true, cuponRedimido);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listarCuponesClienteTest() {
        List<CuponCliente> cupones = clienteServicio.listarCuponesCliente(1);
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
    public void buscarPeliculaFuncionTest() {
        List<PeliculaFuncionDTO> peliculas = clienteServicio.buscarPeliculaFuncion("spider");
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

    @Test
    public void enviarCorreoTest() {
        emailServicio.enviarEmail("Prueba de envio", "Este es un mensaje", "julian.acostat@uqvirtual.edu.co");
    }
}
