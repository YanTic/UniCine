package co.edu.uniquindio.unicine.repo;

import co.edu.uniquindio.unicine.entidades.DistribucionSillas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DistribucionSillasRepo extends JpaRepository<DistribucionSillas, Integer> {

    @Query("select dist from DistribucionSillas dist where dist.filas = :filas and dist.columnas = :columnas and dist.rutaEsquema = :rutaEsquema and dist.totalSillas = :totalSillas")
    Optional<DistribucionSillas> verificarExistencia(Integer filas, Integer columnas, String rutaEsquema, Integer totalSillas);
}
