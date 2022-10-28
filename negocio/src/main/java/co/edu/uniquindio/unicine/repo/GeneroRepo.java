package co.edu.uniquindio.unicine.repo;

import co.edu.uniquindio.unicine.entidades.Genero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GeneroRepo extends JpaRepository<Genero, Integer> {

    @Query("select g from Genero g where g.nombre = UPPER(:nombre)")
    Optional<Genero> verificarExistencia(String nombre);
}
