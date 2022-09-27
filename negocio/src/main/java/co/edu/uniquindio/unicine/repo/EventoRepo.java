package co.edu.uniquindio.unicine.repo;

import co.edu.uniquindio.unicine.entidades.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoRepo extends JpaRepository<Evento, Integer> {
}
