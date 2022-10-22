package co.edu.uniquindio.unicine.servicios;

import co.edu.uniquindio.unicine.entidades.*;

import java.util.List;

public interface AdminGeneralServicio {

    AdminGeneral login(String email, String contrasenia) throws Exception;

    // Crear
    Ciudad crearCiudad(Ciudad ciudad);

    Teatro crearTeatro(Teatro teatro);

    Cupon crearCupon(Cupon cupon);

    Evento crearEvento(Evento evento);

    Pelicula crearPelicula(Pelicula pelicula);

    Confiteria crearConfiteria(Confiteria confiteria);


    // Actualizar
    Ciudad actualizarCiudad(Ciudad ciudad) throws Exception;

    Teatro actualizarTeatro(Teatro teatro) throws Exception;

    Cupon actualizarCupon(Cupon cupon) throws Exception;

    Evento actualizarEvento(Evento evento);

    Pelicula actualizarPelicula(Pelicula pelicula);

    Confiteria actualizarConfiteria(Confiteria confiteria);


    // Eliminar
    void eliminarCiudad(Integer idCiudad);

    void eliminarTeatro(Integer idTeatro);

    void eliminarCupon(Integer idCupon);

    void eliminarEvento(Integer idEvento);

    void eliminarPelicula(Integer idPelicula);

    void eliminarConfiteria(Integer idConfiteria);


    // Listar
    List<Ciudad> listarCiudades();

    List<Teatro> listarTeatros();

    List<Cupon> listarCupones();

    List<Evento> listarEventos();

    List<Pelicula> listarPeliculas();

    List<Confiteria> listarConfiterias();

    List<Compra> listarCompras();


    // Obtener
    Ciudad obtenerCiudad(Integer idCiudad) throws Exception;

    Teatro obtenerTeatro(Integer idTeatro) throws Exception;

    Cupon obtenerCupon(Integer idCupon) throws Exception;

    Evento obtenerEvento(Integer evento);

    Pelicula obtenerPelicula(Integer idPelicula);

    Confiteria obtenerConfiteria(Integer idConfiteria);
}
