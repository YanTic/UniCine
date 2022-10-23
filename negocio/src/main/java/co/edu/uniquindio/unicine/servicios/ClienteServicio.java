package co.edu.uniquindio.unicine.servicios;

import co.edu.uniquindio.unicine.entidades.Cliente;
import co.edu.uniquindio.unicine.entidades.Compra;
import co.edu.uniquindio.unicine.entidades.CuponCliente;
import co.edu.uniquindio.unicine.entidades.Pelicula;

import java.util.List;

public interface ClienteServicio {
    // Todas las funciones asociadas al rol del cliente (compras,loguearse,etc)

    Cliente login(String email, String contrasenia) throws Exception;

    Cliente registrarCliente(Cliente cliente) throws Exception;

    Cliente actualizarCliente(Cliente cliente) throws Exception;

    void eliminarCliente(Integer idCliente) throws Exception;
    
    List<Cliente> listarClientes();

    Cliente obtenerCliente(Integer cedula) throws Exception;

    List<Compra> listarHistorial(Integer idCliente) throws Exception;

    Compra realizarCompra(Compra compra) throws Exception;

    boolean redimirCupon(Integer idCliente, Integer idCupon) throws Exception;

    CuponCliente obtenerCuponCliente(Integer idCupon) throws Exception;

    List<CuponCliente> listarCuponesCliente(Integer idCliente);

    List<Pelicula> buscarPelicula(String nombre);

    Cliente cambiarContrasenia(Integer idCliente, String contraseniaAnterior, String contraseniaNueva) throws Exception;
}
