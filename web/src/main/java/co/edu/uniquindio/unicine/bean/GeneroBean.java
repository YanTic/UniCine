package co.edu.uniquindio.unicine.bean;

import co.edu.uniquindio.unicine.entidades.Genero;
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
public class GeneroBean implements Serializable {

    @Getter @Setter
    private Genero genero;

    @Getter @Setter
    private List<Genero> generos;

    @Getter @Setter
    private List<Genero> generosSeleccionados;

    @Getter @Setter
    private boolean editar;

    @Autowired
    private AdminGeneralServicio adminGeneralServicio;

    @PostConstruct
    public void init() {
        genero = new Genero();
        generos = adminGeneralServicio.listarGeneros();
        generosSeleccionados = new ArrayList<>();
        editar = false;
    }

    public void crearGenero() {
        try {
            if(!editar) {
                Genero registro = adminGeneralServicio.crearGenero(genero);
                generos.add(registro);

                genero = new Genero();

                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Genero creado con exito!");
                FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);
            }
            else {
                adminGeneralServicio.actualizarGenero(genero.getId(), genero);
                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Genero actualizado con exito!");
                FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);
            }

        } catch (Exception e) {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
            FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);
        }
    }

    public void eliminarGeneros() {
        String generosNoEliminados = "";
        for(Genero g: generosSeleccionados) {
            try{
                adminGeneralServicio.eliminarGenero(g.getId());
                generos.remove(g);
            }
            catch (Exception e) {
                generosNoEliminados += "Genero [Id: "+g.getId()+ "] ";
            }

        }
        generosSeleccionados.clear();

        if(generosNoEliminados.isEmpty() || generosNoEliminados.equals("")) {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Los generos se han borrado con exito!");
            FacesContext.getCurrentInstance().addMessage(null, fm);
            PrimeFaces.current().ajax().update("crud_genero:mensajes_g");
        }
        else {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Los generos {"+generosNoEliminados+"} no se pudieron eliminar, porque existen objetos que dependen de ellos");
            FacesContext.getCurrentInstance().addMessage(null, fm);
            PrimeFaces.current().ajax().update("crud_genero:mensajes_g");
        }
    }

    public String getMsgBtnEliminar() {
        if(generosSeleccionados.isEmpty()) {
            return "Eliminar";
        }
        else {
            return generosSeleccionados.size() == 1 ? "Eliminar: 1 elemento" : "Eliminar: "+generosSeleccionados.size()+" elementos";
        }
    }

    public String getMsgDialogoGenero() {
        return editar ? "Editar Genero" : "Crear Genero";
    }

    public void editarGeneroDialogo(Genero generoSelect) {
        this.genero = generoSelect;
        editar = true;
    }

    public void crearGeneroDialogo() {
        this.genero = new Genero();
        editar = false;
    }
}
