package co.edu.uniquindio.unicine.repo;

import co.edu.uniquindio.unicine.entidades.Ciudad;
import co.edu.uniquindio.unicine.entidades.Teatro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TeatroRepo extends JpaRepository<Teatro, Integer> {

    // Esto -> t.ciudad.nombre . Es imposible hacerlo en SQL, tendría que usar un JOIN
    // Por lo que Hibernate realiza la traduccion por nosotros
    @Query("select t from Teatro t where t.ciudad.nombre = :nombreCiudad")
    List<Teatro> listar(String nombreCiudad);

    @Query("select t from Teatro t where t.nombre = :nombre and t.direccion = :direccion and t.ciudad.id = :idCiudad")
    Optional<Teatro> verificarExistencia(String nombre, String direccion, Integer idCiudad);

}
