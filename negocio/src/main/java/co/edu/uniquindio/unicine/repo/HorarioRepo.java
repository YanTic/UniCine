package co.edu.uniquindio.unicine.repo;

import co.edu.uniquindio.unicine.entidades.Horario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Repository
public interface HorarioRepo extends JpaRepository<Horario, Integer> {

    @Query("select h from Horario h where h.id = :idHorario")
    Optional<Horario> obtenerHorario(Integer idHorario);

    @Query("select h from Horario h where h.fecha = :fecha and h.hora_inicio = :horaInicio and h.hora_fin = :horaFin")
    Optional<Horario> verificarExistencia(LocalTime horaInicio, LocalTime horaFin, LocalDate fecha);
}
