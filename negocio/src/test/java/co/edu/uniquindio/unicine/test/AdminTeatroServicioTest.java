package co.edu.uniquindio.unicine.test;

import co.edu.uniquindio.unicine.entidades.*;
import co.edu.uniquindio.unicine.servicios.AdminGeneralServicio;
import co.edu.uniquindio.unicine.servicios.AdminTeatroServicio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PrePersist;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@SpringBootTest
@Transactional
public class AdminTeatroServicioTest {

    @Autowired
    private AdminTeatroServicio adminTeatroServicio;

    // Este es solo para obtener objetos como teatro o cupones para crear objetos que corresponden al servicio de este test
    @Autowired
    private AdminGeneralServicio adminGeneralServicio;


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

        Horario horario = Horario.builder()
                .hora_fin(LocalTime.of(19,30,0))
                .hora_inicio(LocalTime.of(17,30,0))
                .fecha_inicio(LocalDate.parse("2022-05-03"))
                .fecha_fin(LocalDate.parse("2022-05-13"))
                .build();
        // insert into horario values (3, '2022-05-13', '2022-05-03', '19:30:00', '16:30:00');

        try {
            Horario nuevo = adminTeatroServicio.crearHorario(horario);
            System.out.println(nuevo);
            Assertions.assertNotNull(nuevo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void crearSalaTest() {
        Sala sala = null;

        try {
            sala = Sala.builder()
                    .nombre("T1Sala2_3DX") //.nombre("SalaEventos")
                    .tipo(TipoSala._3DX)
                    .teatro(adminGeneralServicio.obtenerTeatro(3))
                    .distribucionSillas(adminTeatroServicio.obtenerDistribucionSillas(1))
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            Sala nueva = adminTeatroServicio.crearSala(sala);
            System.out.println(nueva);
            Assertions.assertNotNull(nueva);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void crearFuncionTest() {
        Funcion funcion = null;
        try {

            //insert into funcion values (id, precio, horarioid, peliculaid, salaid);
            //insert into funcion values (1, 200, 1, 1, 1);
            funcion = Funcion.builder()
                    .precio((float)200)
                    .pelicula(adminGeneralServicio.obtenerPelicula(1))
                    .horario(adminTeatroServicio.obtenerHorario(1))
                    .sala(adminTeatroServicio.obtenerSala(1))
                    .build();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            Funcion nueva = adminTeatroServicio.crearFuncion(funcion); // Falla porque la funcion tiene la misma sala y horario que otra funcion
            System.out.println(nueva);
            Assertions.assertNotNull(nueva);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void crearDistribucionSillasTest() {

        //insert into distribucion_sillas values (id, col, fila, rutaEsquema, totalSillas);
        //insert into distribucion_sillas values (1, 10, 10, "ruta", 90);
        DistribucionSillas dist = DistribucionSillas.builder()
                .filas(10)
                .columnas(10)
                .rutaEsquema("ruta")
                .totalSillas(90)
                .build();

        try {
            DistribucionSillas nueva = adminTeatroServicio.crearDistribucionSillas(dist);
            System.out.println(nueva);
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
            Horario horarioGuardado = adminTeatroServicio.obtenerHorario(3);
            System.out.println("obtenido: "+horarioGuardado);

            Horario horarioActualizado = Horario.builder()
                    .fecha_inicio(horarioGuardado.getFecha_inicio())
                    .fecha_fin(horarioGuardado.getFecha_fin())
                    .hora_inicio(horarioGuardado.getHora_inicio())
                    .hora_fin(LocalTime.of(2,4,50)) // Agrego el valor que quiero cambiar
                    .build();

            // Al hacer esto, crear un nuevo horario con Builder, este horario solo se usuará para asignarle
            // los valores al Horario ya guardado en la db (dataset.sql) con ayuda del metodo del servicio
            // (adminTeatroServicio.actualizarHorario()). Porque como se habia hecho anteriormente (revisar
            // commit anterior), si se obtiene el Horario con el findById que tiene un metodo del servicio,
            // no se obtiene un objeto nuevo, sino que se obtiene el puntero hacia el objeto, asi que cada vez
            // que se le realice un cambio al objeto (por ejemplo) con el .set(), el EntityManger y/o JPA
            // revisa si al objeto se le ha hecho algun cambio y lo guarda, realiza el save() sin uno
            // haberlo escrito.

            // Igualmente se puede usar con el .set(), pero si se quiere hacer una restrinccion, como de que
            // cuando se actualice un objeto no quede exactamente igual (con los mismos valores y catacteristicas),
            // pues se tiene que comparar con ayuda de un @Query para obtener los horarios y compararlos con los
            // valores del nuevo horario, cosa que va a generar error, porque siempre va a obtener ese objeto
            // (Horario con los mismos valores al actualizado), porque el EntityManger y/o JPA hizo el save()
            // al escribir .set();

            // horario.setHora_fin(LocalTime.of(2,4,50));

            System.out.println("actualizado: "+ horarioActualizado);

            Horario nuevoHorario = adminTeatroServicio.actualizarHorario(horarioGuardado.getId(), horarioActualizado);
            System.out.println("nuevo horario: "+nuevoHorario);

            Assertions.assertEquals(LocalTime.of(2,4,50), nuevoHorario.getHora_fin());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void actualizarSalaTest() {
        try {
            Sala sala = adminTeatroServicio.obtenerSala(2);

            Sala salaActualizada = Sala.builder()
                    .nombre("T4Sala6_EST") // T4Sala6_EST
                    .tipo(TipoSala.ESTANDAR) // TipoSala.ESTANDAR
                    .teatro(sala.getTeatro())
                    .distribucionSillas(sala.getDistribucionSillas())
                    .build();

            Sala nuevaSala = adminTeatroServicio.actualizarSala(sala.getId(), salaActualizada);

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
            System.out.println("Funcion vieja: "+funcion);

            Funcion funcionActualizada = Funcion.builder()
                    .precio((float) 3451.2) //(float) 3451.2
                    .horario(funcion.getHorario())
                    .pelicula(funcion.getPelicula())
                    .sala(funcion.getSala())
                    .build();

            Funcion nuevaFuncion = adminTeatroServicio.actualizarFuncion(funcion.getId(), funcionActualizada);
            System.out.println("Funcion nueva: "+nuevaFuncion);

            Assertions.assertEquals((float)3451.2, nuevaFuncion.getPrecio());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void actualizarDistribucionSillasTest() {
        try {
            DistribucionSillas distSillas = adminTeatroServicio.obtenerDistribucionSillas(4);

            DistribucionSillas distSillasActualizada = DistribucionSillas.builder()
                    .filas(distSillas.getFilas())
                    .columnas(distSillas.getColumnas())
                    .rutaEsquema(distSillas.getRutaEsquema())
                    .totalSillas(50)
                    .build();

            DistribucionSillas nuevaDist = adminTeatroServicio.actualizarDistribucionSillas(distSillas.getId(), distSillasActualizada);

            Assertions.assertEquals(50, nuevaDist.getTotalSillas());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    // Eliminar TESTS
    @Test
    @Sql("classpath:dataset.sql")
    public void eliminarHorarioTest() {
        try {
            adminTeatroServicio.eliminarHorario(4);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            Horario horario = adminTeatroServicio.obtenerHorario(4);
            System.out.println(horario);
        } catch (Exception e) {
            // throw new RuntimeException(e);
            Assertions.assertTrue(true); //Si falla está bien, porque no encontró el horario que se eliminó
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void eliminarSalaTest() {
        try {
            adminTeatroServicio.eliminarSala(1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            Sala sala = adminTeatroServicio.obtenerSala(1);
            System.out.println(sala);
        } catch (Exception e) {
            throw new RuntimeException(e);
            // Assertions.assertTrue(true);
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void eliminarFuncionTest() {
        try {
            adminTeatroServicio.eliminarFuncion(5);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            Funcion funcion = adminTeatroServicio.obtenerFuncion(5);
            System.out.println(funcion);
        } catch (Exception e) {
            throw new RuntimeException(e);
            // Assertions.assertTrue(true);
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void eliminarDistribucionSillasTest() {
        try {
            adminTeatroServicio.eliminarDistribucionSillas(3);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            DistribucionSillas dist = adminTeatroServicio.obtenerDistribucionSillas(3);
            System.out.println(dist);
        } catch (Exception e) {
            // throw new RuntimeException(e);
            Assertions.assertTrue(true);
        }
    }

    // Listar TESTS
    @Test
    @Sql("classpath:dataset.sql")
    public void listarHorariosTest() {
        List<Horario> horarios = adminTeatroServicio.listarHorarios();
        horarios.forEach(System.out::println);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listarSalasTest() {
        List<Sala> salas = adminTeatroServicio.listarSalas();
        salas.forEach(System.out::println);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listarFuncionesTest() {
        List<Funcion> funciones = adminTeatroServicio.listarFunciones();
        funciones.forEach(System.out::println);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listarDistribucionSillasTest() {
        List<DistribucionSillas> distSillas = adminTeatroServicio.listarDistribucionSillas();
        distSillas.forEach(System.out::println);
    }
}
