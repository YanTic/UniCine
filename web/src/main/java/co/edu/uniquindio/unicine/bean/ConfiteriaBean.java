package co.edu.uniquindio.unicine.bean;

import co.edu.uniquindio.unicine.entidades.Confiteria;
import co.edu.uniquindio.unicine.entidades.Teatro;
import co.edu.uniquindio.unicine.servicios.AdminGeneralServicio;
import co.edu.uniquindio.unicine.servicios.CloudinaryServicio;
import lombok.Getter;
import lombok.Setter;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@ViewScoped
public class ConfiteriaBean implements Serializable {

    @Getter @Setter
    private Confiteria confiteria;

    private Map<String, String> imagenes;

    @Getter @Setter
    private boolean editar;

    @Getter @Setter
    private List<Confiteria> teatrosSeleccionados;

    @Autowired
    private AdminGeneralServicio adminGeneralServicio;

    @Autowired
    private CloudinaryServicio cloudinaryServicio;


    @PostConstruct
    public void init() {
        confiteria = new Confiteria();
        imagenes = new HashMap<>();
        editar = false;
    }


    /*public void crearTeatro() {
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

    public void crearConfiteria() {
        try {

            if(!imagenes.isEmpty()) {
                pelicula.setImagenes(imagenes);
                adminGeneralServicio.crearPelicula(pelicula);

                pelicula = new Pelicula(); // Resetea los campos en el fronted (con ayuda del update="@form")
                imagenes = new HashMap<>();

                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "La pelicula ha sido creada con exito!");
                FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);
            }
            else {
                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Es necesario subir al menos una imagen");
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
            Map resultado = cloudinaryServicio.uploadImg(imgFile, "peliculas");
            imagenes.put(resultado.get("public_id").toString(), resultado.get("url").toString());
            System.out.println(resultado);
        }
        catch (Exception e) {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
            FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);
        }
    }


    public File convertirUploadFile(UploadedFile img) throws IOException {
        File file = new File(img.getFileName());
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(img.getContent());
        fos.close();

        return file;
    }*/


    public String getMsgDialogoTeatro() {
        return editar ? "Editar Teatro" : "Crear Teatro";
    }

    public void editarTeatroDialogo(Confiteria confiteriaSeleccionada) {
        this.confiteria = confiteriaSeleccionada;
        editar = true;
    }

    public void crearTeatroDialogo() {
        this.confiteria = new Confiteria();
        editar = false;
    }

}
