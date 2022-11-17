package co.edu.uniquindio.unicine.bean;

import co.edu.uniquindio.unicine.entidades.Ciudad;
import co.edu.uniquindio.unicine.entidades.Funcion;
import co.edu.uniquindio.unicine.entidades.Pelicula;
import co.edu.uniquindio.unicine.servicios.AdminGeneralServicio;
import co.edu.uniquindio.unicine.servicios.AdminTeatroServicio;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
@ViewScoped
public class DetallePeliculaBean implements Serializable {

    @Value("#{param['pelicula_id']}")
    private String peliculaId;

    @Getter @Setter
    private Pelicula pelicula;

    @Getter @Setter
    private List<Funcion> funciones; // Traer las funciones dada la pelicula y la ciudad
                                     // Y dada la pelicula y ciudad, traer los teatros

    @Getter @Setter
    private List<Ciudad> ciudades;

    @Getter @Setter
    private Ciudad ciudad;

    @Autowired
    private AdminGeneralServicio adminGeneralServicio;

    @Autowired
    private AdminTeatroServicio adminTeatroServicio;

    @PostConstruct
    public void init() {
        try {
            if (!peliculaId.isEmpty() && peliculaId != null) {
                pelicula = adminGeneralServicio.obtenerPelicula(Integer.parseInt(peliculaId));
                ciudades = adminGeneralServicio.listarCiudades();
                funciones = new ArrayList<>();
                ciudad = new Ciudad();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);

        }
    }


    public void actualizarFunciones() {
        try {
            funciones = adminTeatroServicio.listarFuncionesPorPeliculaCiudad(Integer.parseInt(peliculaId), ciudad.getId());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
            FacesContext.getCurrentInstance().addMessage("mensajes", fm);
        }
    }

}
