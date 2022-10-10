package co.edu.uniquindio.unicine.repo;

import co.edu.uniquindio.unicine.dto.FuncionDTO;
import co.edu.uniquindio.unicine.entidades.Funcion;
import co.edu.uniquindio.unicine.entidades.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuncionRepo extends JpaRepository<Funcion, Integer> {

    @Query("select f.pelicula.nombre from Funcion f where f.id = :idFuncion")
    String obtenerNombrePelicula(Integer idFuncion);

    // Con puedo obtener la misma pelicula varias veces
    //@Query("select f.pelicula from Funcion f ")

    // Pero con "distinct" solo me aparecerá la pelicula asociada a las funcion una sola vez
    @Query("select distinct f.pelicula from Funcion f ")
    List<Pelicula> obtenerPeliculas();


    // Utilizando Object[]
    @Query("select f.pelicula.nombre, f.pelicula.estado, f.pelicula.imagenURL, f.sala.id, f.sala.teatro.direccion, f.sala.teatro.ciudad.nombre, f.horario from Funcion f where f.pelicula.id = :idPelicula")
    List<Object[]> listarFunciones(Integer idPelicula);

    // Utilizando DTO (Data transfer Object)
    @Query("select new co.edu.uniquindio.unicine.dto.FuncionDTO(f.pelicula.nombre, f.pelicula.estado, f.pelicula.imagenURL, f.sala.id, f.sala.teatro.direccion, f.sala.teatro.ciudad.nombre, f.horario) from Funcion f where f.pelicula.id = :idPelicula")
    List<FuncionDTO> listarFunciones2(Integer idPelicula);
}
