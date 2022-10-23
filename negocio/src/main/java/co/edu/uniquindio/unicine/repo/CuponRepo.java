package co.edu.uniquindio.unicine.repo;

import co.edu.uniquindio.unicine.entidades.Cupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface CuponRepo extends JpaRepository<Cupon, Integer> {

    @Query("select c from Cupon c where c.valor_descuento = :valor_descuento and c.fecha_vencimiento = :fecha_vencimiento and c.criterio = :criterio")
    Optional<Cupon> verificarExistencia(Float valor_descuento, LocalDate fecha_vencimiento, String criterio);
}
