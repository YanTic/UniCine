package co.edu.uniquindio.unicine.bean;

import co.edu.uniquindio.unicine.entidades.*;
import co.edu.uniquindio.unicine.servicios.AdminGeneralServicio;
import co.edu.uniquindio.unicine.servicios.CloudinaryServicio;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

@Component
@ViewScoped
public class EventoBean implements Serializable {

    @Getter  @Setter
    private Evento evento;

    @Getter @Setter
    private List<Teatro> teatros;

    @Getter @Setter
    private List<Evento> eventos;

    @Getter @Setter
    private List<Evento> eventosSeleccionados;

    @Getter @Setter
    private boolean editar;

    private Map<String, String> imagenes;

    @Autowired
    private CloudinaryServicio cloudinaryServicio;

    @Autowired
    private AdminGeneralServicio adminGeneralServicio;

    @PostConstruct
    public void init() {
        evento = new Evento();
        eventos = adminGeneralServicio.listarEventos();
        teatros = adminGeneralServicio.listarTeatros();
        eventosSeleccionados = new ArrayList<>();
        imagenes = new HashMap<>();
        editar = false;
    }

    public void crearEvento() {
        try {
            if(!editar) {
                if(!imagenes.isEmpty()) {
                    evento.setImagenes(imagenes);
                    Evento registro = adminGeneralServicio.crearEvento(evento);

                    eventos.add(registro);
                    evento = new Evento(); // Resetea los campos en el fronted (con ayuda del update="@form")
                    imagenes = new HashMap<>();

                    FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "El evento ha sido creada con exito!");
                    FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);
                }
                else {
                    FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Es necesario subir al menos una imagen");
                    FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);
                }

            }
            else {
                adminGeneralServicio.actualizarEvento(evento.getId(), evento);
                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Evento actualizada con exito!");
                FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);
            }

        } catch (Exception e) {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
            FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);
        }
    }

    // TODO: Agregar img a cloudinnary, cambiar el dataset agregando en la tabla evento_imagenes la key y url de la imagen
    // TODO: ACTUALIZAR DATASET
    public void subirImg(FileUploadEvent event) {
        try {
            UploadedFile img = event.getFile();
            File imgFile = convertirUploadFile(img);
            Map resultado = cloudinaryServicio.uploadImg(imgFile, "eventos");
            imagenes.put(resultado.get("public_id").toString(), resultado.get("url").toString());
            System.out.println(resultado);
        }
        catch (Exception e) {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
            FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);
        }
    }

    public void eliminarEventos() {
        String eventosNoEliminados = "";
        for(Evento e: eventosSeleccionados) {
            try{
                adminGeneralServicio.eliminarEvento(e.getId());
                eventos.remove(e);
            }
            catch (Exception exc) {
                eventosNoEliminados += "Evento [Id: "+e.getId()+ "] ";
            }

        }
        eventosSeleccionados.clear();

        if(eventosNoEliminados.isEmpty() || eventosNoEliminados.equals("")) {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Los eventos se han borrado con exito!");
            FacesContext.getCurrentInstance().addMessage(null, fm);
            PrimeFaces.current().ajax().update("crud_evento:mensajes");
        }
        else {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Las eventos {"+eventosNoEliminados+"} no se pudieron eliminar, porque existen objetos que dependen de ellos");
            FacesContext.getCurrentInstance().addMessage(null, fm);
            PrimeFaces.current().ajax().update("crud_evento:mensajes");
        }
    }

    public String getMsgBtnEliminar() {
        if(eventosSeleccionados.isEmpty()) {
            return "Eliminar";
        }
        else {
            return eventosSeleccionados.size() == 1 ? "Eliminar: 1 elemento" : "Eliminar: "+eventosSeleccionados.size()+" elementos";
        }
    }

    public File convertirUploadFile(UploadedFile img) throws IOException {
        File file = new File(img.getFileName());
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(img.getContent());
        fos.close();

        return file;
    }

    public String getMsgDialogoEvento() {
        return editar ? "Editar Evento" : "Crear Evento";
    }

    public void editarEventoDialogo(Evento eventoSelect) {
        this.evento = eventoSelect;
        editar = true;
    }

    public void crearEventoDialogo() {
        this.evento = new Evento();
        editar = false;
    }
}
