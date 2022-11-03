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
    // A diferencia de los htmls, que son archivos que si se cambian cuando el servidor está en
    // ejecucion, al refrescar la pagina, esos cambios se ven; los mensajes que vienen el controlador
    // no se actualizarán automaticamente porque vienen del lado del servidor (no de la vista), por
    // lo que tienen ser compilados y subidos al servidor (tomcat)

}
