package co.edu.uniquindio.unicine.bean;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.faces.view.ViewScoped;
import java.io.Serializable;

@Component
@ViewScoped // Ciclo de vida del controlador, cuando se crea y borra en la memoria
public class InicioBean implements Serializable {

    @Getter @Setter
    private String msg = "Mi primera pagina en JSF";

    @Getter @Setter
    private String dato1, dato2;

    public void cambiarValores(){
        String aux = dato1;
        dato1 = dato2;
        dato2 = aux;
    }
}
