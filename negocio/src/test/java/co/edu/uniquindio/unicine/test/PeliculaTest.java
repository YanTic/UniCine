package co.edu.uniquindio.unicine.test;

import co.edu.uniquindio.unicine.dto.HorarioSalaDTO;
import co.edu.uniquindio.unicine.entidades.EstadoPelicula;
import co.edu.uniquindio.unicine.entidades.Pelicula;
import co.edu.uniquindio.unicine.repo.PeliculaRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PeliculaTest {

    @Autowired
    private PeliculaRepo peliculaRepo;

    // Cree una consulta que devuelva una lista con todas las películas que contengan en su nombre una
    // cadena de búsqueda y que tengan un estado (cartelera o próximamente). Use LIKE.
    @Test
    @Sql("classpath:dataset.sql")
    public void busquedaPelicula(){
        List<Pelicula> peliculas = peliculaRepo.obtenerPeliculasNombreYEstado("no", EstadoPelicula.PRESTRENO);
        peliculas.forEach(System.out::println);
    }


    // Cree una consulta que devuelva los horarios y las salas que tiene una película en un teatro específico.
    // Use un DTO para una mejor respuesta.
    @Test
    @Sql("classpath:dataset.sql")
    public void obtenerHorarioSalas(){
        List<HorarioSalaDTO> horarioSalas = peliculaRepo.obtenerHorariosSalas(1, 1);
        horarioSalas.forEach(System.out::println);
    }


    // Escriba una consulta que devuelva las películas que sean de un género específico. Ordene la respuesta
    // alfabéticamente de acuerdo al nombre de la película.
    @Test
    @Sql("classpath:dataset.sql")
    public void obtenerPeliculasPorGenero(){
        List<Pelicula> peliculas = peliculaRepo.obtenerPeliculasPorGenero(4);
        peliculas.forEach(System.out::println);
    }

    // Escriba una consulta que permita obtener la película más vista en una ciudad específica.
    @Test
    @Sql("classpath:dataset.sql")
    public void obtenerPeliculaMasVista(){
        List<Object[]> pelicula = peliculaRepo.obtenerPeliculaMasVista(1);
        pelicula.forEach(o->
                System.out.println(o[0] + ": "+o[1])
        );
    }

}
