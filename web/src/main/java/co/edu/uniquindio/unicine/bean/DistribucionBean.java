package co.edu.uniquindio.unicine.bean;

import co.edu.uniquindio.unicine.entidades.DistribucionSillas;
import co.edu.uniquindio.unicine.servicios.AdminTeatroServicio;
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
public class DistribucionBean implements Serializable {

    @Getter @Setter
    private DistribucionSillas dist;

    @Getter @Setter
    private List<DistribucionSillas> dists;

    @Getter @Setter
    private List<DistribucionSillas> distSeleccionadas;

    @Getter @Setter
    private boolean editar;

    @Autowired
    private AdminTeatroServicio adminTeatroServicio;

    @PostConstruct
    public void init() {
        dist = new DistribucionSillas();
        dists = adminTeatroServicio.listarDistribucionSillas();
        distSeleccionadas = new ArrayList<>();
        editar = false;
    }

    public void crearDistribucionSillas() {
        try {
            if(!editar) {
                DistribucionSillas registro = adminTeatroServicio.crearDistribucionSillas(dist);
                dists.add(registro);

                dist = new DistribucionSillas();

                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Distribucion de Sillas creada con exito!");
                FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);
            }
            else {
                adminTeatroServicio.actualizarDistribucionSillas(dist.getId(), dist);
                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Distribucion de Sillas actualizada con exito!");
                FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);
            }

        } catch (Exception e) {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
            FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);
        }
    }

    public void eliminarDistribuciones() {
        String distribucionesNoEliminadas = "";
        for(DistribucionSillas ds: distSeleccionadas) {
            try{
                adminTeatroServicio.eliminarDistribucionSillas(ds.getId());
                dists.remove(ds);
            }
            catch (Exception e) {
                distribucionesNoEliminadas += "Distribucion [Id: "+ds.getId()+ "] ";
            }

        }
        distSeleccionadas.clear();

        if(distribucionesNoEliminadas.isEmpty() || distribucionesNoEliminadas.equals("")) {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Las distribuciones de sillas se han borrado con exito!");
            FacesContext.getCurrentInstance().addMessage(null, fm);
            PrimeFaces.current().ajax().update("crud_distribucion:mensajes_distribucion");
        }
        else {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Las distribuciones de sillas {"+distribucionesNoEliminadas+"} no se pudieron eliminar, porque existen objetos que dependen de ellos");
            FacesContext.getCurrentInstance().addMessage(null, fm);
            PrimeFaces.current().ajax().update("crud_distribucion:mensajes_distribucion");
        }
    }

    public String getMsgBtnEliminar() {
        if(distSeleccionadas.isEmpty()) {
            return "Eliminar";
        }
        else {
            return distSeleccionadas.size() == 1 ? "Eliminar: 1 elemento" : "Eliminar: "+distSeleccionadas.size()+" elementos";
        }
    }

    public String getMsgDialogoDistribucion() {
        return editar ? "Editar Distribucion" : "Crear Distribucion";
    }

    public void editarDistribucionDialogo(DistribucionSillas dsSelect) {
        this.dist = dsSelect;
        editar = true;
    }

    public void crearDistribucionDialogo() {
        this.dist = new DistribucionSillas();
        editar = false;
    }
}
