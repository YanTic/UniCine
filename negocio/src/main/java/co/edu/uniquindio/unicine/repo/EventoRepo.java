package co.edu.uniquindio.unicine.repo;

import co.edu.uniquindio.unicine.entidades.Evento;
import co.edu.uniquindio.unicine.entidades.Teatro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Repository
public interface EventoRepo extends JpaRepository<Evento, Integer> {

    @Query("select e from Evento e where e.fecha = :fecha and e.hora_inicio = :hora_inicio and e.hora_fin = :hora_fin and e.teatro.id = :idTeatro")
    Optional<Evento> verificarExistencia(LocalDate fecha, LocalTime hora_inicio, LocalTime hora_fin, Integer idTeatro);
}
