package co.edu.uniquindio.unicine.bean;

import co.edu.uniquindio.unicine.entidades.Funcion;
import co.edu.uniquindio.unicine.entidades.Horario;
import co.edu.uniquindio.unicine.entidades.Pelicula;
import co.edu.uniquindio.unicine.entidades.Sala;
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
import java.util.List;

@Component
@ViewScoped
public class FuncionBean implements Serializable {

    @Getter @Setter
    private Funcion funcion;

    @Getter @Setter
    private List<Funcion> funciones;

    @Getter @Setter
    private List<Funcion> funcionesSeleccionadas;

    @Getter @Setter
    private List<Pelicula> peliculas;

    @Getter @Setter
    private List<Sala> salas;

    @Getter @Setter
    private List<Horario> horarios;

    @Getter @Setter
    private boolean editar;

    @Autowired
    private AdminTeatroServicio adminTeatroServicio;

    @Autowired
    private AdminGeneralServicio adminGeneralServicio;

    @PostConstruct
    public void init() {
        funcion = new Funcion();
        funciones = adminTeatroServicio.listarFunciones();
        funcionesSeleccionadas = new ArrayList<>();
        peliculas = adminGeneralServicio.listarPeliculas();
        salas = adminTeatroServicio.listarSalas();
        horarios = adminTeatroServicio.listarHorarios();
        editar = false;
    }

    public void crearFuncion() {
        try {
            if(!editar) {
                Funcion registro = adminTeatroServicio.crearFuncion(funcion);
                funciones.add(registro); // Actualizamos los datos de la tabla que está en la web

                funcion = new Funcion(); // Cuando se crea el objeto, se tiene que resetear (porque en la vista sigue siendo el mismo)

                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Funcion creada con exito!");
                FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);
            }
            else {
                adminTeatroServicio.actualizarFuncion(funcion.getId(), funcion);
                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Funcion actualizada con exito!");
                FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);
            }

        } catch (Exception e) {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
            FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);
        }
    }

    public void eliminarFunciones() {
        String funcionesNoEliminadas = "";
        for(Funcion f: funcionesSeleccionadas) {
            try{
                adminTeatroServicio.eliminarFuncion(f.getId());
                funciones.remove(f);
            }
            catch (Exception e) {
                funcionesNoEliminadas += "Funcion [Id: "+f.getId()+ "] ";
            }

        }
        funcionesSeleccionadas.clear();

        if(funcionesNoEliminadas.isEmpty() || funcionesNoEliminadas.equals("")) {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Las funciones se han borrado con exito!");
            FacesContext.getCurrentInstance().addMessage(null, fm);
            PrimeFaces.current().ajax().update("crud_funcion:mensajes");
        }
        else {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Las funciones {"+funcionesNoEliminadas+"} no se pudieron eliminar, porque existen objetos que dependen de ellos");
            FacesContext.getCurrentInstance().addMessage(null, fm);
            PrimeFaces.current().ajax().update("crud_funcion:mensajes");
        }
    }

    public String getMsgBtnEliminar() {
        if(funcionesSeleccionadas.isEmpty()) {
            return "Eliminar";
        }
        else {
            return funcionesSeleccionadas.size() == 1 ? "Eliminar: 1 elemento" : "Eliminar: "+funcionesSeleccionadas.size()+" elementos";
        }
    }

    public String getMsgDialogoFuncion() {
        return editar ? "Editar Funcion" : "Crear Funcion";
    }

    public void editarFuncionDialogo(Funcion funcionSelect) {
        this.funcion = funcionSelect;
        editar = true;
    }

    public void crearFuncionDialogo() {
        this.funcion = new Funcion();
        editar = false;
    }
}
