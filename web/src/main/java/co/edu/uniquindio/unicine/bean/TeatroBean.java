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
import java.util.ArrayList;
import java.util.List;

@Component
@ViewScoped
public class TeatroBean implements Serializable {

    @Getter @Setter
    private Teatro teatro;

    @Getter @Setter
    private List<Ciudad> ciudades;

    @Getter @Setter
    private List<Teatro> teatros;

    @Getter @Setter
    private List<Teatro> teatrosSeleccionados;

    @Autowired
    private AdminGeneralServicio adminGeneralServicio;

    @PostConstruct
    public void init() {
        teatro = new Teatro();
        ciudades = adminGeneralServicio.listarCiudades();
        teatros = adminGeneralServicio.listarTeatros();
        teatrosSeleccionados = new ArrayList<>();
    }

    public void crearTeatro() {
        try {
            Teatro registro = adminGeneralServicio.crearTeatro(teatro);
            teatros.add(registro); // Actualizamos los datos de la tabla que está en la web

            teatro = new Teatro(); // Cuando se crea el objeto, se tiene que resetear (porque en la vista sigue siendo el mismo)

            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Teatro creado con exito!");
            FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);
        } catch (Exception e) {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
            FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);
        }
    }

    public void eliminarTeatros() {
        teatrosSeleccionados.forEach(t -> {
            try {
                adminGeneralServicio.eliminarTeatro(t.getId());
                teatros.remove(t);
                teatrosSeleccionados.clear();
            } catch (Exception e) {
                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
                FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);
            }
        });
    }

    public String getMsgBtnEliminar() {
        if(teatrosSeleccionados.isEmpty()) {
            return "Eliminar";
        }
        else {
            return teatrosSeleccionados.size() == 1 ? "Eliminar: 1 elemento" : "Eliminar: "+teatrosSeleccionados.size()+" elementos";
        }
    }
}
