package co.edu.uniquindio.unicine.bean;

import co.edu.uniquindio.unicine.entidades.EstadoPelicula;
import co.edu.uniquindio.unicine.entidades.Genero;
import co.edu.uniquindio.unicine.entidades.Pelicula;
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
public class PeliculaBean implements Serializable {

    @Getter @Setter
    private Pelicula pelicula;

    @Getter @Setter
    private List<Genero> generos;

    @Getter @Setter
    private List<EstadoPelicula> estadoPeliculas;

    @Getter @Setter
    private List<Pelicula> peliculas;

    @Getter @Setter
    private List<Pelicula> peliculasSeleccionadas;

    @Getter @Setter
    private boolean editar;

    private Map<String, String> imagenes;

    @Autowired
    private AdminGeneralServicio adminGeneralServicio;

    @Autowired
    private CloudinaryServicio cloudinaryServicio;

    @PostConstruct
    public void init() {
        pelicula = new Pelicula();
        peliculas = adminGeneralServicio.listarPeliculas();
        generos = adminGeneralServicio.listarGeneros();
        estadoPeliculas = Arrays.asList(EstadoPelicula.values());
        peliculasSeleccionadas = new ArrayList<>();
        imagenes = new HashMap<>();
        editar = false;
    }

    public void crearPelicula() {
        try {
            if(!editar) {
                if(!imagenes.isEmpty()) {
                    pelicula.setImagenes(imagenes);
                    adminGeneralServicio.crearPelicula(pelicula);

                    peliculas.add(pelicula);
                    pelicula = new Pelicula(); // Resetea los campos en el fronted (con ayuda del update="@form")
                    imagenes = new HashMap<>();

                    FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "La pelicula ha sido creada con exito!");
                    FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);
                }
                else {
                    FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Es necesario subir al menos una imagen");
                    FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);
                }

            }
            else {
                adminGeneralServicio.actualizarPelicula(pelicula.getId(), pelicula);
                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Pelicula actualizada con exito!");
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

    public void eliminarPeliculas() {
        String peliculasNoEliminadas = "";
        for(Pelicula p: peliculasSeleccionadas) {
            try{
                adminGeneralServicio.eliminarPelicula(p.getId());
                peliculas.remove(p);
            }
            catch (Exception e) {
                // Esto es en caso de que al hacer el delete(), el jdbc tire error, ya sea porque no se puede eliminar
                // la pelicula, porque ya existen objetos que están relacionados o dependen de este
                // Es mejor hacerlo de esta forma, solo decirle al usuario "no se puede", que hacer una peligrosa eliminacion por cascada
                peliculasNoEliminadas += "Pelicula [Id: "+p.getId()+ "] ";
            }

        }
        peliculasSeleccionadas.clear();

        if(peliculasNoEliminadas.isEmpty() || peliculasNoEliminadas.equals("")) {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Las peliculas se han borrado con exito!");
            FacesContext.getCurrentInstance().addMessage(null, fm);
            PrimeFaces.current().ajax().update("crud_pelicula:mensajes");
        }
        else {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Las peliculas {"+peliculasNoEliminadas+"} no se pudieron eliminar, porque existen objetos que dependen de ellos");
            FacesContext.getCurrentInstance().addMessage(null, fm);
            PrimeFaces.current().ajax().update("crud_pelicula:mensajes");
        }
    }

    public String getMsgBtnEliminar() {
        if(peliculasSeleccionadas.isEmpty()) {
            return "Eliminar";
        }
        else {
            return peliculasSeleccionadas.size() == 1 ? "Eliminar: 1 elemento" : "Eliminar: "+peliculasSeleccionadas.size()+" elementos";
        }
    }

    public File convertirUploadFile(UploadedFile img) throws IOException {
        File file = new File(img.getFileName());
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(img.getContent());
        fos.close();

        return file;
    }

    public String getMsgDialogoPelicula() {
        return editar ? "Editar Pelicula" : "Crear Pelicula";
    }

    public void editarPeliculaDialogo(Pelicula peliculaSeleccionada) {
        this.pelicula = peliculaSeleccionada;
        editar = true;
    }

    public void crearPeliculaDialogo() {
        this.pelicula = new Pelicula();
        editar = false;
    }
}
