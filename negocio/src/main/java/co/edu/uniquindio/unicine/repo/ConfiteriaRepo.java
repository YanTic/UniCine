package co.edu.uniquindio.unicine.repo;

import co.edu.uniquindio.unicine.entidades.Confiteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfiteriaRepo extends JpaRepository<Confiteria, Integer> {

    Optional<Confiteria> findByProducto(String producto);

    @Query("select c from Confiteria c where c.producto = :producto and c.precio = :precio")
    Optional<Confiteria> verificarExistencia(String producto, Float precio);

    @Query("select c from Confiteria c where c.id <> :idConfiteria and c.producto = :producto")
    Optional<Confiteria> verificarDisponibilidadParaActualizadas(Integer idConfiteria, String producto);
}
