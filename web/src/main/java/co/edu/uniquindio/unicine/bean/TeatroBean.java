package co.edu.uniquindio.unicine.bean;

import co.edu.uniquindio.unicine.entidades.Ciudad;
import co.edu.uniquindio.unicine.entidades.Teatro;
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
import java.util.List;

@Component
@ViewScoped
public class TeatroBean implements Serializable {

    @Getter @Setter
    private Teatro teatro;

    @Getter @Setter
    private List<Ciudad> ciudades;

    @Autowired
    private AdminGeneralServicio adminGeneralServicio;

    @PostConstruct
    public void init() {
        teatro = new Teatro();
        ciudades = adminGeneralServicio.listarCiudades();
    }

    public void crearTeatro() {
        try {
            adminGeneralServicio.crearTeatro(teatro);
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Teatro creado con exito!");
            FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);
        } catch (Exception e) {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
            FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);
        }
    }
}
