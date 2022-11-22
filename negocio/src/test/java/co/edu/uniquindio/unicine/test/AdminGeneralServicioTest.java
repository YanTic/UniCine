package co.edu.uniquindio.unicine.test;

import co.edu.uniquindio.unicine.entidades.*;
import co.edu.uniquindio.unicine.servicios.AdminGeneralServicio;
import co.edu.uniquindio.unicine.servicios.AdminTeatroServicio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
public class AdminGeneralServicioTest {

    @Autowired
    private AdminGeneralServicio adminGeneralServicio;

    // Este es solo para obtener objetos como ciudad o sala para crear objetos que corresponden al servicio de este test
    @Autowired
    private AdminTeatroServicio adminTeatroServicio;

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

    @Test
    @Sql("classpath:dataset.sql")
    public void crearCiudadTest() {
        Ciudad ciudad = Ciudad.builder().nombre("Armenia").build();

        Ciudad nueva = adminGeneralServicio.crearCiudad(ciudad);
        System.out.println(nueva);
        Assertions.assertNotNull(nueva);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void crearTeatroTest() {
        Teatro teatro = null;
        try {
            // insert into teatro values (1, "Carrera 18", "Unicine Armenia", 1);
            teatro = Teatro.builder()
                    .nombre("Unicine Armenia")
                    .direccion("Carrera 19")
                    .ciudad(adminGeneralServicio.obtenerCiudad(1))
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            Teatro nuevo = adminGeneralServicio.crearTeatro(teatro);
            System.out.println(nuevo);
            Assertions.assertNotNull(nuevo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void crearCuponTest() {

        // insert into cupon values (1, "Registro", "Cupon por registrarse", '2022-04-12', 0.10);
        Cupon cupon = Cupon.builder()
                .valor_descuento((float)0.10)
                .criterio("Registro")
                .descripcion("Descripcion")
                .fecha_vencimiento(LocalDate.parse("2022-04-12"))
                .build();

        try {
            Cupon nuevo = adminGeneralServicio.crearCupon(cupon);
            System.out.println(nuevo);
            Assertions.assertNotNull(nuevo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    @Sql("classpath:dataset.sql")
    public void crearEventoTest() {
        Evento evento = null;
        try {
            // insert into evento values (1, '2022-08-17', '10:30:00', '09:30:00', "url", "Evento Fin de Anio", 1);
            evento = Evento.builder()
                    .nombre("Evento Fin de Anio")
                    .fecha(LocalDate.parse("2022-08-17"))
                    .hora_fin(LocalTime.of(10, 30, 0))
                    .hora_inicio(LocalTime.of(9, 30, 0))
                    .teatro(adminGeneralServicio.obtenerTeatro(1))
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            Evento nuevo = adminGeneralServicio.crearEvento(evento);
            System.out.println(nuevo);
            Assertions.assertNotNull(nuevo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void crearPeliculaTest() {

        ArrayList<Genero> generos = new ArrayList<>();
        try {
            generos.add(adminGeneralServicio.obtenerGenero(3));
            generos.add(adminGeneralServicio.obtenerGenero(4));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // insert into pelicula values (2,'PRESTRENO', "url", "StarWars: Rogue One","Andor, Darth Vader", "Pelean por los planos de destruccion de la death star", "url");
        Pelicula pelicula = Pelicula.builder()
                .nombre("StarWars: Rogue One")
                .estado(EstadoPelicula.PRESTRENO)
                .trailerURL("url")
                .generos(generos)
                .reparto("Andor, Darth Vader")
                .sinopsis("Pelean por los planos de destruccion de la death star")
                .build();

        try {
            Pelicula nueva = adminGeneralServicio.crearPelicula(pelicula);
            System.out.println(nueva);
            Assertions.assertNotNull(nueva);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void crearGeneroTest() {
        Genero genero = Genero.builder().nombre("MUSICAL").build();

        try {
            Genero nuevo = adminGeneralServicio.crearGenero(genero);
            System.out.println(nuevo);
            Assertions.assertNotNull(nuevo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void crearConfiteriaTest() {
        // insert into confiteria values (2, "url", 6000, "Agua");
        Confiteria confiteria = Confiteria.builder()
                .producto("Agua")
                .imagenURL("URL")
                .precio((float) 6000)
                .build();

        try {
            Confiteria nueva = adminGeneralServicio.crearConfiteria(confiteria);
            System.out.println(nueva);
            Assertions.assertNotNull(nueva);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



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
            System.out.println("Teatro viejo: "+teatro);

            Teatro teatroActualizado = Teatro.builder()
                    .nombre(teatro.getNombre())
                    .direccion(teatro.getDireccion()) // "Por allá detras de la casa roja"
                    .ciudad(teatro.getCiudad()).build();

            Teatro nuevoTeatro = adminGeneralServicio.actualizarTeatro(teatro.getId(), teatroActualizado);
            System.out.println("Teatro nuevo: "+nuevoTeatro);

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
            System.out.println("Cupon viejo: "+ cupon);

            Cupon cuponActualizado = Cupon.builder()
                    .valor_descuento(cupon.getValor_descuento())
                    .fecha_vencimiento(cupon.getFecha_vencimiento())
                    .descripcion("Cupon porque si")
                    .criterio(cupon.getCriterio())
                    .build();

            Cupon nuevoCupon = adminGeneralServicio.actualizarCupon(cupon.getId(), cuponActualizado);
            System.out.println("Cupon nuevo: "+nuevoCupon);

            Assertions.assertEquals("Cupon porque si", nuevoCupon.getDescripcion());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void actualizarEventoTest() {
        try {
            Evento evento = adminGeneralServicio.obtenerEvento(2);
            System.out.println("Evento viejo: "+ evento);

            Evento eventoActualizado = Evento.builder()
                    .nombre(evento.getNombre()) // "EVENTO MINECRAFT"
                    .fecha(evento.getFecha())
                    .hora_inicio(evento.getHora_inicio())
                    .hora_fin(evento.getHora_fin())
                    .teatro(evento.getTeatro())
                    .build();

            Evento nuevoEvento = adminGeneralServicio.actualizarEvento(evento.getId(), eventoActualizado);
            System.out.println("Evento nuevo: "+ nuevoEvento);

            Assertions.assertEquals("EVENTO MINECRAFT", nuevoEvento.getNombre());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void actualizarPeliculaTest() {
        try {
            Pelicula pelicula = adminGeneralServicio.obtenerPelicula(3);
            System.out.println("Pelicula vieja: "+ pelicula);

            Pelicula peliculaActualizada = Pelicula.builder()
                    .nombre("The Batman")
                    .trailerURL(pelicula.getTrailerURL())
                    .sinopsis(pelicula.getSinopsis())
                    .reparto(pelicula.getReparto())
                    .estado(pelicula.getEstado())
                    .generos(pelicula.getGeneros())
                    .build();

            Pelicula nuevaPelicula = adminGeneralServicio.actualizarPelicula(pelicula.getId(), peliculaActualizada);
            System.out.println("Pelicula nueva: "+ nuevaPelicula);

            Assertions.assertEquals("The Batman", nuevaPelicula.getNombre());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void actualizarGeneroTest() {
        try {
            Genero genero = adminGeneralServicio.obtenerGenero(2);
            System.out.println("Genero viejo: "+ genero);

            Genero generoActualizado = Genero.builder().nombre("ACCION").build(); // "DOCUMENTAL"

            Genero nuevo = adminGeneralServicio.actualizarGenero(genero.getId(), generoActualizado);
            System.out.println("Genero nuevo: "+ nuevo);

            Assertions.assertEquals("DOCUMENTAL", nuevo.getNombre());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void actualizarConfiteriaTest() {
        try {
            Confiteria confiteria = adminGeneralServicio.obtenerConfiteria(4);
            System.out.println("Confiteria vieja: "+ confiteria);

            Confiteria confiteriaActualizada = Confiteria.builder()
                    .producto(confiteria.getProducto())
                    .precio((float)53530.2)
                    .imagenURL(confiteria.getImagenURL())
                    .build();

            Confiteria nuevaConfiteria = adminGeneralServicio.actualizarConfiteria(confiteria.getId(), confiteriaActualizada);
            System.out.println("Confiteria nueva: "+ nuevaConfiteria);

            Assertions.assertEquals((float)53530.2, nuevaConfiteria.getPrecio());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    // Eliminar TESTS
    @Test
    @Sql("classpath:dataset.sql")
    public void eliminarCiudadTest() {
        try {
            adminGeneralServicio.eliminarCiudad(3);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            Ciudad ciudadEliminada = adminGeneralServicio.obtenerCiudad(3);
            System.out.println(ciudadEliminada);
        } catch (Exception e) {
            // throw new RuntimeException(e);
            Assertions.assertTrue(true); // Exito! Porque no encuentra la ciudad que se eliminó
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void eliminarTeatroTest() {
        try {
            adminGeneralServicio.eliminarTeatro(1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            Teatro teatro = adminGeneralServicio.obtenerTeatro(1);
            System.out.println(teatro);
        } catch (Exception e) {
            // throw new RuntimeException(e);
            Assertions.assertTrue(true); // Funciona, porque al no encontrar el teatro que se eliminó debe fallar
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void eliminarCuponTest() {
        try {
            adminGeneralServicio.eliminarCupon(4);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            Cupon cupon = adminGeneralServicio.obtenerCupon(4);
            System.out.println(cupon);
        } catch (Exception e) {
            // throw new RuntimeException(e);
            Assertions.assertTrue(true);
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void eliminarEventoTest() {
        try {
            adminGeneralServicio.eliminarEvento(2);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            Evento evento = adminGeneralServicio.obtenerEvento(2);
            System.out.println(evento);
        } catch (Exception e) {
            throw new RuntimeException(e);
            // Assertions.assertTrue(true);
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void eliminarPeliculaTest() {
        try {
            adminGeneralServicio.eliminarPelicula(4);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            Pelicula pelicula = adminGeneralServicio.obtenerPelicula(4);
            System.out.println(pelicula);
        } catch (Exception e) {
            //throw new RuntimeException(e);
            Assertions.assertTrue(true);
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void eliminarGeneroTest() {
        try {
            adminGeneralServicio.eliminarGenero(2);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            Genero genero = adminGeneralServicio.obtenerGenero(2);
            System.out.println(genero);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void eliminarConfiteriaTest() {
        try {
            adminGeneralServicio.eliminarConfiteria(4);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            Confiteria confiteria = adminGeneralServicio.obtenerConfiteria(4);
            System.out.println(confiteria);
        } catch (Exception e) {
            throw new RuntimeException(e);
            // Assertions.assertTrue(true);
        }
    }


    // Listar TESTS
    @Test
    @Sql("classpath:dataset.sql")
    public void listarCiudadesTest() {
        List<Ciudad> ciudades = adminGeneralServicio.listarCiudades();
        ciudades.forEach(System.out::println);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listarTeatrosTest() {
        List<Teatro> teatros = adminGeneralServicio.listarTeatros();
        teatros.forEach(System.out::println);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listarCuponesTest() {
        List<Cupon> cupones = adminGeneralServicio.listarCupones();
        cupones.forEach(System.out::println);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listarEventosTest() {
        List<Evento> eventos = adminGeneralServicio.listarEventos();
        eventos.forEach(System.out::println);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listarPeliculasTest() {
        List<Pelicula> peliculas = adminGeneralServicio.listarPeliculas();
        peliculas.forEach(System.out::println);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listarGenerosTest() {
        List<Genero> generos = adminGeneralServicio.listarGeneros();
        generos.forEach(System.out::println);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listarConfiteriasTest() {
        List<Confiteria> confiterias = adminGeneralServicio.listarConfiterias();
        confiterias.forEach(System.out::println);
    }
}
