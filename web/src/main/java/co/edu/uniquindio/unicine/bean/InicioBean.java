package co.edu.uniquindio.unicine.bean;

import co.edu.uniquindio.unicine.entidades.Ciudad;
import co.edu.uniquindio.unicine.entidades.EstadoPelicula;
import co.edu.uniquindio.unicine.entidades.Pelicula;
import co.edu.uniquindio.unicine.servicios.AdminGeneralServicio;
import co.edu.uniquindio.unicine.servicios.ClienteServicio;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
@ViewScoped // Ciclo de vida del controlador, cuando se crea y borra en la memoria
public class InicioBean implements Serializable {

    @Autowired
    private ClienteServicio clienteServicio;

    @Autowired
    private AdminGeneralServicio adminGeneralServicio;

    @Getter @Setter
    private List<Pelicula> peliculasEstreno;

    @Getter @Setter
    private List<Pelicula> peliculasProximas;

    @Getter @Setter
    private List<Ciudad> ciudades;

    @Getter @Setter
    private Ciudad ciudad;

    @Getter @Setter
    private List<String> imagenes;

    @PostConstruct
    public void init() {
        try {
            peliculasEstreno = clienteServicio.listarPeliculasPorEstado(EstadoPelicula.ESTRENO);
            peliculasProximas = clienteServicio.listarPeliculasPorEstado(EstadoPelicula.PROXIMO);
            ciudades = adminGeneralServicio.listarCiudades();

            imagenes = new ArrayList<>();
            imagenes.add("https://wallpaperaccess.com/full/553391.jpg"); // spidy
            imagenes.add("https://wallpaperaccess.com/full/2042961.jpg"); // klaus
            imagenes.add("https://wallpaperaccess.com/full/2776322.jpg"); // tenet
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void elegirCiudad() {
        if (ciudad != null) {
            try {
                peliculasEstreno = clienteServicio.listarPeliculasPorEstadoCiudad(EstadoPelicula.ESTRENO, ciudad.getId());
                peliculasProximas = clienteServicio.listarPeliculasPorEstadoCiudad(EstadoPelicula.PROXIMO, ciudad.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    
    }
}
