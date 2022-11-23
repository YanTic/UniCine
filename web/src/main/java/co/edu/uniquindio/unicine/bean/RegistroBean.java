package co.edu.uniquindio.unicine.bean;

import co.edu.uniquindio.unicine.entidades.Cliente;
import co.edu.uniquindio.unicine.entidades.Pelicula;
import co.edu.uniquindio.unicine.servicios.ClienteServicio;
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
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
@ViewScoped
public class RegistroBean implements Serializable {

    @Autowired
    private ClienteServicio clienteServicio;

    @Getter @Setter
    private Cliente cliente;

    private Map<String, String> imagenes;

    @Getter @Setter
    private String confirmacionContrasenia;

    @Getter @Setter
    private LocalDate fechaNacimiento;

    @Autowired
    private CloudinaryServicio cloudinaryServicio;


    @PostConstruct // Constructor del Bean (Controlador), al momento de SpringBoot instancie el Bean, llama este metodo
    public void init() { // Se tiene que instanciar el objeto, para que no tire un NullPointerException
        cliente = new Cliente();
        imagenes = new HashMap<>();
    }


    public void registrarCliente() {
        try {

            if(!imagenes.isEmpty()) {
                cliente.setImagenes(imagenes);
                cliente.setEstado(false);

                if(cliente.getContrasenia().equals(confirmacionContrasenia)) {
                    clienteServicio.registrarCliente(cliente);

                    FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Registro Exitoso!");
                    FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);
                }
                else{
                    FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Las contraseñas no son iguales");
                    FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);
                }
                cliente = new Cliente();
                imagenes = new HashMap<>();
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
            Map resultado = cloudinaryServicio.uploadImg(imgFile, "clientes");
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
    }
}
