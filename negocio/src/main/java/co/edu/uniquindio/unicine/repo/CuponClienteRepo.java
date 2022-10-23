package co.edu.uniquindio.unicine.repo;

import co.edu.uniquindio.unicine.entidades.CuponCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Optional;

@Repository
public interface CuponClienteRepo extends JpaRepository<CuponCliente, Integer> {

    @Query("select c from CuponCliente c where c.cliente.cedula = :idCliente")
    List<CuponCliente> obtenerCuponesCliente(Integer idCliente);

    @Query("select c from CuponCliente c where c.cliente.cedula = :idCliente and c.cupon.id = :idCupon")
    Optional<CuponCliente> obtenerPorCuponYCliente(Integer idCliente, Integer idCupon);
    Optional<CuponCliente> findByClienteAndCupon(Integer idCliente, Integer idCupon);
}
