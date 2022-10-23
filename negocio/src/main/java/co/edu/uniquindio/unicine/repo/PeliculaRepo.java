package co.edu.uniquindio.unicine.repo;

import co.edu.uniquindio.unicine.dto.HorarioSalaDTO;
import co.edu.uniquindio.unicine.entidades.EstadoPelicula;
import co.edu.uniquindio.unicine.entidades.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PeliculaRepo extends JpaRepository<Pelicula, Integer> {

    Optional<Pelicula> findByNombre(String nombrePelicula);



    // Cree una consulta que devuelva una lista con todas las películas que contengan en su nombre una
    // cadena de búsqueda y que tengan un estado (cartelera o próximamente). Use LIKE.
    @Query("select p from Pelicula p where p.nombre LIKE concat('%', :cadenaBusqueda, '%') and p.estado = :estadoPelicula")
    List<Pelicula> obtenerPeliculasNombreYEstado(String cadenaBusqueda, EstadoPelicula estadoPelicula);

    // Cree una consulta que devuelva los horarios y las salas que tiene una película en un teatro específico.
    // Use un DTO para una mejor respuesta.
    @Query("select new co.edu.uniquindio.unicine.dto.HorarioSalaDTO(f.horario, f.sala) from Pelicula p JOIN p.funciones f where p.id = :idPelicula and f.sala.teatro.id = :idTeatro")
    List<HorarioSalaDTO> obtenerHorariosSalas(Integer idPelicula, Integer idTeatro);

    // Escriba una consulta que devuelva las películas que sean de un género específico. Ordene la respuesta
    // alfabéticamente de acuerdo al nombre de la película.
    @Query("select p from Pelicula p JOIN p.generos g where g.id = :idGenero order by p.nombre")
    List<Pelicula> obtenerPeliculasPorGenero(Integer idGenero);


    // Escriba una consulta que permita obtener la película más vista en una ciudad específica.
    @Query("select comp.funcion.pelicula, count(comp) from Compra comp where comp.funcion.sala.teatro.ciudad.id = :idCiudad group by comp.funcion.pelicula")
    List<Object[]> obtenerPeliculaMasVista(Integer idCiudad);

    /*@Query("select max(cantidadCompras) from (select count(comp) as cantidadCompras from Compra comp group by comp.funcion.pelicula) n ")
    List<Object[]> obtenerPeliculaMasVista2(Integer idCiudad);*/
}
