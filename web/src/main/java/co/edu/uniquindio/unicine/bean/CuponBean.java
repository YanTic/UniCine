package co.edu.uniquindio.unicine.bean;

import co.edu.uniquindio.unicine.entidades.Cupon;
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
public class CuponBean implements Serializable {

    @Getter @Setter
    private Cupon cupon;

    @Getter @Setter
    private List<Cupon> cupones;

    @Getter @Setter
    private List<Cupon> cuponesSeleccionados;

    @Getter @Setter
    private boolean editar;

    @Autowired
    private AdminGeneralServicio adminGeneralServicio;

    @PostConstruct
    public void init() {
        cupon = new Cupon();
        cupones = adminGeneralServicio.listarCupones();
        cuponesSeleccionados = new ArrayList<>();
        editar = false;
    }

    public void crearCupon() {
        try {
            if(!editar) {
                Cupon registro = adminGeneralServicio.crearCupon(cupon);
                cupones.add(registro);

                cupon = new Cupon();

                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Cupon creado con exito!");
                FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);
            }
            else {
                adminGeneralServicio.actualizarCupon(cupon.getId(), cupon);
                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Cupon actualizado con exito!");
                FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);
            }

        } catch (Exception e) {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
            FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);
        }
    }

    public void eliminarCupones() {
        String cuponesNoEliminados = "";
        for(Cupon c: cuponesSeleccionados) {
            try{
                adminGeneralServicio.eliminarCupon(c.getId());
                cupones.remove(c);
            }
            catch (Exception e) {
                cuponesNoEliminados += "Cupon [Id: "+c.getId()+ "] ";
            }

        }
        cuponesSeleccionados.clear();

        if(cuponesNoEliminados.isEmpty() || cuponesNoEliminados.equals("")) {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Los cupones se han borrado con exito!");
            FacesContext.getCurrentInstance().addMessage(null, fm);
            PrimeFaces.current().ajax().update("crud_cupon:mensajes");
        }
        else {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Los cupones {"+cuponesNoEliminados+"} no se pudieron eliminar, porque existen objetos que dependen de ellos");
            FacesContext.getCurrentInstance().addMessage(null, fm);
            PrimeFaces.current().ajax().update("crud_cupon:mensajes");
        }
    }

    public String getMsgBtnEliminar() {
        if(cuponesSeleccionados.isEmpty()) {
            return "Eliminar";
        }
        else {
            return cuponesSeleccionados.size() == 1 ? "Eliminar: 1 elemento" : "Eliminar: "+cuponesSeleccionados.size()+" elementos";
        }
    }

    public String getMsgDialogoCupon() {
        return editar ? "Editar Cupon" : "Crear Cupon";
    }

    public void editarCuponDialogo(Cupon cuponSelect) {
        this.cupon = cuponSelect;
        editar = true;
    }

    public void crearCuponDialogo() {
        this.cupon = new Cupon();
        editar = false;
    }
}
