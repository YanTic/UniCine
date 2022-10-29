package co.edu.uniquindio.unicine.repo;

import co.edu.uniquindio.unicine.entidades.ConfiteriaCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfiteriaCompraRepo extends JpaRepository<ConfiteriaCompra, Integer> {

    @Query("select c from ConfiteriaCompra c where c.compra.id = :idCompra")
    List<ConfiteriaCompra> obtenerConfiteriasCompra(Integer idCompra);
}
