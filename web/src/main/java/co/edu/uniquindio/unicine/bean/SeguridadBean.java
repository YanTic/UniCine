package co.edu.uniquindio.unicine.bean;

import co.edu.uniquindio.unicine.entidades.AdminGeneral;
import co.edu.uniquindio.unicine.entidades.AdminTeatro;
import co.edu.uniquindio.unicine.entidades.Ciudad;
import co.edu.uniquindio.unicine.entidades.Cliente;
import co.edu.uniquindio.unicine.servicios.AdminGeneralServicio;
import co.edu.uniquindio.unicine.servicios.AdminTeatroServicio;
import co.edu.uniquindio.unicine.servicios.ClienteServicio;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.Serializable;

@Component
@Scope("session")
public class SeguridadBean implements Serializable {

    //JWT           Buenos para la seguridad, aprender
    //spring boot starter security

    @Autowired
    private ClienteServicio clienteServicio;

    @Autowired
    private AdminGeneralServicio adminGeneralServicio;

    @Autowired
    private AdminTeatroServicio adminTeatroServicio;

    @Getter @Setter
    private boolean autenticado;

    @Getter @Setter
    private Ciudad ciudadSeleccionada;

    @Getter @Setter
    private String email, password;

    @Getter @Setter
    private Cliente cliente;

    @Getter @Setter
    private AdminGeneral adminGeneral;

    @Getter @Setter
    private AdminTeatro adminTeatro;

    @Getter @Setter
    private String tipoSesion;

    @PostConstruct
    public void init() {
        autenticado = false;
    }

    public String iniciarSesion() {
        if (!email.isEmpty() && !password.isEmpty()) {
            try {
                cliente = clienteServicio.login(email, password);
                tipoSesion = "cliente";
                autenticado = true;
                return "/index?faces-redirect=true";
            } catch (Exception e) {
                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
                FacesContext.getCurrentInstance().addMessage("login-bean", fm);
            }
        }
        else{
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "El correo y la contraseña son necesarios");
            FacesContext.getCurrentInstance().addMessage("login-bean", fm);
        }

        return "";
    }

    public String iniciarSesionAdmins() {
        if (!email.isEmpty() && !password.isEmpty()) {
            try{
                adminGeneral = adminGeneralServicio.login(email, password);
                tipoSesion = "admin_general";
                autenticado = true;
                return "/index_admin?faces-redirect=true";

            } catch (Exception e) {
                try {
                    adminTeatro = adminTeatroServicio.login(email, password);
                    tipoSesion = "admin_teatro";
                    autenticado = true;
                    return "/index_admin?faces-redirect=true";

                } catch (Exception c) {
                    FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", c.getMessage());
                    FacesContext.getCurrentInstance().addMessage("login-bean", fm);
                }
            }
        }
        else {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "El correo y la contraseña son necesarios");
            FacesContext.getCurrentInstance().addMessage("login-bean", fm);
        }

        return "";
    }


    public void seleccionarCiudad(Ciudad ciudad) {
         this.ciudadSeleccionada = ciudad;
    }

    public String cerrarSesion() {
        if (tipoSesion == "cliente") {
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession(); // Este objeto saca a seguridadBean de la memoria
            return "/index?faces-redirect=true";
        }
        else{
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession(); // Este objeto saca a seguridadBean de la memoria
            return "/index_admin?faces-redirect=true";
        }
    }


    public String getMsgCerrarSesion() {
        if (tipoSesion == "cliente") {
            return cliente.getNombre();
        }
        else if (tipoSesion == "admin_general") {
            return adminGeneral.getNombre();
        }
        else {
            return adminTeatro.getNombre();
        }
    }
}
