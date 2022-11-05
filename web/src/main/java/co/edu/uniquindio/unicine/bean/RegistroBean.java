package co.edu.uniquindio.unicine.bean;

import co.edu.uniquindio.unicine.entidades.Cliente;
import co.edu.uniquindio.unicine.servicios.ClienteServicio;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;

@Component
@ViewScoped
public class RegistroBean implements Serializable {

    @Autowired
    private ClienteServicio clienteServicio;

    @Getter @Setter
    private Cliente cliente;

    @Getter @Setter
    private String confirmacionContrasenia;

    @Getter @Setter
    private LocalDate fechaNacimiento;

    @PostConstruct // Constructor del Bean (Controlador), al momento de SpringBoot instancie el Bean, llama este metodo
    public void init() { // Se tiene que instanciar el objeto, para que no tire un NullPointerException
        cliente = new Cliente();
    }



    public void registrarCliente() {
        try {

            //Esto será de prueba, para no tener valores nulos
            cliente.setEstado(false);
            String[] tel = new String[] {"214124", "343242"};
            cliente.setTelefonos(Arrays.asList(tel));

            if(cliente.getContrasenia().equals(confirmacionContrasenia)) {
                clienteServicio.registrarCliente(cliente);

                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Registro Exitoso!");
                FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);
            }
            else{
                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Las contraseñas no son iguales");
                FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);
            }

        } catch (Exception e) {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
            FacesContext.getCurrentInstance().addMessage("mensaje_bean", fm);
        }
    }
}
