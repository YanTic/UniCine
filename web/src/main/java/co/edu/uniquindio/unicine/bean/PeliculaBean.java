package co.edu.uniquindio.unicine.bean;

import co.edu.uniquindio.unicine.entidades.EstadoPelicula;
import co.edu.uniquindio.unicine.entidades.Genero;
import co.edu.uniquindio.unicine.entidades.Pelicula;
import co.edu.uniquindio.unicine.servicios.AdminGeneralServicio;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@ViewScoped
public class PeliculaBean implements Serializable {

    @Getter @Setter
    private Pelicula pelicula;

    @Getter @Setter
    private List<Genero> generos;

    @Getter @Setter
    private List<EstadoPelicula> estadoPeliculas;

    @Autowired
    private AdminGeneralServicio adminGeneralServicio;

    @PostConstruct
    public void init() {
        pelicula = new Pelicula();
        generos = adminGeneralServicio.listarGeneros();
        estadoPeliculas = Arrays.asList(EstadoPelicula.values());
    }

    public String crearPelicula() {

        try {
            adminGeneralServicio.crearPelicula(pelicula);

            pelicula = new Pelicula(); // Resetea los campos en el fronted (con ayuda del update="@form")

            // Realizo una redireccion
            return "/admin/pelicula_creada?faces-redirect=true";

        } catch (Exception e) {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
            FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);
        }

        return "";
    }


}
