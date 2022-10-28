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
    Horario actualizarHorario(Integer idHorario, Horario horarioActualizado) throws Exception;

    Sala actualizarSala(Integer idSala, Sala salaActualizada) throws Exception;

    Funcion actualizarFuncion(Integer idFuncion, Funcion funcionActualizada) throws Exception;

    DistribucionSillas actualizarDistribucionSillas(Integer idDistSillas, DistribucionSillas distribucionSillasActualizada) throws Exception;


    // Eliminar
    void eliminarHorario(Integer idHorario) throws Exception;

    void eliminarSala(Integer idSala) throws Exception;

    void eliminarFuncion(Integer idFuncion) throws Exception;

    void eliminarDistribucionSillas(Integer idDistribucionSillas) throws Exception;


    // Listar
    List<Horario> listarHorarios();

    List<Sala> listarSalas();

    List<Funcion> listarFunciones();

    List<DistribucionSillas> listarDistribucionSillas();


    // Obtener
    Horario obtenerHorario(Integer idHorario) throws Exception;

    Sala obtenerSala(Integer idSala) throws Exception;

    Funcion obtenerFuncion(Integer idFuncion) throws Exception;

    DistribucionSillas obtenerDistribucionSillas(Integer idDistribucionSillas) throws Exception;

}
