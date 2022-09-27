package co.edu.uniquindio.unicine.repo;

import co.edu.uniquindio.unicine.entidades.Silla;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SillaRepo extends JpaRepository<Silla, Integer> {
}
