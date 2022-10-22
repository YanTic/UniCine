package co.edu.uniquindio.unicine.test;

import co.edu.uniquindio.unicine.entidades.AdminGeneral;
import co.edu.uniquindio.unicine.entidades.Ciudad;
import co.edu.uniquindio.unicine.entidades.Cupon;
import co.edu.uniquindio.unicine.entidades.Teatro;
import co.edu.uniquindio.unicine.servicios.AdminGeneralServicio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class AdminGeneralServicioTest {

    @Autowired
    private AdminGeneralServicio adminGeneralServicio;

    @Test
    @Sql("classpath:dataset.sql")
    public void loginTest() {
        try {
            AdminGeneral adminGeneral = adminGeneralServicio.login("rex@email.com", "3jka");
            Assertions.assertNotNull(adminGeneral);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Crear TESTS


    // Actualizar TESTS

    @Test
    @Sql("classpath:dataset.sql")
    public void actualizarCiudadTest() {
        try {
            Ciudad ciudad = adminGeneralServicio.obtenerCiudad(52);
            ciudad.setNombre("Barrancabermeja");
            Ciudad nuevaCiudad = adminGeneralServicio.actualizarCiudad(ciudad);

            Assertions.assertEquals("Barrancabermeja", nuevaCiudad.getNombre());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void actualizarTeatroTest() {
        try {
            Teatro teatro = adminGeneralServicio.obtenerTeatro(2);
            teatro.setDireccion("Por allá detras de la casa roja");
            Teatro nuevoTeatro = adminGeneralServicio.actualizarTeatro(teatro);

            Assertions.assertEquals("Por allá detras de la casa roja", nuevoTeatro.getDireccion());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void actualizarCuponTest() {
        try {
            Cupon cupon = adminGeneralServicio.obtenerCupon(4);
            cupon.setDescripcion("Cupon porque si");
            Cupon nuevoCupon = adminGeneralServicio.actualizarCupon(cupon);

            Assertions.assertEquals("Cupon porqe si", nuevoCupon.getDescripcion());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void actualizarEventoTest() {

    }

    @Test
    @Sql("classpath:dataset.sql")
    public void actualizarPeliculaTest() {

    }

    @Test
    @Sql("classpath:dataset.sql")
    public void actualizarConfiteriaTest() {

    }


    // Eliminar TESTS


    // Listar TESTS
}
