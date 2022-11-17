package co.edu.uniquindio.unicine.repo;

import co.edu.uniquindio.unicine.dto.FuncionDTO;
import co.edu.uniquindio.unicine.entidades.Funcion;
import co.edu.uniquindio.unicine.entidades.Horario;
import co.edu.uniquindio.unicine.entidades.Pelicula;
import co.edu.uniquindio.unicine.entidades.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface FuncionRepo extends JpaRepository<Funcion, Integer> {

    @Query("select f.pelicula.nombre from Funcion f where f.id = :idFuncion")
    String obtenerNombrePelicula(Integer idFuncion);

    // Con puedo obtener la misma pelicula varias veces
    //@Query("select f.pelicula from Funcion f ")

    // Pero con "distinct" solo me aparecerá la pelicula asociada a las funcion una sola vez
    @Query("select distinct f.pelicula from Funcion f ")
    List<Pelicula> obtenerPeliculas();

    @Query("select f from Funcion f where f.horario.id = :idHorario and f.sala.id = :idSala")
    Optional<Funcion> verificarDisponibilidad(Integer idHorario, Integer idSala);

    @Query("select f from Funcion f where f.id <> :idFuncion and f.horario.id = :idHorario and f.sala.id = :idSala")
    Optional<Funcion> verificarDisponibilidadParaActualizadas(Integer idFuncion, Integer idHorario, Integer idSala);

    @Query("select f from Funcion f where f.precio = :precio and f.horario.id = :idHorario and f.pelicula.id = :idPelicula and f.sala.id = :idSala")
    Optional<Funcion> verificarExistencia(Float precio, Integer idHorario, Integer idPelicula, Integer idSala);

    @Query("select f from Funcion f where f.pelicula.id = :idPelicula and f.sala.teatro.ciudad.id = :idCiudad")
    List<Funcion> obtenerFuncionesPorPeliculaCiudad(Integer idPelicula, Integer idCiudad);





    // Utilizando Object[]
    @Query("select f.pelicula.nombre, f.pelicula.estado, f.sala.id, f.sala.teatro.direccion, f.sala.teatro.ciudad.nombre, f.horario from Funcion f where f.pelicula.id = :idPelicula")
    List<Object[]> listarFunciones(Integer idPelicula);

    // Utilizando DTO (Data transfer Object)
    @Query("select new co.edu.uniquindio.unicine.dto.FuncionDTO(f.pelicula.nombre, f.pelicula.estado, f.sala.id, f.sala.teatro.direccion, f.sala.teatro.ciudad.nombre, f.horario) from Funcion f where f.pelicula.id = :idPelicula")
    List<FuncionDTO> listarFunciones2(Integer idPelicula);


    //Cree una consulta que permita determinar qué funciones no tienen compras asociadas en un teatro específico. Use IS EMPTY.
    @Query("select f from Funcion f where f.sala.teatro.id = :idTeatro and f.compras is empty")
    List<Funcion> obtenerFuncionesSinCompras(Integer idTeatro);


    //Cree una consulta que obtenga una lista de funciones que tiene un teatro en un rango de fechas dadas por parámetro.
    @Query("select f from Funcion f where f.sala.teatro.id = :idTeatro and f.horario.fecha_inicio <= :fechaInicio and f.horario.fecha_fin >= :fechaFin")
    List<Funcion> obtenerFuncionesPorFecha(LocalDate fechaInicio, LocalDate fechaFin, Integer idTeatro);
}
