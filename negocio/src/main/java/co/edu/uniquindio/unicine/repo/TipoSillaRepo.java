package co.edu.uniquindio.unicine.repo;

import co.edu.uniquindio.unicine.entidades.TipoSilla;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoSillaRepo extends JpaRepository<TipoSilla, Integer> {
}
