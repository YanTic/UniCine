package co.edu.uniquindio.unicine.repo;

import co.edu.uniquindio.unicine.dto.PeliculaFuncionDTO;
import co.edu.uniquindio.unicine.entidades.*;
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

    @Query("select c from Cupon c where c.id = :idCupon")
    Cupon obtenerCupon(Integer idCupon);

    @Query("select c from Cliente c where c.estado = :estado")
    List<Cliente> obtenerPorEstado(boolean estado, Pageable paginador);

    // Puede parecer confusa la consulta, pero es porque es una relacion de muchos a muchos
    @Query("select cup.cupon from Cliente c JOIN c.cupones cup where c.email = :emailCliente")
    List<Cupon> obtenerCupones(String emailCliente);


    // Cree una consulta que calcule el valor total que ha gastado un usuario en compras.
    @Query("select sum(comp.valorTotal) from Cliente c JOIN c.compras comp where c.cedula = :idCliente")
    Float obtenerDineroGastado(Integer idCliente);

    @Query("select p from Pelicula p where p.nombre like concat('%', :nombrePelicula, '%')")
    List<Pelicula> obtenerPelicula(String nombrePelicula);

    // Si no se muestra una pelicula, es porque no tiene asociada una funcion, para esto se puede usar left join
    @Query("select new co.edu.uniquindio.unicine.dto.PeliculaFuncionDTO(p,f) from Pelicula p left join p.funciones f where p.nombre like concat('%', :nombrePelicula, '%')")
    List<PeliculaFuncionDTO> obtenerPeliculaFuncion(String nombrePelicula);

    @Query("select distinct f.pelicula from Funcion f where f.pelicula.estado = :estadoPelicula and f.sala.teatro.ciudad.id = :idCiudad")
    List<Pelicula> obtenerPeliculasPorEstadoCiudad(EstadoPelicula estadoPelicula, Integer idCiudad);

    @Query("select distinct f.pelicula from Funcion f where f.pelicula.estado = :estadoPelicula")
    List<Pelicula> obtenerPeliculasPorEstado(EstadoPelicula estadoPelicula);

    @Query("select comp from Compra comp where comp.cliente.cedula = :idCliente")
    List<Compra> listarHistorialCompras(Integer idCliente);

    @Query("select c from Cliente c where c.cedula = :idCliente and c.contrasenia = :contrasenia")
    Optional<Cliente> verificarContrasenia(Integer idCliente, String contrasenia);

    @Query("select cup from CuponCliente cup where cup.cliente.cedula = :idCliente and cup.cupon.id = :idCupon and cup.estado = false and cup.cupon.fecha_vencimiento > current_date")
    Optional<CuponCliente> verificarDisponibilidadCupon(Integer idCliente, Integer idCupon);

    @Query("select comp from Compra comp where comp.id = :idCompra and comp.cliente.cedula = :idCliente")
    Optional<Compra> verificarExistenciaCompraCliente(Integer idCliente, Integer idCompra);
}
