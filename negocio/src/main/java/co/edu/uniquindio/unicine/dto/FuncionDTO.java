package co.edu.uniquindio.unicine.dto;

import co.edu.uniquindio.unicine.entidades.EstadoPelicula;
import co.edu.uniquindio.unicine.entidades.Horario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class FuncionDTO {

    // Consulta Query, en FuncionRepo
    // f.pelicula.nombre, f.pelicula.estado, f.pelicula.imagenURL, f.sala.id, f.sala.teatro.direccion, f.sala.teatro.ciudad.nombre, f.horario
    private String nombrePelicula;
    private EstadoPelicula estadoPelicula;
    private String imagenURL;
    private Integer idSala;
    private String direccionTeatro;
    private String nombreCiudad;
    private Horario horarioFuncion;

}
