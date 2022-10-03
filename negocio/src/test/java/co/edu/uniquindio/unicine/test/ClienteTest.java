package co.edu.uniquindio.unicine.test;

import co.edu.uniquindio.unicine.entidades.Cliente;
import co.edu.uniquindio.unicine.repo.ClienteRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ClienteTest {

    @Autowired
    private ClienteRepo clienteRepo;

    @Test
    @Sql("classpath:dataset.sql")
    public void registrar(){
        String[] tel = new String[] {"214124", "343242"};
        Cliente cliente = new Cliente(1007, "Julian Acosta", Arrays.asList(tel), "julian@email.com", "ruta", "1234");

        Cliente guardado = clienteRepo.save(cliente);

        Assertions.assertNotNull(guardado);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void eliminar(){
        Cliente buscado = clienteRepo.findById(2).orElse(null);
        clienteRepo.delete(buscado);
        Assertions.assertNull(clienteRepo.findById(2).orElse(null));
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void actualizar(){
        Cliente guardado = clienteRepo.findById(4).orElse(null);
        guardado.setEmail("julian_nuevo@email.com");

        Cliente nuevo = clienteRepo.save(guardado);
        Assertions.assertEquals("julian_nuevo@email.com", guardado.getEmail());
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void obtener(){
        Optional<Cliente> buscado = clienteRepo.findById(3);
        Assertions.assertNotNull(buscado.orElse(null));
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listar(){
        List<Cliente> lista = clienteRepo.findAll();
        lista.forEach(System.out::println);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void obtenerPorCorreo(){
        /*Cliente cliente = clienteRepo.obtener("juan@email.com");*/
        Cliente cliente = clienteRepo.findByEmail("juan@email.com");
        System.out.println(cliente);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void comprobarAutenticacion(){
        /*Cliente cliente = clienteRepo.comprobarAutenticacion("juan@email.com", "3jn1");*/
        Cliente cliente = clienteRepo.findByEmailAndContrasenia("juan@email.com", "3jn1");
        System.out.println(cliente);
        Assertions.assertNotNull(cliente);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void paginador(){
        List<Cliente> clientes = clienteRepo.findAll(PageRequest.of(1,2)).toList();
        clientes.forEach(System.out::println);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void paginadorEstado(){
        List<Cliente> clientes = clienteRepo.obtenerPorEstado(true,PageRequest.of(1,2));
        clientes.forEach(System.out::println);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void ordenarRegistros(){
        /*List<Cliente> clientes = clienteRepo.findAll(Sort.by("nombre").descending());*/
        List<Cliente> clientes = clienteRepo.findAll(PageRequest.of(1,2, Sort.by("nombre").descending())).toList();
        clientes.forEach(System.out::println);
    }
}
