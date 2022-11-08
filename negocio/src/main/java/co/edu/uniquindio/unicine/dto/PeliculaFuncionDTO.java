package co.edu.uniquindio.unicine.dto;

import co.edu.uniquindio.unicine.entidades.Funcion;
import co.edu.uniquindio.unicine.entidades.Pelicula;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class PeliculaFuncionDTO {

    private Pelicula pelicula;
    private Funcion funcion;
}
