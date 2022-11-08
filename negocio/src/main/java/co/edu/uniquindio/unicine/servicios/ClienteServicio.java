package co.edu.uniquindio.unicine.servicios;

import co.edu.uniquindio.unicine.dto.PeliculaFuncionDTO;
import co.edu.uniquindio.unicine.entidades.*;

import java.util.List;

public interface ClienteServicio {
    // Todas las funciones asociadas al rol del cliente (compras,loguearse,etc)

    Cliente login(String email, String contrasenia) throws Exception;

    Cliente registrarCliente(Cliente cliente) throws Exception;

    Cliente activarCuenta(Cliente cliente) throws Exception;

    Cliente actualizarCliente(Integer idCliente, Cliente clienteActualizado) throws Exception;

    void eliminarCliente(Integer idCliente) throws Exception;
    
    List<Cliente> listarClientes();

    Cliente obtenerCliente(Integer cedula) throws Exception;

    List<Compra> listarHistorialCompras(Integer idCliente) throws Exception;

    Compra realizarCompra(Compra compra, List<Boleta> boletas, List<ConfiteriaCompra> confiteriaCompras) throws Exception;

    List<Boleta> listarBoletasCompra(Integer idCompra);

    boolean redimirCupon(Integer idCliente, Integer idCupon) throws Exception;

    CuponCliente obtenerCuponCliente(Integer idCliente, Integer idCupon) throws Exception;

    List<CuponCliente> listarCuponesCliente(Integer idCliente);

    List<ConfiteriaCompra> listarConfiteriasCompra(Integer idCompra);

    List<Pelicula> buscarPelicula(String nombre);

    List<PeliculaFuncionDTO> buscarPeliculaFuncion(String nombre);

    Cliente cambiarContrasenia(Integer idCliente, String contraseniaAnterior, String contraseniaNueva) throws Exception;
}
