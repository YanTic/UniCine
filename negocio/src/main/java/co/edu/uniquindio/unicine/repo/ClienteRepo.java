package co.edu.uniquindio.unicine.repo;

import co.edu.uniquindio.unicine.entidades.Cliente;
import co.edu.uniquindio.unicine.entidades.Compra;
import co.edu.uniquindio.unicine.entidades.Cupon;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepo extends JpaRepository<Cliente, Integer> {

    @Query("select c from Cliente c where c.email = ?1")
    Cliente obtener(String email);

    Optional<Cliente> findByEmail(String email); //Spring boot crea la consulta por inferencia

    @Query("select c from Cliente c where c.email = :email and c.contrasenia = :contrasenia")
    Cliente comprobarAutenticacion(String email, String contrasenia);

    Cliente findByEmailAndContrasenia(String email, String contrasenia); // Metodo por inferencia

    @Query("select c from Cliente c where c.estado = :estado")
    List<Cliente> obtenerPorEstado(boolean estado, Pageable paginador);


    // Puede parecer confusa la consulta, pero es porque es una relacion de muchos a muchos
    @Query("select cup.cupon from Cliente c JOIN c.cupones cup where c.email = :emailCliente")
    List<Cupon> obtenerCupones(String emailCliente);


    // Cree una consulta que calcule el valor total que ha gastado un usuario en compras.
    @Query("select sum(comp.valorTotal) from Cliente c JOIN c.compras comp where c.cedula = :idCliente")
    Float obtenerDineroGastado(Integer idCliente);
}
