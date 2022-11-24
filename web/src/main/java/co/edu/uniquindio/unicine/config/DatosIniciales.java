package co.edu.uniquindio.unicine.config;

import co.edu.uniquindio.unicine.entidades.*;
import co.edu.uniquindio.unicine.servicios.AdminGeneralServicio;
import co.edu.uniquindio.unicine.servicios.AdminTeatroServicio;
import co.edu.uniquindio.unicine.servicios.ClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DatosIniciales implements CommandLineRunner {

    @Autowired
    private ClienteServicio clienteServicio;

    @Autowired
    private AdminGeneralServicio adminGeneralServicio;

    @Autowired
    private AdminTeatroServicio adminTeatroServicio;

    @Override
    public void run(String... args) throws Exception {

        List<Ciudad> ciudades = adminGeneralServicio.listarCiudades();

        if (ciudades.isEmpty()) {
            Ciudad c1 = Ciudad.builder().nombre("Armenia").build();
            Ciudad c2 = Ciudad.builder().nombre("Pereira").build();

            adminGeneralServicio.crearCiudad(c1);
            adminGeneralServicio.crearCiudad(c2);
            adminGeneralServicio.crearTeatro(Teatro.builder().nombre("Teatro heroku").direccion("Más alla de por allá").ciudad(adminGeneralServicio.obtenerCiudad(1)).build());

            adminGeneralServicio.crearAdminTeatro(AdminTeatro.builder().cedula("122").nombre("ADMIN Teatro").email("adminTeatro@email.com").contrasenia("1234").teatro(adminGeneralServicio.obtenerTeatro(1)).build());
            adminGeneralServicio.crearAdminGeneral(AdminGeneral.builder().cedula("312").nombre("ADMIN General").email("adminGeneral@email.com").contrasenia("1234").ciudad(adminGeneralServicio.obtenerCiudad(1)).build());

            String[] tel = new String[] {"214124", "343242"};
            Cliente cliente = Cliente.builder().cedula(222).nombre_completo("Cliente").telefonos(Arrays.asList(tel)).email("cliente@email.com").imagen_perfil("url").contrasenia("1234").build();
            cliente.setEstado(true);
            clienteServicio.registrarCliente(cliente);
        }


    }
}
