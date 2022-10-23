package co.edu.uniquindio.unicine.repo;

import co.edu.uniquindio.unicine.entidades.Genero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GeneroRepo extends JpaRepository<Genero, Integer> {

    Optional<Genero> findByNombre(String nombreGenero);
}
