package co.edu.uniquindio.unicine.repo;

import co.edu.uniquindio.unicine.entidades.DistribucionSillas;
import co.edu.uniquindio.unicine.entidades.Sala;
import co.edu.uniquindio.unicine.entidades.Teatro;
import co.edu.uniquindio.unicine.entidades.TipoSala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SalaRepo extends JpaRepository<Sala, Integer> {

    // Puede tener las mismas caracteristicas, pero su nombre si o si debe ser distinto a las salas ya creadas (nombre es unique)
    Optional<Sala> findByNombre(String nombreSala);

    // Si una sala tiene unas caracteristicas concretas, no se puede crear o actualizar una sala con esas mismas caracteristicas
    @Query("select s from Sala s where s.nombre = :nombre and s.tipo = :tipo and s.teatro.id = :idTeatro and s.distribucionSillas.id = :idDistribucionSillas")
    Optional<Sala> verificarExistencia(String nombre, TipoSala tipo, Integer idTeatro, Integer idDistribucionSillas);
}
