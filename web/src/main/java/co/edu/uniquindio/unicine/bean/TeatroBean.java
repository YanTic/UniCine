package co.edu.uniquindio.unicine.bean;

import co.edu.uniquindio.unicine.entidades.Ciudad;
import co.edu.uniquindio.unicine.entidades.Teatro;
import co.edu.uniquindio.unicine.servicios.AdminGeneralServicio;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.PrimeFaces;
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

    @Getter @Setter
    private boolean editar;

    @Autowired
    private AdminGeneralServicio adminGeneralServicio;

    @PostConstruct
    public void init() {
        teatro = new Teatro();
        ciudades = adminGeneralServicio.listarCiudades();
        teatros = adminGeneralServicio.listarTeatros();
        teatrosSeleccionados = new ArrayList<>();
        editar = false;
    }

    public void crearTeatro() {
        try {

            if(!editar) {
                Teatro registro = adminGeneralServicio.crearTeatro(teatro);
                teatros.add(registro); // Actualizamos los datos de la tabla que está en la web

                teatro = new Teatro(); // Cuando se crea el objeto, se tiene que resetear (porque en la vista sigue siendo el mismo)

                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Teatro creado con exito!");
                FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);
            }
            else {
                adminGeneralServicio.actualizarTeatro(teatro.getId(), teatro);
                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Teatro actualizado con exito!");
                FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);
            }

        } catch (Exception e) {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
            FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);
        }
    }

    public void eliminarTeatros() {
        String teatrosNoEliminados = "";
        for(Teatro t: teatrosSeleccionados) {
            try{
                adminGeneralServicio.eliminarTeatro(t.getId());
                teatros.remove(t);
            }
            catch (Exception e) {
                // Esto es en caso de que al hacer el delete(), el jdbc tire error, ya sea porque no se puede eliminar
                // el teatro, porque ya existen objetos que están relacionados o dependen de este
                // Es mejor hacerlo de esta forma, solo decirle al usuario "no se puede", que hacer una peligrosa eliminacion por cascada
                teatrosNoEliminados += "Teatro [Id: "+t.getId()+ "] ";
            }

        }
        teatrosSeleccionados.clear();

        if(teatrosNoEliminados.isEmpty() || teatrosNoEliminados.equals("")) {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Los teatros se han borrado con exito!");
            FacesContext.getCurrentInstance().addMessage(null, fm);
            PrimeFaces.current().ajax().update("crud_teatro:mensajes");
        }
        else {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Los teatros {"+teatrosNoEliminados+"} no se pudieron eliminar, porque existen objetos que dependen de ellos");
            FacesContext.getCurrentInstance().addMessage(null, fm);
            PrimeFaces.current().ajax().update("crud_teatro:mensajes");
        }
    }

    public String getMsgBtnEliminar() {
        if(teatrosSeleccionados.isEmpty()) {
            return "Eliminar";
        }
        else {
            return teatrosSeleccionados.size() == 1 ? "Eliminar: 1 elemento" : "Eliminar: "+teatrosSeleccionados.size()+" elementos";
        }
    }

    public String getMsgDialogoTeatro() {
        return editar ? "Editar Teatro" : "Crear Teatro";
    }

    public void editarTeatroDialogo(Teatro teatroSeleccionado) {
        this.teatro = teatroSeleccionado;
        editar = true;
    }

    public void crearTeatroDialogo() {
        this.teatro = new Teatro();
        editar = false;
    }
}
