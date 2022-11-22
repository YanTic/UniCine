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
public class ConfiteriaBean implements Serializable {

    @Getter @Setter
    private Confiteria confiteria;

    @Getter @Setter
    private List<Confiteria> confiterias;

    @Getter @Setter
    private List<Confiteria> confiteriasSeleccionadas;

    @Getter @Setter
    private boolean editar;

    private Map<String, String> imagenes;

    @Autowired
    private AdminGeneralServicio adminGeneralServicio;

    @Autowired
    private CloudinaryServicio cloudinaryServicio;

    @PostConstruct
    public void init() {
        confiteria = new Confiteria();
        confiterias = adminGeneralServicio.listarConfiterias();
        confiteriasSeleccionadas = new ArrayList<>();
        imagenes = new HashMap<>();
        editar = false;
    }

    public void crearConfiteria() {
        try {
            if(!editar) {
                if(!imagenes.isEmpty()) {
                    confiteria.setImagenes(imagenes);
                    Confiteria registro = adminGeneralServicio.crearConfiteria(confiteria);

                    confiterias.add(registro);
                    confiteria = new Confiteria(); // Resetea los campos en el fronted (con ayuda del update="@form")
                    imagenes = new HashMap<>();

                    FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "La Confiteria ha sido creada con exito!");
                    FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);
                }
                else {
                    FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Es necesario subir al menos una imagen");
                    FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);
                }

            }
            else {
                adminGeneralServicio.actualizarConfiteria(confiteria.getId(), confiteria);
                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Confiteria actualizada con exito!");
                FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);
            }

        } catch (Exception e) {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
            FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);
        }
    }

    public void subirImg(FileUploadEvent event) {
        try {
            UploadedFile img = event.getFile();
            File imgFile = convertirUploadFile(img);
            Map resultado = cloudinaryServicio.uploadImg(imgFile, "confiterias");
            imagenes.put(resultado.get("public_id").toString(), resultado.get("url").toString());
            System.out.println(resultado);
        }
        catch (Exception e) {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
            FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);
        }
    }

    public void eliminarConfiterias() {
        String confiteriasNoEliminadas = "";
        for(Confiteria c: confiteriasSeleccionadas) {
            try{
                adminGeneralServicio.eliminarConfiteria(c.getId());
                confiterias.remove(c);
            }
            catch (Exception e) {
                confiteriasNoEliminadas += "Confiteria [Id: "+c.getId()+ "] ";
            }

        }
        confiteriasSeleccionadas.clear();

        if(confiteriasNoEliminadas.isEmpty() || confiteriasNoEliminadas.equals("")) {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Las confiterias se han borrado con exito!");
            FacesContext.getCurrentInstance().addMessage(null, fm);
            PrimeFaces.current().ajax().update("crud_confiteria:mensajes");
        }
        else {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Las confiterias {"+confiteriasNoEliminadas+"} no se pudieron eliminar, porque existen objetos que dependen de ellos");
            FacesContext.getCurrentInstance().addMessage(null, fm);
            PrimeFaces.current().ajax().update("crud_confiteria:mensajes");
        }
    }

    public String getMsgBtnEliminar() {
        if(confiteriasSeleccionadas.isEmpty()) {
            return "Eliminar";
        }
        else {
            return confiteriasSeleccionadas.size() == 1 ? "Eliminar: 1 elemento" : "Eliminar: "+confiteriasSeleccionadas.size()+" elementos";
        }
    }

    public File convertirUploadFile(UploadedFile img) throws IOException {
        File file = new File(img.getFileName());
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(img.getContent());
        fos.close();

        return file;
    }

    public String getMsgDialogoConfiteria() {
        return editar ? "Editar Confiteria" : "Crear Confiteria";
    }

    public void editarConfiteriaDialogo(Confiteria confiteriaSelect) {
        this.confiteria = confiteriaSelect;
        editar = true;
    }

    public void crearConfiteriaDialogo() {
        this.confiteria = new Confiteria();
        editar = false;
    }

}
