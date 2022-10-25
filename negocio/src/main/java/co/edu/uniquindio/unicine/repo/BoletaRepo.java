package co.edu.uniquindio.unicine.repo;

import co.edu.uniquindio.unicine.entidades.Boleta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoletaRepo extends JpaRepository<Boleta, Integer> {

    @Query("select bol from Boleta bol where bol.compra.id = :idCompra")
    List<Boleta> obtenerBoletas(Integer idCompra);
}
