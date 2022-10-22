package co.edu.uniquindio.unicine.servicios;

import co.edu.uniquindio.unicine.entidades.*;

import java.util.List;

public interface AdminTeatroServicio {

    AdminTeatro login(String email, String contrasenia) throws Exception;


    // Crear
    Horario crearHorario(Horario horario) throws Exception;

    Sala crearSala(Sala sala) throws Exception;

    Funcion crearFuncion(Funcion funcion) throws Exception;

    DistribucionSillas crearDistribucionSillas(DistribucionSillas distribucionSillas) throws Exception;


    // Actualizar
    Horario actualizarHorario(Horario horario) throws Exception;

    Sala actualizarSala(Sala sala) throws Exception;

    Funcion actualizarFuncion(Funcion funcion) throws Exception;

    DistribucionSillas actualizarDistribucionSillas(DistribucionSillas distribucionSillas) throws Exception;


    // Eliminar
    void eliminarHorario(Integer idHorario);

    void eliminarSala(Integer idSala);

    void eliminarFuncion(Integer idFuncion);

    void eliminarDistribucionSillas(Integer idDistribucionSillas);


    // Listar
    List<Horario> listarHorarios();

    List<Sala> listarSala();

    List<Funcion> listarFuncion();

    List<DistribucionSillas> listarDistribucionSillas();


    // Obtener
    Horario obtenerHorario(Integer idHorario) throws Exception;

    Sala obtenerSala(Integer idSala) throws Exception;

    Funcion obtenerFuncion(Integer idFuncion) throws Exception;

    DistribucionSillas obtenerDistribucionSillas(Integer idDistribucionSillas) throws Exception;

}
