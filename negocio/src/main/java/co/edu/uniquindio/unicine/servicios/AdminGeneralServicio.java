package co.edu.uniquindio.unicine.servicios;

import co.edu.uniquindio.unicine.entidades.*;

import java.util.List;

public interface AdminGeneralServicio {

    AdminGeneral login(String email, String contrasenia) throws Exception;

    // Crear
    Ciudad crearCiudad(Ciudad ciudad);

    Teatro crearTeatro(Teatro teatro) throws Exception;

    Cupon crearCupon(Cupon cupon) throws Exception;

    Evento crearEvento(Evento evento) throws Exception;

    Pelicula crearPelicula(Pelicula pelicula) throws Exception;

    Genero crearGenero(Genero genero) throws Exception;

    Confiteria crearConfiteria(Confiteria confiteria) throws Exception;


    // Actualizar
    Ciudad actualizarCiudad(Ciudad ciudad) throws Exception;

    Teatro actualizarTeatro(Integer idTeatro, Teatro teatroActualizado) throws Exception;

    Cupon actualizarCupon(Integer idCupon, Cupon cuponActualizado) throws Exception;

    Evento actualizarEvento(Integer idEvento, Evento eventoActualizado) throws Exception;

    Pelicula actualizarPelicula(Pelicula pelicula) throws Exception;

    Genero actualizarGenero(Genero genero) throws Exception;

    Confiteria actualizarConfiteria(Confiteria confiteria) throws Exception;


    // Eliminar
    void eliminarCiudad(Integer idCiudad) throws Exception;

    void eliminarTeatro(Integer idTeatro) throws Exception;

    void eliminarCupon(Integer idCupon) throws Exception;

    void eliminarEvento(Integer idEvento) throws Exception;

    void eliminarPelicula(Integer idPelicula) throws Exception;

    void eliminarGenero(Integer idGenero) throws Exception;

    void eliminarConfiteria(Integer idConfiteria) throws Exception;


    // Listar
    List<Ciudad> listarCiudades();

    List<Teatro> listarTeatros();

    List<Cupon> listarCupones();

    List<Evento> listarEventos();

    List<Pelicula> listarPeliculas();

    List<Genero> listarGeneros();

    List<Confiteria> listarConfiterias();

    // List<Compra> listarCompras();


    // Obtener
    Ciudad obtenerCiudad(Integer idCiudad) throws Exception;

    Teatro obtenerTeatro(Integer idTeatro) throws Exception;

    Cupon obtenerCupon(Integer idCupon) throws Exception;

    Evento obtenerEvento(Integer idEvento) throws Exception;

    Pelicula obtenerPelicula(Integer idPelicula) throws Exception;

    Genero obtenerGenero(Integer idGenero) throws Exception;

    Confiteria obtenerConfiteria(Integer idConfiteria) throws Exception;
}
