package co.edu.uniquindio.unicine.bean;

import co.edu.uniquindio.unicine.entidades.*;
import co.edu.uniquindio.unicine.servicios.AdminGeneralServicio;
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
import java.util.Arrays;
import java.util.List;

@Component
@ViewScoped
public class SalaBean implements Serializable {

    @Getter @Setter
    private Sala sala;

    @Getter @Setter
    private List<Sala> salas;

    @Getter @Setter
    private List<Sala> salasSeleccionadas;

    @Getter @Setter
    private List<Teatro> teatros;

    @Getter @Setter
    private List<TipoSala> tipoSalas;

    @Getter @Setter
    private List<DistribucionSillas> distribucionSillas;

    @Getter @Setter
    private boolean editar;

    @Autowired
    private AdminTeatroServicio adminTeatroServicio;

    @Autowired
    private AdminGeneralServicio adminGeneralServicio;


    @PostConstruct
    public void init() {
        sala = new Sala();
        salas = adminTeatroServicio.listarSalas();
        salasSeleccionadas = new ArrayList<>();
        teatros = adminGeneralServicio.listarTeatros();
        tipoSalas = Arrays.asList(TipoSala.values());
        distribucionSillas = adminTeatroServicio.listarDistribucionSillas();
        editar = false;
    }

    public void crearSala() {
        try {
            if(!editar) {
                Sala registro = adminTeatroServicio.crearSala(sala);
                salas.add(registro); // Actualizamos los datos de la tabla que está en la web

                sala = new Sala(); // Cuando se crea el objeto, se tiene que resetear (porque en la vista sigue siendo el mismo)

                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Sala creada con exito!");
                FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);
            }
            else {
                adminTeatroServicio.actualizarSala(sala.getId(), sala);
                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Sala actualizada con exito!");
                FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);
            }

        } catch (Exception e) {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
            FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);
        }
    }

    public void eliminarSalas() {
        String salasNoEliminadas = "";
        for(Sala s: salasSeleccionadas) {
            try{
                adminTeatroServicio.eliminarSala(s.getId());
                salas.remove(s);
            }
            catch (Exception e) {
                salasNoEliminadas += "Sala [Id: "+s.getId()+ "] ";
            }
        }
        salasSeleccionadas.clear();

        if(salasNoEliminadas.isEmpty() || salasNoEliminadas.equals("")) {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Las salas se han borrado con exito!");
            FacesContext.getCurrentInstance().addMessage(null, fm);
            PrimeFaces.current().ajax().update("crud_sala:mensajes");
        }
        else {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Las salas {"+salasNoEliminadas+"} no se pudieron eliminar, porque existen objetos que dependen de ellos");
            FacesContext.getCurrentInstance().addMessage(null, fm);
            PrimeFaces.current().ajax().update("crud_sala:mensajes");
        }
    }

    public String getMsgBtnEliminar() {
        if(salasSeleccionadas.isEmpty()) {
            return "Eliminar";
        }
        else {
            return salasSeleccionadas.size() == 1 ? "Eliminar: 1 elemento" : "Eliminar: "+salasSeleccionadas.size()+" elementos";
        }
    }

    public String getMsgDialogoSala() {
        return editar ? "Editar Sala" : "Crear Sala";
    }

    public void editarSalaDialogo(Sala salaSelect) {
        this.sala = salaSelect;
        editar = true;
    }

    public void crearSalaDialogo() {
        this.sala = new Sala();
        editar = false;
    }

}
