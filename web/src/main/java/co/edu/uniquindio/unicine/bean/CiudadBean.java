package co.edu.uniquindio.unicine.bean;

import co.edu.uniquindio.unicine.entidades.Ciudad;
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
public class CiudadBean implements Serializable {

    @Getter @Setter
    private Ciudad ciudad;

    @Getter @Setter
    private List<Ciudad> ciudades;

    @Getter @Setter
    private List<Ciudad> ciudadesSeleccionadas;

    @Getter @Setter
    private boolean editar;

    @Autowired
    private AdminGeneralServicio adminGeneralServicio;

    @PostConstruct
    public void init() {
        ciudad = new Ciudad();
        ciudades = adminGeneralServicio.listarCiudades();
        ciudadesSeleccionadas = new ArrayList<>();
        editar = false;
    }

    public void crearCiudad() {
        try {
            if(!editar) {
                Ciudad registro = adminGeneralServicio.crearCiudad(ciudad);
                ciudades.add(registro);

                ciudad = new Ciudad();

                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Ciudad creada con exito!");
                FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);
            }
            else {
                adminGeneralServicio.actualizarCiudad(ciudad);
                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Ciudad actualizada con exito!");
                FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);
            }

        } catch (Exception e) {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
            FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);
        }
    }

    public void eliminarCiudades() {
        String ciudadesNoEliminadas = "";
        for(Ciudad c: ciudadesSeleccionadas) {
            try{
                adminGeneralServicio.eliminarCiudad(c.getId());
                ciudades.remove(c);
            }
            catch (Exception e) {
                ciudadesNoEliminadas += "Ciudad [Id: "+c.getId()+ "] ";
            }

        }
        ciudadesSeleccionadas.clear();

        if(ciudadesNoEliminadas.isEmpty() || ciudadesNoEliminadas.equals("")) {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Las ciudades se han borrado con exito!");
            FacesContext.getCurrentInstance().addMessage(null, fm);
            PrimeFaces.current().ajax().update("crud_ciudad:mensajes_ciudad");
        }
        else {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Las ciudades {"+ciudadesNoEliminadas+"} no se pudieron eliminar, porque existen objetos que dependen de ellos");
            FacesContext.getCurrentInstance().addMessage(null, fm);
            PrimeFaces.current().ajax().update("crud_ciudad:mensajes_ciudad");
        }
    }

    public String getMsgBtnEliminar() {
        if(ciudadesSeleccionadas.isEmpty()) {
            return "Eliminar";
        }
        else {
            return ciudadesSeleccionadas.size() == 1 ? "Eliminar: 1 elemento" : "Eliminar: "+ciudadesSeleccionadas.size()+" elementos";
        }
    }

    public String getMsgDialogoCiudad() {
        return editar ? "Editar Ciudad" : "Crear Ciudad";
    }

    public void editarCiudadDialogo(Ciudad ciudadSelect) {
        this.ciudad = ciudadSelect;
        editar = true;
    }

    public void crearCiudadDialogo() {
        this.ciudad = new Ciudad();
        editar = false;
    }
}
