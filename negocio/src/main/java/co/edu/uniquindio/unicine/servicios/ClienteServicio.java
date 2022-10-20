package co.edu.uniquindio.unicine.servicios;

import co.edu.uniquindio.unicine.entidades.Cliente;
import co.edu.uniquindio.unicine.entidades.Compra;

import java.util.List;

public interface ClienteServicio {
    // Todas las funciones asociadas al rol del cliente (compras,loguearse,etc)

    Cliente obtenerCliente(Integer cedula) throws Exception;

    Cliente login(String email, String contrasenia) throws Exception;

    Cliente registrarCliente(Cliente cliente) throws Exception;

    Cliente actualizarCliente(Cliente cliente) throws Exception;

    void eliminarCliente(Integer idCliente) throws Exception;
    
    List<Cliente> listarClientes();

    List<Compra> listarHistorial(Integer idCliente) throws Exception;

    Compra realizarCompra(Compra compra) throws Exception;

    boolean redimirCupon(Integer idCupon) throws Exception;
}
