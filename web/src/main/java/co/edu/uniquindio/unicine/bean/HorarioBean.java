package co.edu.uniquindio.unicine.bean;

import co.edu.uniquindio.unicine.entidades.Horario;
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
public class HorarioBean implements Serializable {

    @Getter @Setter
    private Horario horario;

    @Getter @Setter
    private List<Horario> horarios;

    @Getter @Setter
    private List<Horario> horariosSeleccionados;

    @Getter @Setter
    private boolean editar;

    @Autowired
    private AdminTeatroServicio adminTeatroServicio;

    @PostConstruct
    public void init() {
        horario = new Horario();
        horarios = adminTeatroServicio.listarHorarios();
        horariosSeleccionados = new ArrayList<>();
        editar = false;
    }

    public void crearHorario() {
        try {
            if(!editar) {
                Horario registro = adminTeatroServicio.crearHorario(horario);
                horarios.add(registro); // Actualizamos los datos de la tabla que está en la web

                horario = new Horario(); // Cuando se crea el objeto, se tiene que resetear (porque en la vista sigue siendo el mismo)

                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Horario creado con exito!");
                FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);
            }
            else {
                adminTeatroServicio.actualizarHorario(horario.getId(), horario);
                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Horario actualizado con exito!");
                FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);
            }

        } catch (Exception e) {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
            FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);
        }
    }

    public void eliminarHorarios() {
        String horariosNoEliminados = "";
        for(Horario h: horariosSeleccionados) {
            try{
                adminTeatroServicio.eliminarHorario(h.getId());
                horarios.remove(h);
            }
            catch (Exception e) {
                horariosNoEliminados += "Horario [Id: "+h.getId()+ "] ";
            }

        }
        horariosSeleccionados.clear();

        if(horariosNoEliminados.isEmpty() || horariosNoEliminados.equals("")) {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Los horarios se han borrado con exito!");
            FacesContext.getCurrentInstance().addMessage(null, fm);
            PrimeFaces.current().ajax().update("crud_horario:mensajes_horario");
        }
        else {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Los horarios {"+horariosNoEliminados+"} no se pudieron eliminar, porque existen objetos que dependen de ellos");
            FacesContext.getCurrentInstance().addMessage(null, fm);
            PrimeFaces.current().ajax().update("crud_horario:mensajes_horario");
        }
    }

    public String getMsgBtnEliminar() {
        if(horariosSeleccionados.isEmpty()) {
            return "Eliminar";
        }
        else {
            return horariosSeleccionados.size() == 1 ? "Eliminar: 1 elemento" : "Eliminar: "+horariosSeleccionados.size()+" elementos";
        }
    }

    public String getMsgDialogoTeatro() {
        return editar ? "Editar Horario" : "Crear Horario";
    }

    public void editarHorarioDialogo(Horario horarioSelect) {
        this.horario = horarioSelect;
        editar = true;
    }

    public void crearHorarioDialogo() {
        this.horario = new Horario();
        editar = false;
    }
}
