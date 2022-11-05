package co.edu.uniquindio.unicine.bean;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.faces.view.ViewScoped;
import java.io.Serializable;

@Component
@ViewScoped // Ciclo de vida del controlador, cuando se crea y borra en la memoria
public class InicioBean implements Serializable {

}
