package co.edu.uniquindio.unicine.test;

import co.edu.uniquindio.unicine.dto.FuncionDTO;
import co.edu.uniquindio.unicine.entidades.Funcion;
import co.edu.uniquindio.unicine.entidades.Pelicula;
import co.edu.uniquindio.unicine.repo.FuncionRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FuncionRepoTest {

    @Autowired
    private FuncionRepo funcionRepo;

    @Test
    @Sql("classpath:dataset.sql")
    public void obtenerPeliculaFuncion(){
        String nombrePelicula = funcionRepo.obtenerNombrePelicula(3);

        System.out.println(nombrePelicula);
        Assertions.assertEquals("Klaus", nombrePelicula);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void obtenerPelicula(){
        List<Pelicula> peliculas = funcionRepo.obtenerPeliculas();

        peliculas.forEach(System.out::println);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void obtenerDatosFuncion(){
        List<Object[]> funciones = funcionRepo.listarFunciones(1);

        // f.pelicula.nombre, f.pelicula.estado, f.pelicula.imagenURL, f.sala.id, f.sala.teatro.direccion, f.sala.teatro.ciudad.nombre, f.horario
        funciones.forEach(o ->
            System.out.println(o[0] + ", "+ o[1] + ", "+ o[2] + ", "+ o[3] + ", "+ o[4] + ", "+ o[5] + ", "+ o[6])
        );
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void obtenerDatosFuncion2(){
        List<FuncionDTO> funciones = funcionRepo.listarFunciones2(1);
        funciones.forEach(System.out::println);
    }


    // Cree una consulta que permita determinar qué funciones no tienen compras asociadas en un teatro específico. Use IS EMPTY.
    @Test
    @Sql("classpath:dataset.sql")
    public void obtenerFuncionesSinCompras(){
        List<Funcion> funciones = funcionRepo.obtenerFuncionesSinCompras(4);
        funciones.forEach(System.out::println);
    }

    //Cree una consulta que obtenga una lista de funciones que tiene un teatro en un rango de fechas dadas por parámetro.
    @Test
    @Sql("classpath:dataset.sql")
    public void obtenerFuncionesPorFecha(){
        List<Funcion> funciones = funcionRepo.obtenerFuncionesPorFecha(LocalDate.parse("2022-08-06"),LocalDate.parse("2022-08-10"),1);
        funciones.forEach(System.out::println);
    }
}
