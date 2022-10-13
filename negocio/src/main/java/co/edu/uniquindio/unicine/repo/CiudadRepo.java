package co.edu.uniquindio.unicine.repo;

import co.edu.uniquindio.unicine.entidades.Ciudad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CiudadRepo extends JpaRepository<Ciudad, Integer> {

    // Cree una consulta que permita contar el número de teatros que hay por cada ciudad. Use GROUP BY.
    @Query("select c.nombre, count(t) from Ciudad c LEFT JOIN c.teatros t group by c")
    List<Object[]> teatroPorCiudad();

}
