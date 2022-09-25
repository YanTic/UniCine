package co.edu.uniquindio.unicine.test;

import co.edu.uniquindio.unicine.entidades.Cliente;
import co.edu.uniquindio.unicine.repo.ClienteRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ClienteTest {

    @Autowired
    private ClienteRepo clienteRepo;

    @Test
    public void registrar(){

        /*ArrayList<String> telefonos = new ArrayList<>();
        telefonos.add("343435");
        telefonos.add("343331");*/

        String[] tel = new String[] {"214124", "343242"};
        Cliente cliente = new Cliente(1007, "Julian Acosta", Arrays.asList(tel), "julian@email.com", "ruta", "1234");

        Cliente guardado = clienteRepo.save(cliente);

//        Assertions.assertEquals("Julian Acosta", guardado.getNombre_completo());
        Assertions.assertNotNull(guardado);
    }

    @Test
    public void eliminar(){
        String[] tel = new String[] {"214124", "343242"};
        Cliente cliente = new Cliente(1007, "Julian Acosta", Arrays.asList(tel), "julian@email.com", "ruta", "1234");

        Cliente guardado = clienteRepo.save(cliente);
        clienteRepo.delete(guardado);

        Optional<Cliente> buscado = clienteRepo.findById(1007);

        Assertions.assertNull(buscado.orElse(null));
    }

    @Test
    public void actualizar(){
        String[] tel = new String[] {"214124", "343242"};
        Cliente cliente = new Cliente(1007, "Julian Acosta", Arrays.asList(tel), "julian@email.com", "ruta", "1234");

        Cliente guardado = clienteRepo.save(cliente);

        guardado.setEmail("julian_nuevo@email.com");

        Cliente nuevo = clienteRepo.save(guardado);

        Assertions.assertEquals("julian_nuevo@email.com", guardado.getEmail());
    }

    @Test
    public void obtener(){
        String[] tel = new String[] {"214124", "343242"};
        Cliente cliente = new Cliente(1007, "Julian Acosta", Arrays.asList(tel), "julian@email.com", "ruta", "1234");

        clienteRepo.save(cliente);

        Optional<Cliente> buscado = clienteRepo.findById(1007);
        System.out.println(buscado.orElse(null));
    }

    @Test
    public void listar(){
        String[] tel = new String[] {"214124", "343242"};
        Cliente cliente = new Cliente(1007, "Julian Acosta", Arrays.asList(tel), "julian@email.com", "ruta", "1234");

        clienteRepo.save(cliente);

        String[] tel2 = new String[] {"214124", "343242"};
        Cliente cliente2 = new Cliente(1005, "Luis", Arrays.asList(tel), "luis@email.com", "ruta", "1234");

        clienteRepo.save(cliente2);

        List<Cliente> lista = clienteRepo.findAll();

        System.out.println(lista);


    }
}
