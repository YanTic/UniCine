package co.edu.uniquindio.unicine.test;

import co.edu.uniquindio.unicine.entidades.*;
import co.edu.uniquindio.unicine.servicios.AdminTeatroServicio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

@SpringBootTest
@Transactional
public class AdminTeatroServicioTest {

    @Autowired
    private AdminTeatroServicio adminTeatroServicio;

    @Test
    @Sql("classpath:dataset.sql")
    public void loginTest() {

        try {
            AdminTeatro admin = adminTeatroServicio.login("bruno@email.com", "33ks");
            Assertions.assertNotNull(admin);
        } catch (Exception e) {
            //throw new RuntimeException(e);
            Assertions.assertTrue(false);
        }

    }


    // Crear TESTS

    @Test
    @Sql("classpath:dataset.sql")
    public void crearHorarioTest() {
        // Horario horario = Horario.builder().hora_fin(LocalTime.of(4,24,0)).hora_inicio(LocalTime.of(7,11,0)).fecha(LocalDate.parse("2022-03-25")).build();

        Horario horario = Horario.builder().hora_fin(LocalTime.of(19,30,0)).hora_inicio(LocalTime.of(16,30,0)).fecha(LocalDate.parse("2022-05-03")).build();
        // insert into horario values (3, '2022-05-03', '19:30:00', '16:30:00');

        try {
            Horario nuevo = adminTeatroServicio.crearHorario(horario);
            System.out.println(nuevo);
            Assertions.assertNotNull(nuevo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*Sala sala = Sala.builder().
            nombre("SalaEventos").
            tipo(TipoSala._3DX).
            teatro(Teatro.builder().
                    nombre("Teatro").
                    direccion("Carrea tin y tan").
                    ciudad(Ciudad.builder().nombre("BuenaVentura").build()).
                    build()).
            distribucionSillas(DistribucionSillas.builder().
                    totalSillas(30).
                    columnas(3).
                    filas(10).
                    rutaEsquema("ruta").build()).
            build();*/

    @Test
    @Sql("classpath:dataset.sql")
    public void crearSalaTest() {
        Sala sala = Sala.builder().
            nombre("SalaEventos").
            tipo(TipoSala._3DX).
            teatro(Teatro.builder().
                    nombre("Teatro").
                    direccion("Carrea tin y tan").
                    ciudad(Ciudad.builder().nombre("BuenaVentura").build()).
                    build()).
            distribucionSillas(DistribucionSillas.builder().
                    totalSillas(30).
                    columnas(3).
                    filas(10).
                    rutaEsquema("ruta").build()).
            build();

        try {
            Sala nueva = adminTeatroServicio.crearSala(sala);
            Assertions.assertNotNull(nueva);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    // Actualizar TESTS

    @Test
    @Sql("classpath:dataset.sql")
    public void actualizarHorarioTest() {
        try {
            Horario horario = adminTeatroServicio.obtenerHorario(3);
            System.out.println(horario);
            horario.setHora_fin(LocalTime.of(2,4,50));
            Horario nuevoHorario = adminTeatroServicio.actualizarHorario(horario);
            System.out.println(nuevoHorario);

            Assertions.assertEquals(LocalTime.of(2,4,50), horario.getHora_fin());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void actualizarSalaTest() {
        try {
            Sala sala = adminTeatroServicio.obtenerSala(50);
            sala.setTipo(TipoSala.ESTANDAR);
            Sala nuevaSala = adminTeatroServicio.actualizarSala(sala);

            Assertions.assertEquals(TipoSala.ESTANDAR, nuevaSala.getTipo());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void actualizarFuncionTest() {
        try {
            Funcion funcion = adminTeatroServicio.obtenerFuncion(3);
            funcion.setPrecio((float) 3451.2);
            Funcion nuevaFuncion = adminTeatroServicio.actualizarFuncion(funcion);

            Assertions.assertEquals((float)3451.2, nuevaFuncion.getPrecio());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void actualizarDistribucionSillasTest() {
        try {
            DistribucionSillas distribucionSillas = adminTeatroServicio.obtenerDistribucionSillas(4);
            distribucionSillas.setTotalSillas(50);
            DistribucionSillas nuevaDist = adminTeatroServicio.actualizarDistribucionSillas(distribucionSillas);

            Assertions.assertEquals(50, distribucionSillas.getTotalSillas());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}
