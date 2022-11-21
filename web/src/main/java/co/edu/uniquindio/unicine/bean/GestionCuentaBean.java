package co.edu.uniquindio.unicine.bean;

import co.edu.uniquindio.unicine.servicios.ClienteServicio;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import java.io.Serializable;

@Component
@ViewScoped
public class GestionCuentaBean implements Serializable {

    @Value("#{param['p1']}")
    private String param1;

    @Value("#{param['p2']}")
    private String param2;

    @Getter @Setter
    private String mensaje = "Verificando su cuenta. . .";

    @Getter @Setter
    private boolean verificado;

    @Autowired
    private ClienteServicio clienteServicio;

    @PostConstruct
    public void init() {
        verificado = false;

        if (param1 != null && !param1.isEmpty() && param2 != null && !param2.isEmpty() ) {
            try {
                clienteServicio.activarCuenta(param1, param2);
                mensaje = "Cuenta activada!";
                verificado = true;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public String volverAInicio() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession(); // Este objeto saca a GestionCuentaBean de la memoria
        return "/index?faces-redirect=true";
    }

}
