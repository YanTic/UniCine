package co.edu.uniquindio.unicine.repo;

import co.edu.uniquindio.unicine.entidades.ConfiteriaCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfiteriaCompraRepo extends JpaRepository<ConfiteriaCompra, Integer> {
}
